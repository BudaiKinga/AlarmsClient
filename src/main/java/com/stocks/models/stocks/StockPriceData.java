package com.stocks.models.stocks;

import java.util.*;

public class StockPriceData {


    private Code symbol;
    private String date;
    private Map<PriceType, Double> priceData;

    public void setSymbol(Code symbol) {
        this.symbol = symbol;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPriceData(Map<PriceType, Double> priceData) {
        this.priceData = priceData;
    }

    public void setUpdatedPrices(Set<PriceType> updatedPrices) {
        this.updatedPrices = updatedPrices;
    }

    private Set<PriceType> updatedPrices;


    public StockPriceData() {
        priceData = new HashMap<>();
        Arrays.stream(PriceType.values()).forEach(pt -> priceData.put(pt, -1d));
    }

    public StockPriceData(Code symbol, String date, Map<PriceType, Double> priceData) {
        this.symbol = symbol;
        this.date = date;
        this.priceData = priceData;
        updatedPrices = new HashSet<>(priceData.keySet());
    }

    public Code getSymbol() {
        return symbol;
    }

    public String getDate() {
        return date;
    }

    public Map<PriceType, Double> getPrice() {
        return priceData;
    }

    public Set<PriceType> getUpdatedPrices() {
        return updatedPrices;
    }

    public void updatePrices(Set<PriceType> updatedPrices, Map<PriceType, Double> priceData) {
        for (PriceType pt : updatedPrices) {
            this.priceData.put(pt, priceData.get(pt));
        }
    }

    public double getOpen() {
        return getPrice(PriceType.OPEN_PRICE);
    }

    public double getPrice(PriceType priceType) {
        return priceData.get(priceType);
    }

    @Override
    public String toString() {
        return "StockPriceData{" +
                "symbol=" + symbol +
                ", date='" + date + '\'' +
                ", priceData=" + priceData +
                ", updatedPrices=" + updatedPrices +
                '}';
    }

}
