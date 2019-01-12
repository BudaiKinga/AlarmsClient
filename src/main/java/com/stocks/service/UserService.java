package com.stocks.service;

import com.stocks.dao.UserDAO;
import com.stocks.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User createUser(User user) {
        return userDAO.save(user);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }


    public User getUserById(Integer userId) {
        User user = userDAO.findOne(userId);
        return user;
    }

    public User updateUser(Integer userId, User updatedUser) {
        User user = userDAO.findOne(userId);
        if (user == null) {
            return null;
        }
        user.setName(updatedUser.getName());
        User newUSer = userDAO.save(user);
        return newUSer;
    }

    public User deleteUser(Integer userId) {
        User user = userDAO.findOne(userId);
        userDAO.delete(user);
        return user;
    }

}