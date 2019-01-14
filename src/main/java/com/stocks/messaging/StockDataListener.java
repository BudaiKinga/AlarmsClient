package com.stocks.messaging;

import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.StockPriceData;
import com.stocks.service.IStockDataMsgListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class StockDataListener {

    private Object monitoringObj = new Object();

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${messaging.subscribe.subject}")
    String subscriberSubject;

    @Value("${messaging.init.subject}")
    String initialPriceSubject;

    IStockDataMsgListener listener;

    @JmsListener(destination = "${messaging.stockdata.subject}")
    public void receive(StockPriceData msg) {
        System.out.println("Recieved Message: " + msg);
        listener.msgReceived(msg);
    }

    public void subscribe(Set<Code> codes, IStockDataMsgListener listener) {
        this.listener = listener;
        System.out.println("Sending to producer: " + codes);
        jmsTemplate.convertAndSend(subscriberSubject, codes);

    }

    public void requestInitialPriceData(String s) {
        System.out.println("Requesting " + s);
        jmsTemplate.convertAndSend(initialPriceSubject, s);
        synchronized (listener) {
            try {
                listener.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("initial price received");
    }

    @JmsListener(destination = "${messaging.init.subject}")
    public void receiveInitialPrice(StockPriceData msg) {
        System.out.println("rec init: " + msg);
        synchronized (listener) {
            listener.setInitialPriceData(msg);
            listener.notify();
        }
    }
}
