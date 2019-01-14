package com.stocks.service;

import com.stocks.models.stocks.StockPriceData;

public interface IStockDataMsgListener {

    void msgReceived(StockPriceData msg);

    void setInitialPriceData(StockPriceData stockPriceData);
}
