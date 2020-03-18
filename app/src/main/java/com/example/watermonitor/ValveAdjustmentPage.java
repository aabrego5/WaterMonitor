package com.example.watermonitor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watermonitor.models.Appliance;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import lecho.lib.hellocharts.model.SliceValue;

public class ValveAdjustmentPage extends AppCompatActivity {
    public static ArrayList<ValveStatus> valves;
    public static RecyclerView valveAdjustment;
    public static MyAdapter adapter;
    Button save_button;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valve_adjustment);
        valveAdjustment = findViewById(R.id.ValveAdjustment);
        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < valves.size(); i++) {
                    valves.get(i).setValvePercentage(valves.get(i).getValvePercentage());
                }
            }
        });

        realm = null;

        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
            if (!results_app.isEmpty()) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for(int i = 0; i < valves.size(); i++){
                            for(int j = 0; j < results_app.size(); j++){
                                if(results_app.get(j).appliance.equals(valves.get(i).getApplianceName())){
                                    valves.get(i).setValvePercentage((int)results_app.get(j).amount);
                                }
                            }

                        }
                    }
                });

            }
        }finally{
            if(realm != null) realm.close();
        }
        /*save_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                MyAdapter ad = ValveAdjustmentPage.adapter;
                for(int i = 0; i < valves.size(); i++) {
                    // access the valve data set and the recycler view from ValveAdjustmentPage
                    ValveStatus valve = ValveAdjustmentPage.valves.get(i);
                    MyAdapter.MyViewHolder holder = (MyAdapter.MyViewHolder)
                            ValveAdjustmentPage.valveAdjustment.findViewHolderForAdapterPosition(i);

                    // update the valve status and notify the adapter
                    valve.setValvePercentage(holder.valvePcntBar.getProgress());
                    if(valve.openPcntIsChanged())
                        ad.notifyItemChanged(i);
                }
            }
        });*/

        // Lookup the recyclerview in activity layout
        valveAdjustment = findViewById(R.id.ValveAdjustment);

        // access the database for the appliances
        valves = new ArrayList<ValveStatus>();
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Appliance> appliances = realm.where(Appliance.class).findAll();
            for(Appliance app : appliances) {
                valves.add(new ValveStatus(app.appliance, (int) app.amount));
            }
        } finally {
            if(realm != null) realm.close();
        }

        // Create adapter passing in the dummy data
        adapter = new MyAdapter(valves);
        // Attach the adapter to the recyclerview to populate items
        valveAdjustment.setAdapter(adapter);
        // Set layout manager to position the items
        valveAdjustment.setLayoutManager(new LinearLayoutManager(this));
    }
}