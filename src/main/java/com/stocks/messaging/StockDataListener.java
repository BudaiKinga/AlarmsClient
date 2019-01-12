package com.stocks.messaging;

import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.StockPriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
public class StockDataListener {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${messaging.subscribe.subject}")
    String subscriberSubject;

    @JmsListener(destination = "${messaging.stockdata.subject}")
    public void receive(StockPriceData msg) {
        System.out.println("Recieved Message: " + msg);
    }

    public void subscribe(Set<Code> codes) {
        jmsTemplate.convertAndSend(subscriberSubject, codes);

    }
}
