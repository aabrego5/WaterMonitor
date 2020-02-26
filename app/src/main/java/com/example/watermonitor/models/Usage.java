package com.example.watermonitor.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class Usage extends RealmObject {
    private String location;
    @Index
    private Date date;
    private float amount;

    public Usage(String location, Date date, float amount) {
        this.location = location;
        this.date = date;
        this.amount = amount;
    }

    public Usage() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
