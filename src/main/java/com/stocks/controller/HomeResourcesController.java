package com.stocks.controller;

import com.stocks.models.Alarm;
import com.stocks.models.CustomUserDetails;
import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.StockPriceData;
import com.stocks.repository.AlarmRepository;
import com.stocks.repository.UserRepository;
import com.stocks.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/rest/alarm")
@Controller
public class HomeResourcesController {

    @Autowired
    private StockDataService stockDataService;

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String securedHello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        model.addAttribute("user", currentUserName);
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        int userId = details.getId();
        List<Alarm> alarms = stockDataService.findAlarmsByUserId(userId);
        model.addAttribute("alarms", alarms);
        List<StockPriceData> stocksToBeMonitored = stockDataService.findStockData(userId);
        return "rest/alarm/home";
    }
}