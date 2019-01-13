package com.stocks.service;

import com.stocks.dao.AlarmDAO;
import com.stocks.models.Alarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    @Autowired
    AlarmDAO alarmDAO;

    public Alarm createAlarm(Alarm alarm) {
        return alarmDAO.save(alarm);
    }

    public Alarm findById(int id) {
        return alarmDAO.findById(id);
    }

    public void delete(int id) {
        alarmDAO.delete(id);
    }

    public void save(Alarm alarm) {
        alarmDAO.save(alarm);
    }

    public List<Alarm> findAllByUserId(int userId) {
        return alarmDAO.findAllByUserId(userId);
    }
}