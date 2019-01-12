package com.stocks.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "alarm_id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "symbol")
    @NotEmpty
    private String symbol;

    @Column(name = "lower")
    @NotEmpty
    private double lower;

    @Column(name = "upper")
    @NotEmpty
    private double upper;

    public Alarm() {
    }

    public Alarm(int id, int userId, @NotEmpty String symbol, @NotEmpty double lower, @NotEmpty double upper) {
        this.id = id;
        this.userId = userId;
        this.symbol = symbol;
        this.lower = lower;
        this.upper = upper;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLower() {
        return lower;
    }

    public void setLower(double lower) {
        this.lower = lower;
    }

    public double getUpper() {
        return upper;
    }

    public void setUpper(double upper) {
        this.upper = upper;
    }
}
