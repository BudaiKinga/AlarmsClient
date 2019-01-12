package com.stocks.scheduler.provider;

import com.stocks.scheduler.provider.alphavantage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StockProvider {

    private static final String API_KEY = "P4C4FPAYBZW5IHDA";
    private static final int TIME_OUT = 3000;
    private List<String> symbols;
    private TimeSeries stockTimeSeries;

    public StockProvider(List<String> symbols) {
        this.symbols = symbols;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(API_KEY, TIME_OUT);
        stockTimeSeries = new TimeSeries(apiConnector);
    }

    public List<String> getPriceData() {
        List<String> result = new ArrayList<>();

        try {
            // API call frequency is 5 calls per minute and 500 calls per day
            IntraDay response = stockTimeSeries.intraDay("MSFT", Interval.ONE_MIN, OutputSize.COMPACT);
            Map<String, String> metaData = response.getMetaData();
            System.out.println("Information: " + metaData.get("1. Information"));
            System.out.println("StockPriceData: " + metaData.get("2. Symbol"));

            List<StockData> stockData = response.getStockData();
            stockData.forEach(stock -> {
                System.out.println("date:   " + stock.getDateTime());
                System.out.println("open:   " + stock.getOpen());
                System.out.println("high:   " + stock.getHigh());
                System.out.println("low:    " + stock.getLow());
                System.out.println("close:  " + stock.getClose());
                System.out.println("volume: " + stock.getVolume());
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("something went wrong");
        }
        return result;
    }

}
