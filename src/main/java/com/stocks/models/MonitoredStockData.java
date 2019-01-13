package com.stocks.models;

import com.stocks.models.stocks.PriceType;
import com.stocks.models.stocks.StockPriceData;

public class MonitoredStockData {

    StockPriceData initialPriceData;
    StockPriceData currentPriceData;
    Alarm alarm;

    public MonitoredStockData() {
    }

    public MonitoredStockData(StockPriceData priceData, StockPriceData currentPriceData, Alarm alarm) {
        this.initialPriceData = priceData;
        this.currentPriceData = currentPriceData;
        this.alarm = alarm;
    }

    public StockPriceData getInitialPriceData() {

        return initialPriceData;
    }

    public void setInitialPriceData(StockPriceData initialPriceData) {
        this.initialPriceData = initialPriceData;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public String getPriceType() {
        return alarm.getPriceType();
    }

    public double getInitialPrice() {
        return alarm.getReferencePrice();
    }

    public double getCurrentPrice() {
        return currentPriceData.getPrice(PriceType.valueOf(alarm.getPriceType()));
    }

    public double getCurrentVariance() {
        double init = getInitialPrice();
        double curr = getCurrentPrice();
        return 100.0d - (init / curr * 100.0);
    }

    public double getTargetVariance() {
        return alarm.getTargetPercentage();
    }

    public String getAlarmName() {
        return alarm.getName();
    }

    public boolean isActive() {
        return alarm.isActive();
    }

    public String getSymbol() {
        return alarm.getSymbol();
    }
}
