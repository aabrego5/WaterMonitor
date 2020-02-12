package com.example.watermonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ValveAdjustmentPage extends AppCompatActivity {
    private ArrayList<ValveStatus> valves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valve_adjustment);

        // Lookup the recyclerview in activity layout
        RecyclerView valveAdjustment = (RecyclerView) findViewById(R.id.ValveAdjustment);

        // create dummy list of valves
        valves = new ArrayList<ValveStatus>();
        valves.add(new ValveStatus("Sink"));
        valves.add(new ValveStatus("Shower", 50));
        valves.add(new ValveStatus("Toilet", 75));

        // Create adapter passing in the dummy data
        MyAdapter adapter = new MyAdapter(valves);
        // Attach the adapter to the recyclerview to populate items
        valveAdjustment.setAdapter(adapter);
        // Set layout manager to position the items
        valveAdjustment.setLayoutManager(new LinearLayoutManager(this));
    }
}