package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.slice.Slice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class HomePage extends AppCompatActivity {
    PieChartView pieChartView;
    List<SliceValue> pieData = new ArrayList<>();
    PieChartData pieChartData = new PieChartData(pieData);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        pieChartView = findViewById(R.id.chart);

        SliceValue bSink = new SliceValue(15,Color.BLUE);
        bSink.setLabel("Bathroom Sink");
        pieData.add(bSink);


        //pieData.add(new SliceValue(15, Color.BLUE).setLabel("Bathroom Sink"));


        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Kitchen Sink"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Shower"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Toilet"));
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setClickable(true);
        pieChartView.setValueTouchEnabled(true);


        pieChartView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent=new Intent(HomePage.this,TrackingPage.class);
                startActivity(intent);
            }
        });



    }
    public void onChartSingleTapped(MotionEvent me){

    }
}
