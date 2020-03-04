package com.example.watermonitor.models;

import com.example.watermonitor.ValveStatus;

import java.util.Date;

import io.realm.RealmObject;

public class Appliance extends RealmObject {
    public String appliance;
    public Date date;
    public float amount;
    public String username;
    public ValveStatus valve;

    public Appliance(String location, Date date, float amount, String username) {
        this.username = username;
        this.appliance = location;
        this.date = date;
        this.amount = amount;
    }

    public Appliance() {}
}

