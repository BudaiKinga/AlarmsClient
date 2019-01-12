package com.stocks.scheduler;

import com.stocks.scheduler.provider.StockProvider;

import java.util.List;

public class RequestService implements Runnable {

    private static int count = 0;
    private StockProvider stockProvider;

    public RequestService(List<String> symbols) {
        stockProvider = new StockProvider(symbols);
    }

    @Override
    public void run() {
        count++;
        List<String> result = stockProvider.getPriceData();
        for (String s : result) {
            System.out.println(s);
        }
        System.out.println("Executed!" + count);
    }
}
