package com.example.watermonitor;

public class ValveStatus {
    private String appliance;
    private int valveOpenPcnt; // can range from 0-100, 0 is closed and 100 is open
    private boolean isChanged;

    public ValveStatus(String app, int pcnt) {
        appliance = app;
        isChanged = false;
        if(pcnt > 100) valveOpenPcnt = 100;
        else if(pcnt < 0) valveOpenPcnt = 0;
        else valveOpenPcnt = pcnt;
    }

    public ValveStatus(String app) {
        this(app, 100);
    }

    public String GetApplianceName() { return appliance; }

    public int GetValvePercentage() { return valveOpenPcnt; }

    public boolean OpenPcntIsChanged() { return isChanged; }

    protected void SetValvePercentage(int pcnt) {
        if(pcnt > 100) pcnt = 100;
        if(pcnt < 0) pcnt = 0;

        if(pcnt == valveOpenPcnt) return;

        valveOpenPcnt = pcnt;
        isChanged = true;
    }
}
