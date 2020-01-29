package com.example.watermonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ValveAdjustmentPage extends AppCompatActivity {
    RecyclerView recycler;
    LinearLayoutManager manager;
    MyAdapter adapter;
    String[] appliances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_valve_adjustment);
        setContentView(R.layout.activity_valve_adjustment_dummy);

        /*appliances = new String[] {"Bathroom Sink", "Kitchen Sink", "Shower", "Toilet"};
        recycler = (RecyclerView) findViewById(R.id.valve_page);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // recycler.setHasFixedSize(true);

        // use a linear layout manager
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);

        // specify an adapter
        adapter = new MyAdapter(appliances);
        recycler.setAdapter(adapter);*/
    }
}
