package com.stocks.scheduler.provider.alphavantage;

import java.time.LocalDateTime;

public class SimpelDigitalCurrencyData {

    private final LocalDateTime parse;
    private final double priceA;
    private final double priceB;
    private final double volume;
    private final double marketCap;

    public SimpelDigitalCurrencyData(LocalDateTime parse,
                                     double priceA,
                                     double priceB,
                                     double volume,
                                     double marketCap) {
        this.parse = parse;
        this.priceA = priceA;
        this.priceB = priceB;
        this.volume = volume;
        this.marketCap = marketCap;
    }

    public LocalDateTime getDateTime() {
        return parse;
    }

    public double getPriceA() {
        return priceA;
    }

    public double getPriceB() {
        return priceB;
    }

    public double getVolume() {
        return volume;
    }

    public double getMarketCap() {
        return marketCap;
    }

}
