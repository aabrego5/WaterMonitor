package com.example.watermonitor.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Index;



public class Usage extends RealmObject {
    @Index

    public String username; //V123
    public String password;
    public String user_name; //Vinisha Venugopal
    //Appliance appliance;
    //ArrayList<String> appList; // = new ArrayList<Appliance>();


    public Usage(String username, String password, String user_name) {
        this.username = username;
        this.password = password;
        this.user_name = user_name;
    }

    public Usage() {

    }



}
