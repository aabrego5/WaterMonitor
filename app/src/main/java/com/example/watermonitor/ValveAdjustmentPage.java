package com.example.watermonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watermonitor.models.Appliance;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ValveAdjustmentPage extends AppCompatActivity {
    public static ArrayList<ValveStatus> valves;
    public static RecyclerView valveAdjustment;
    public static MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valve_adjustment);

        Button save_button = findViewById(R.id.save_button);
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