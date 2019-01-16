package com.stocks.models;

import com.stocks.models.stocks.PriceType;
import com.stocks.models.stocks.StockPriceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoredStockData {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoredStockData.class);

    StockPriceData currentPriceData;
    Alarm alarm;

    public MonitoredStockData() {
        currentPriceData = new StockPriceData();
    }

    public MonitoredStockData(StockPriceData currentPriceData, Alarm alarm) {
        this.currentPriceData = currentPriceData;
        this.alarm = alarm;
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
        String pt = alarm.getPriceType();
        if (pt == null) {
            LOGGER.info("No alarm set up, adding empty monitored stock data");
            return -1;
        }
        PriceType priceType = PriceType.valueOf(pt);
        return currentPriceData.getPrice(priceType);
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

    public void setCurrentPrice(double updatedPrice) {
        this.currentPriceData.setPrice(PriceType.valueOf(getPriceType()), updatedPrice);
    }

    public boolean targetValueReached() {
        if (!alarm.isActive()) {
            return false;
        }
        double currentPerc = getCurrentVariance();
        double targetPerc = getTargetVariance();
        if (targetPerc < 0) {
            return currentPerc<=targetPerc;
        }
        return targetPerc<=currentPerc;
    }
}
