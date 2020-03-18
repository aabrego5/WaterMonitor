package com.example.watermonitor.models;

import com.example.watermonitor.ValveStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmObject;

public class Appliance extends RealmObject {
    public String appliance;
    public Date date;
    public float amount; //out of a 100 percent valve opening
    public String username;
    public ValveStatus valve;
    //public ArrayList<Integer> app_data = new ArrayList<Integer>();
//    public ArrayList<Integer> app_data_per_month = new ArrayList<Integer>();
//    public ArrayList<Integer> app_data_per_week = new ArrayList<Integer>();
//    public ArrayList<Integer> app_data_per_day = new ArrayList<Integer>();

    public Appliance(String location, Date date, float amount, String username) {
        this.username = username;
        this.appliance = location;
        this.date = date;
        this.amount = amount;
    }



    public Appliance() {}
}

