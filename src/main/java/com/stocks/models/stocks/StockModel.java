package com.stocks.models.stocks;

import java.util.Map;

public class StockModel {
    public Map<Code, StockPriceData> stocks;

    public void update(Map<Code, StockPriceData> updateStockPriceData) {
        updateStockPriceData.entrySet().stream().forEach(e ->
                {
                    Code code = e.getKey();
                    StockPriceData updatedPriceData = e.getValue();
                    StockPriceData priceData = stocks.get(code);
                    priceData.updatePrices(updatedPriceData.getUpdatedPrices(), updatedPriceData.getPrice());
                }
        );
    }
}
