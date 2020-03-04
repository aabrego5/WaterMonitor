package com.example.watermonitor.models;

import com.example.watermonitor.ValveStatus;

import java.util.Date;

public class Appliance {
    public String appliance;
    public Date date;
    public float amount;
    public String username;
    public ValveStatus valve;

    public Appliance(String location, Date date, float amount, String username, ValveStatus valve) {
        this.appliance = location;
        this.date = date;
        this.amount = amount;
        this.valve = valve;
    }
}

