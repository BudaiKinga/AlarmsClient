package com.stocks.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "alarm_id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date creationTime;

    @Column(name = "symbol")
    @NotEmpty
    private String symbol;

    @Column(name = "targetPercentage")
    private double targetPercentage;

    @Column(name = "price_type")
    private String priceType;

    @Column(name = "active")
    private boolean active;

    @Column(name = "reference_price")
    private double referencePrice;


    public Alarm() {
    }

    public Alarm(int id, int userId, String name, Date creationTime, @NotEmpty String symbol, double targetPercentage, String priceType, boolean active, double referencePrice) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.creationTime = creationTime;
        this.symbol = symbol;
        this.targetPercentage = targetPercentage;
        this.priceType = priceType;
        this.active = active;
        this.referencePrice = referencePrice;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(double targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(double referencePrice) {
        this.referencePrice = referencePrice;
    }
}
