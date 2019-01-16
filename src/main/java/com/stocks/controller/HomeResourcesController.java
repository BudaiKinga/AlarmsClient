package com.stocks.controller;

import com.stocks.models.Alarm;
import com.stocks.models.CustomUserDetails;
import com.stocks.models.MonitoredStockData;
import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.PriceType;
import com.stocks.models.stocks.StockPriceData;
import com.stocks.service.IStockDataMsgListener;
import com.stocks.service.MailService;
import com.stocks.service.StockDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeResourcesController implements IStockDataMsgListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeResourcesController.class);
    @Autowired
    private StockDataService stockDataService;

    @Autowired
    private MailService mailService;

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "rest/alarm/stockPoll", method = RequestMethod.GET)
    public @ResponseBody
    List<MonitoredStockData> personPoll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        int userId = details.getId();
        List<Alarm> alarms = stockDataService.findAlarmsByUserId(userId);

        Set<Code> stocksToBeMonitored = alarms.stream()
                .map(Alarm::getSymbol)
                .map(Code::valueOf)
                .collect(Collectors.toSet());
        List<MonitoredStockData> monitoredStocks = stockDataService.getMonitoredStockData(userId);
        monitoredStocks.forEach(e -> stocksToBeMonitored.add(Code.valueOf(e.getSymbol())));

        stockDataService.subscribeToStockDataChanges(stocksToBeMonitored, this);

        return stockDataService.getMonitoredStockData(userId);
    }

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
                .map(Alarm::getSymbol)
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

        Alarm alarm = new Alarm();
        model.addAttribute("alarm", alarm);
        return "rest/alarm/home";
    }

    @RequestMapping(value = "/monitorNewStock", method = RequestMethod.POST)
    public String getMonitorNewStock(@ModelAttribute(name = "alarm") Alarm alarm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        int userId = details.getId();
        alarm.setName("-");
        stockDataService.addNewStockToMonitor(alarm, userId);
        stockDataService.subscribeToStockDataChanges(new HashSet<>(Collections.singletonList(Code.valueOf(alarm.getSymbol()))), this);
        return "redirect:rest/alarm/home";
    }

    @Override
    public void msgReceived(StockPriceData msg) {
        List<MonitoredStockData> targetReached = stockDataService.updateMonitoredStockData(msg);
        List<Alarm> alarmsToDeactivate = targetReached.stream().map(MonitoredStockData::getAlarm).collect(Collectors.toList());
        alarmsToDeactivate.forEach(alarm -> alarm.setActive(false));
        stockDataService.updateAlarms(alarmsToDeactivate);
        mailService.sendNotificationOnMailForAlarms(targetReached);
        LOGGER.info("Monitored stocked data updated: " + msg);
    }

    @Override
    public void setInitialPriceData(StockPriceData stockPriceData) {
        stockDataService.setInitialPriceData(stockPriceData);
    }

}