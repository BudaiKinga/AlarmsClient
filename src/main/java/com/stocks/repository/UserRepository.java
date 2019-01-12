package com.stocks.repository;

import com.stocks.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);

    Optional<User> findByUserName(String username);
}