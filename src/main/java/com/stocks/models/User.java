package com.stocks.models;

import com.stocks.models.validator.FieldMatch;

import javax.persistence.*;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword", message = "passwords must match")

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    @Size(min = 2, max = 15, message = "size must be between 2 and 15")
    private String email;

    @Column(name = "password")
    @Size(min = 2, max = 15, message = "size must be between 2 and 15")
    private String password;

    @Size(min = 2, max = 15, message = "size must be between 2 and 15")
    private String confirmPassword;

    @Column(name = "name")
    @Size(min = 2, max = 15, message = "size must be between 2 and 15")
    private String name;

    @Column(name = "last_name")
    @Size(min = 2, max = 15, message = "size must be between 2 and 15")
    private String lastName;

    @Column(name = "user_name")
    @Size(min = 2, max = 15, message = "size must be between 2 and 15")
    private String userName;

    public User() {
    }

    public User(User users) {
        this.email = users.getEmail();
        this.lastName = users.getLastName();
        this.id = users.getId();
        this.password = users.getPassword();
        this.userName = users.getUserName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
