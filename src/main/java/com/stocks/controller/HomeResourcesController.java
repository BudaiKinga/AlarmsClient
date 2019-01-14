package com.stocks.controller;

import com.stocks.dto.AlarmDTO;
import com.stocks.models.Alarm;
import com.stocks.models.CustomUserDetails;
import com.stocks.models.MonitoredStockData;
import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.PriceType;
import com.stocks.models.stocks.StockPriceData;
import com.stocks.service.IStockDataMsgListener;
import com.stocks.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeResourcesController implements IStockDataMsgListener {

    @Autowired
    private StockDataService stockDataService;

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "rest/alarm/home", method = RequestMethod.GET)
    public String getDataForHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        model.addAttribute("user", currentUserName);
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        int userId = details.getId();
        List<Alarm> alarms = stockDataService.findAlarmsByUserId(userId);
        model.addAttribute("alarms", alarms);

        Set<Code> stocksToBeMonitored = alarms.stream()
                .map(alarm -> alarm.getSymbol())
                .map(Code::valueOf)
                .collect(Collectors.toSet());
        List<MonitoredStockData> monitoredStocks = stockDataService.getMonitoredStockData(userId);
        monitoredStocks.forEach(e -> stocksToBeMonitored.add(Code.valueOf(e.getSymbol())));

        stockDataService.subscribeToStockDataChanges(stocksToBeMonitored, this);

        List<MonitoredStockData> monitoredStockData = stockDataService.getMonitoredStockData(userId);
        model.addAttribute("stocks", monitoredStockData);

        Set<String> codes = Arrays.stream(Code.values()).map(Code::name).collect(Collectors.toSet());
        model.addAttribute("codes", codes);

        PriceType[] priceTypes = PriceType.values();
        model.addAttribute("priceTypes", priceTypes);

        AlarmDTO alarm = new AlarmDTO();
        model.addAttribute("alarm", alarm);
        return "rest/alarm/home";
    }

    @RequestMapping(value = "/monitorNewStock", method = RequestMethod.POST)
    public String getMonitorNewStock(@ModelAttribute(name = "alarm") Alarm alarm, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        int userId = details.getId();
        stockDataService.addNewStockToMonitor(alarm, userId);
        stockDataService.subscribeToStockDataChanges(new HashSet<>(Arrays.asList(Code.valueOf(alarm.getSymbol()))), this);
        return "redirect:rest/alarm/home";
    }

    @Override
    public void msgReceived(StockPriceData msg) {
        List<MonitoredStockData> targetReached = stockDataService.updateMonitoredStockData(msg);
        List<Alarm> alarmsToDeactivate = targetReached.stream().map( msd -> msd.getAlarm()).collect(Collectors.toList());
        alarmsToDeactivate.forEach(alarm -> alarm.setActive(false));
        stockDataService.updateAlarms(alarmsToDeactivate);
        // new service to send email
        System.out.println("Monitored stocked data updated: " + msg);
    }

    @Override
    public void setInitialPriceData(StockPriceData stockPriceData) {
        stockDataService.setInitialPriceData(stockPriceData);
    }

}