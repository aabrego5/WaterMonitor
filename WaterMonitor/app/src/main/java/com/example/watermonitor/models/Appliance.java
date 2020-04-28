package com.example.watermonitor.models;

import com.example.watermonitor.ValveStatus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Appliance extends RealmObject {
    public String appliance;
    public Date date;
    public float amount; //out of a 100 percent valve opening
    public String username;
    //public ValveStatus valve;

    //private String appliance;
    //private int valveOpenPcnt; // can range from 0-100, 0 is closed and 100 is open  -> covered by amount
    //private int newValveOpenPcnt; //covered by amount???
    public boolean isChanged;
    //public ArrayList<Integer> app_data = new ArrayList<Integer>();
//    public ArrayList<Integer> app_data_per_month = new ArrayList<Integer>();
//    public ArrayList<Integer> app_data_per_week = new ArrayList<Integer>();
//    public ArrayList<Integer> app_data_per_day = new ArrayList<Integer>();
    public RealmList<Integer> usageHistoryDay; // how much water has been used each day (L), most recent day is first in list
    public Date lastUpdate;

    public Appliance(String location, Date date, float amount, String username, boolean isChanged) {
        this.username = username;
        this.appliance = location;
        this.date = date;
        this.amount = amount;
        this.isChanged = isChanged;
        usageHistoryDay = new RealmList<Integer>();
        usageHistoryDay.add(0, 0);
        this.lastUpdate = date;
    }



    public Appliance() {}
}

