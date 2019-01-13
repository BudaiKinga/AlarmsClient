package com.stocks.models.stocks;

public enum PriceType {
    OPEN_PRICE("1. open"),
    HIGH_PRICE("2. high"),
    LOW_PRICE("3. low"),
    CLOSE_PRICE("4. close"),
    VOLUME("5. volume");

    private String responseTag;

    public String getResponseTag() {
        return responseTag;
    }

    PriceType(String responseTag) {
        this.responseTag = responseTag;
    }
}