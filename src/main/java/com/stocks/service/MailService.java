package com.stocks.service;

import com.stocks.models.MonitoredStockData;
import com.stocks.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    UserService userService;

    private static final String SUBJECT_ALARM = "Alarm %s has been reached target value on %s!";
    private static final String TEXT_ALARM = "Alarm %s having target variance %.4f has been triggered.\n" +
            "Initial price: %.4f\n" +
            "Current price: %.4f";
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    public void sendNotificationOnMailForAlarms(List<MonitoredStockData> monitoredStockData) {
        monitoredStockData.stream().forEach(msd -> {
            mailSender.send(createMailMessage(msd));
        });
    }

    private SimpleMailMessage createMailMessage(MonitoredStockData msd) {
        SimpleMailMessage message = new SimpleMailMessage();
        int userId = msd.getAlarm().getUserId();
        User user = userService.getUserById(userId);
        message.setTo(user.getEmail());
        message.setSubject(String.format(SUBJECT_ALARM, msd.getAlarm().getName(), msd.getSymbol()));
        message.setText(String.format(TEXT_ALARM, msd.getAlarm().getName(), msd.getTargetVariance(), msd.getInitialPrice(), msd.getCurrentPrice()));
        LOGGER.info("Sending e-mail notification to: " + user.getEmail());
        return message;
    }

}
