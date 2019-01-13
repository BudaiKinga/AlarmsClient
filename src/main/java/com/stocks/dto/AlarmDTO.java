package com.stocks.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AlarmDTO {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String symbol;

    @NotNull
    @NotEmpty
    private double targetPercentage;

    @NotNull
    @NotEmpty
    private String priceType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(double targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public AlarmDTO() {
    }
}
