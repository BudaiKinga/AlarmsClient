package com.stocks.dao;

import com.stocks.models.User;
import com.stocks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDAO {

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(Integer userId) {
        return userRepository.getOne(userId);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

}