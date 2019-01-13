package com.stocks.controller;

import com.stocks.dto.AlarmDTO;
import com.stocks.models.Alarm;
import com.stocks.models.CustomUserDetails;
import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.PriceType;
import com.stocks.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
// rest controller = controller + response body. response body before method = return value of method should be sent in body on resp
@PreAuthorize("hasAnyRole('USER')")
public class AlarmController {

    @Autowired
    AlarmService alarmService;


    @RequestMapping(value = "/createAlarm", method = RequestMethod.POST)
    public String getCreateAlarmForm(Model model) {
        AlarmDTO alarm = new AlarmDTO();
        model.addAttribute("alarm", alarm);
        Code[] codes = Code.values();
        model.addAttribute("codes", codes);
        PriceType[] priceTypes = PriceType.values();
        model.addAttribute("priceTypes", priceTypes);
        return "rest/alarm/createAlarm";
    }

    @RequestMapping(value = "/saveAlarm", method = RequestMethod.POST)
    public String saveAlarm(@ModelAttribute(name = "alarm") Alarm alarm, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        int userId = details.getId();
        alarm.setUserId(userId);
        alarm.setCreationTime(new Date());
        alarm.setActive(true);
        alarmService.createAlarm(alarm);
        return "redirect:/rest/alarm/home";
    }

    @RequestMapping(value = "/rest/alarm/delete/{id}", method = RequestMethod.GET)
    public String deleteAlarm(@PathVariable("id") int id, Model model) {
        alarmService.delete(id);
        return "redirect:/rest/alarm/home";
    }

    @RequestMapping(value = "rest/alarm/edit/{id}", method = RequestMethod.POST)
    public String showEditAlarm(@PathVariable("id") int id, Model model) {
        Alarm alarm = alarmService.findById(id);
        model.addAttribute("alarm", alarm);
        Code[] codes = Code.values();
        model.addAttribute("codes", codes);
        PriceType[] priceTypes = PriceType.values();
        model.addAttribute("priceTypes", priceTypes);
        return "rest/alarm/updateAlarm";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateAlarm(Alarm alarm, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        int userId = details.getId();
        alarm.setUserId(userId);
        alarmService.save(alarm);
        model.addAttribute("alarms", alarmService.findAllByUserId(userId));
        return "redirect:rest/alarm/home";
    }


    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return "redirect:rest/alarm/home";
    }
}
