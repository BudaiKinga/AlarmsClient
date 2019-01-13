package com.stocks.service;

import com.stocks.dao.AlarmDAO;
import com.stocks.messaging.StockDataListener;
import com.stocks.models.Alarm;
import com.stocks.models.MonitoredStockData;
import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.StockPriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service

public class StockDataService {

    @Autowired
    private AlarmDAO alarmDAO;

    @Autowired
    private StockDataListener subcriber;

    public List<Alarm> findAlarmsByUserId(int userId) {
        return alarmDAO.findAllByUserId(userId);
    }

    public void subscribeToStockDataChanges(Set<Code> codes) {
        subcriber.subscribe(codes);
    }

    public List<MonitoredStockData> getMonitoredStockData(int userId) {
        List<MonitoredStockData> monitoredStockData = new ArrayList<>();
        List<Alarm> alarms = findAlarmsByUserId(userId);
        for (Alarm a : alarms) {
            MonitoredStockData msd = new MonitoredStockData(new StockPriceData(), new StockPriceData(), a);
            monitoredStockData.add(msd);
        }
        return monitoredStockData;
    }
}
