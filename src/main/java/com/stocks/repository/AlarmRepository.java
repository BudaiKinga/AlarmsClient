package com.stocks.repository;

import com.stocks.models.Alarm;
import com.stocks.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    Optional<Alarm> findById(Integer id);

    List<Alarm> findAllByUserId(int userId);
}