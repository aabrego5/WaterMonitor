package com.example.watermonitor.models;

import java.util.Date;

public class Appliance {
    public String appliance;
    public Date date;
    public float amount;

    public Appliance(String location, Date date, float amount) {
        this.appliance = location;
        this.date = date;
        this.amount = amount;
    }
}

