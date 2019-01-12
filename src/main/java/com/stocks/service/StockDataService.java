package com.stocks.service;

import com.stocks.dao.AlarmDAO;
import com.stocks.messaging.StockDataListener;
import com.stocks.models.Alarm;
import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.StockPriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class StockDataService {

    @Autowired
    private AlarmDAO alarmDAO;

    @Autowired
    private StockDataListener subcriber;

    private Map<Integer, List<Alarm>> alarmsCache = new HashMap<>();

    public List<Alarm> findAlarmsByUserId(int userId) {
        List<Alarm> result = alarmsCache.get(userId);
        if (result == null) {
            result = alarmDAO.findAllByUserId(userId);
            alarmsCache.put(userId, result);
        }
        return result;
    }

    public List<StockPriceData> findStockData(int userId) {
        List<Alarm> alarms = findAlarmsByUserId(userId);
        Set<Code> stocksToBeMonitored = alarms.stream()
                .map(alarm -> alarm.getSymbol())
                .map(Code::valueOf)
                .collect(Collectors.toSet());
        subcriber.subscribe(stocksToBeMonitored);

        return null;
    }
}
