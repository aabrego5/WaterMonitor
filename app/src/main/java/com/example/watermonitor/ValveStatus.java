package com.example.watermonitor;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import org.eclipse.paho.android.service.*;

public class ValveStatus extends RealmObject{
    @Index
    private String appliance;
    private int valveOpenPcnt; // can range from 0-100, 0 is closed and 100 is open
    private int newValveOpenPcnt;
    private boolean isChanged;

    public ValveStatus(){}

    public ValveStatus(String app, int pcnt) {
        appliance = app;
        isChanged = false;
        if(pcnt > 100) valveOpenPcnt = newValveOpenPcnt = 100;
        else if(pcnt < 0) valveOpenPcnt = newValveOpenPcnt = 0;
        else valveOpenPcnt = newValveOpenPcnt = pcnt;
    }

    public ValveStatus(String app) {
        this(app, 100);
    }

    public String getApplianceName() { return appliance; }

    public int getValvePercentage() { return valveOpenPcnt; }

    public boolean openPcntIsChanged() { return isChanged; }

    protected void setValvePercentage(int pcnt) {
        if(pcnt > 100) pcnt = 100;
        if(pcnt < 0) pcnt = 0;

        if(pcnt != valveOpenPcnt) {
            valveOpenPcnt = pcnt;
            isChanged = true;
        }
    }
}
