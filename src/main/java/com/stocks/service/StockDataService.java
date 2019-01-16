package com.stocks.service;

import com.stocks.dao.AlarmDAO;
import com.stocks.messaging.StockDataListener;
import com.stocks.models.Alarm;
import com.stocks.models.MonitoredStockData;
import com.stocks.models.stocks.Code;
import com.stocks.models.stocks.PriceType;
import com.stocks.models.stocks.StockPriceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockDataService {

    @Autowired
    private AlarmDAO alarmDAO;

    @Autowired
    private StockDataListener subcriber;

    private Map<Integer, List<MonitoredStockData>> monitoredStockData = new HashMap<>();

    public List<Alarm> findAlarmsByUserId(int userId) {
        return alarmDAO.findAllByUserId(userId);
    }

    public void subscribeToStockDataChanges(Set<Code> codes, IStockDataMsgListener listener) {
        subcriber.subscribe(codes, listener);
    }

    public List<MonitoredStockData> getMonitoredStockData(int userId) {

        List<MonitoredStockData> msd = this.monitoredStockData.get(userId);
        if (msd == null) {
            msd = new ArrayList<>();
            monitoredStockData.put(userId, msd);
            List<Alarm> alarms = findAlarmsByUserId(userId);
            for (Alarm a : alarms) {
                MonitoredStockData msdEntry = new MonitoredStockData(new StockPriceData(), a);
                msd.add(msdEntry);
            }
        }
        return msd;
    }

    public List<MonitoredStockData> updateMonitoredStockData(StockPriceData msg) {
        List<MonitoredStockData> result = new ArrayList<>();
        monitoredStockData.values().forEach(msd ->
                msd.forEach(e -> {
                            if (e.getSymbol().equals(msg.getSymbol().name())) {
                                PriceType pt = PriceType.valueOf(e.getPriceType());
                                double updatedPrice = msg.getPrice(pt);
                                e.setCurrentPrice(updatedPrice);
                                if (e.targetValueReached()) {
                                    result.add(e);
                                }
                            }
                        }
                )
        );

        return result;
    }

    public void addNewStockToMonitor(Alarm alarm, int userId) {
        MonitoredStockData ms = new MonitoredStockData(new StockPriceData(), alarm);
        List<MonitoredStockData> msd = this.monitoredStockData.get(userId);
        // get one request with price data
        if (msd == null) {
            msd = new ArrayList<>();
            monitoredStockData.put(userId, msd);
        }
        msd.add(ms);
    }


    public void setInitialPriceData(StockPriceData stockPriceData) {
        List<MonitoredStockData> allStockData = monitoredStockData.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        for (MonitoredStockData msd : allStockData) {
            Alarm alarm = msd.getAlarm();
            if (alarm.getReferencePrice() == 0.0d && stockPriceData.getSymbol().toString().equals(alarm.getSymbol())) {
                alarm.setReferencePrice(stockPriceData.getPrice(PriceType.valueOf(alarm.getPriceType())));
            }
        }
    }

    public Set<String> getMonitoredStockDataWithoutAlarms(int userId) {
        Set<String> msdWithoutAlarms = monitoredStockData.get(userId).stream()
                .map(e -> e.getSymbol())
                .collect(Collectors.toSet());
        return msdWithoutAlarms;
    }

    public void addNewStockData(MonitoredStockData msdNew, int userId) {
        List<MonitoredStockData> msd = this.monitoredStockData.get(userId);
        if (msd == null) {
            msd = new ArrayList<>();
            monitoredStockData.put(userId, msd);
        }
        msd.add(msdNew);
    }

    public void deactivateAlarms(int userId, int id) {
        List<MonitoredStockData> msd = monitoredStockData.get(userId);
        List<MonitoredStockData> toBeRemoved = new ArrayList<>();
        msd.forEach(monitoredStockData1 -> {
            if (monitoredStockData1.getAlarm().getId() == id) {
                toBeRemoved.add(monitoredStockData1);
            }
        });
        msd.removeAll(toBeRemoved);

    }

    public void requestInitialPrice(String symbol) {
        subcriber.requestInitialPriceData(symbol);
    }

    public void updateAlarms(List<Alarm> alarmsToDeactivate) {
        for(Alarm a : alarmsToDeactivate) {
            alarmDAO.save(a);
        }
    }
}
