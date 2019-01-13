package com.stocks.controller;

import com.stocks.models.Alarm;
import com.stocks.models.CustomUserDetails;
import com.stocks.models.MonitoredStockData;
import com.stocks.models.stocks.Code;
import com.stocks.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/rest/alarm")
@Controller
public class HomeResourcesController {

    @Autowired
    private StockDataService stockDataService;

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
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
        stockDataService.subscribeToStockDataChanges(stocksToBeMonitored);

        List<MonitoredStockData> monitoredStockData = stockDataService.getMonitoredStockData(userId);
        model.addAttribute("stocks", monitoredStockData);
        return "rest/alarm/home";
    }
}