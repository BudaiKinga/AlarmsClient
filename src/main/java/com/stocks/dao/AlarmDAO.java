package com.stocks.dao;

import com.stocks.models.Alarm;
import com.stocks.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmDAO {

    @Autowired
    AlarmRepository alarmRepo;

    public Alarm save(Alarm a) {
        return alarmRepo.save(a);
    }

    public List<Alarm> findAll() {
        return alarmRepo.findAll();
    }

    public Alarm findById(int alarmId) {
        return alarmRepo.findById(alarmId).get();
    }

    public void delete(Alarm alarm) {
        alarmRepo.delete(alarm);
    }

    public List<Alarm> findAllByUserId(int userId) {
        return alarmRepo.findAllByUserId(userId);
    }

    public void delete(int id) {
        alarmRepo.deleteById(id);
    }
}
