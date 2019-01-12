package com.stocks.scheduler.provider.alphavantage;


import java.time.LocalDateTime;

public class StockData {
    private final LocalDateTime dateTime;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double adjustedClose;
    private final long volume;
    private final double dividendAmount;
    private final double splitCoefficient;

    public StockData(LocalDateTime dateTime,
                     double open,
                     double high,
                     double low,
                     double close,
                     double adjustedClose,
                     long volume,
                     double dividendAmount,
                     double splitCoefficient) {
        this.dateTime = dateTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
        this.dividendAmount = dividendAmount;
        this.splitCoefficient = splitCoefficient;
    }

    public StockData(LocalDateTime dateTime,
                     double open,
                     double high,
                     double low,
                     double close,
                     double adjustedClose,
                     long volume,
                     double dividendAmount) {
        this.dateTime = dateTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
        this.dividendAmount = dividendAmount;
        this.splitCoefficient = 0;
    }

    public StockData(LocalDateTime dateTime,
                     double open,
                     double high,
                     double low,
                     double close,
                     long volume) {
        this.dateTime = dateTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjustedClose = 0;
        this.volume = volume;
        this.dividendAmount = 0;
        this.splitCoefficient = 0;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getAdjustedClose() {
        return adjustedClose;
    }

    public long getVolume() {
        return volume;
    }

    public double getDividendAmount() {
        return dividendAmount;
    }

    public double getSplitCoefficient() {
        return splitCoefficient;
    }
}

