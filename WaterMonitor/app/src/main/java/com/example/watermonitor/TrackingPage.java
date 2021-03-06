package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.watermonitor.models.Appliance;

public class TrackingPage extends AppCompatActivity {
    Realm realm = null;
    TextView title, titleToday, titleThisWeek, titleThisMonth, titleThisYear, titleYears;
    Button option1, option2, option3, home;
    LineChartView lineChartViewToday;
//    LineChartView lineChartViewThisWeek;
//    LineChartView lineChartViewThisMonth;
//    LineChartView lineChartViewThisYear;
//    LineChartView lineChartViewYears;
    String[] axisDataToday = {"12am", "2am", "4am", "6am", "8am", "10am", "12pm", "2pm", "4pm",
            "6pm", "8pm", "10pm"};
    //int[] yAxisDataToday = {5, 2, 1, 3, 2, 6, 5, 4, 5, 1, 9, 1};

    int[] yAxisDataToday = new int[12];
    int yLimit;



//    String[] axisDataThisWeek = {"M", "Tu", "W", "Th", "F", "Sa", "Su"};
//    int[] yAxisDataThisWeek = {50, 20, 15, 30, 40, 50, 40};
//
//    String[] axisDataThisMonth = {"W1", "W2", "W3", "W4"};
//    int[] getyAxisDataThisMonth = {15, 10, 15, 20};
//
//    String[] axisDataThisYear = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
//            "Oct", "Nov", "Dec"};
//    int[] yAxisDataThisYear = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};
//
//    String[] axisDataYears = {"2018", "2019", "2020"};
//    int[] yAxisDataYears = {250, 200, 150};
    //LineChartData dataYears;

    TextView tvProgressLabel;




    float x1, x2, y1, y2; //for swiping between page applications


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_page);
        title = (TextView) findViewById(R.id.title);
        titleToday = (TextView) findViewById(R.id.titleToday);
        titleThisWeek = (TextView) findViewById(R.id.titleThisWeek);
        titleThisMonth = (TextView) findViewById(R.id.titleThisMonth);
        titleThisYear = (TextView) findViewById(R.id.titleThisYear);
        titleYears = (TextView) findViewById(R.id.titleYears);

//        title.setBackgroundColor(Color.BLUE);
//        titleToday.setBackgroundColor(Color.BLUE);
//        titleThisWeek.setBackgroundColor(Color.BLUE);
//        titleThisMonth.setBackgroundColor(Color.BLUE);
//        titleThisYear.setBackgroundColor(Color.BLUE);
//        titleYears.setBackgroundColor(Color.BLUE);

        title.setTextColor(Color.BLACK);
        titleToday.setTextColor(Color.BLUE);
        titleThisWeek.setTextColor(Color.BLUE);
        titleThisMonth.setTextColor(Color.BLUE);
        titleThisYear.setTextColor(Color.BLUE);
        titleYears.setTextColor(Color.BLUE);


        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        home = (Button) findViewById(R.id.home);

        title.setText("Appliance 1");
        option1.setText("Appliance 2");
        option2.setText("Appliance 3");
        option3.setText("Appliance 4");



        realm = null;
        try{
            realm = Realm.getDefaultInstance();
            Appliance sink  = realm.where(Appliance.class).contains("username", LoginPage.check_username).contains("appliance","Sink").findFirst();
            //yAxisDataToday
            yLimit = 0;
            for(int i = 0; i < 12; i++){
                yAxisDataToday[i] = sink.usageHistoryDay.get(i);
                if(sink.usageHistoryDay.get(i) > yLimit)
                    yLimit = sink.usageHistoryDay.get(i);
            }
//            for(int i = sink.usageHistoryDay.get(0).size(); i < 12; i++){
//                yAxisDataToday[i] = 0;
//            }

            final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
            if (!results_app.isEmpty()) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        for(int i = 0; i < results_app.size(); i++){
                            if(i == 0){
                                title.setText(results_app.get(i).appliance);
                            }else if(i == 1){
                                option1.setText(results_app.get(i).appliance);
                            }else if(i == 2){
                                option2.setText(results_app.get(i).appliance);
                            }else if(i == 3){
                                option3.setText(results_app.get(i).appliance);
                            }
                        }
                    }
                });



            }

        }finally{
            if(realm != null) realm.close();
        }


        lineChartViewToday = findViewById(R.id.chartToday);
//        lineChartViewThisWeek = findViewById(R.id.chartThisWeek);
//        lineChartViewThisMonth = findViewById(R.id.chartThisMonth);
//        lineChartViewThisYear = findViewById(R.id.chartThisYear);
//        lineChartViewYears = findViewById(R.id.chartYears);


        //yAxisLimit needs to be appropriately adjusted
        display(axisDataToday, yAxisDataToday, lineChartViewToday, yLimit);
//        display(axisDataThisWeek, yAxisDataThisWeek, lineChartViewThisWeek, 60);
//        display(axisDataThisMonth, getyAxisDataThisMonth, lineChartViewThisMonth, 40);
//        display(axisDataThisYear, yAxisDataThisYear, lineChartViewThisYear, 100);
//        display(axisDataYears, yAxisDataYears, lineChartViewYears, 300);

//        SeekBar seekBar = findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

//        int progress = seekBar.getProgress();
//        tvProgressLabel = findViewById(R.id.textView);
//        tvProgressLabel.setText("Valve Opening: " + progress);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrackingPage.this,TrackingPage2.class);
                startActivity(intent);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrackingPage.this,TrackingPage3.class);
                startActivity(intent);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrackingPage.this,TrackingPage4.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrackingPage.this,HomePage.class);
                startActivity(intent);
            }
        });
    }
//    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
//
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            // updated continuously as the user slides the thumb
//            tvProgressLabel.setText("Valve Opening: " + progress);
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            // called when the user first touches the SeekBar
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            // called after the user finishes moving the SeekBar
//        }
//    };


    public void display(String[] axisData, int[] yAxisData, LineChartView lineChartView, int yAxisLimit){
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();


        Line line = new Line(yAxisValues).setColor(Color.BLUE);
        //line.setStrokeWidth()
        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setName("Time Period");
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.BLACK);
        data.setAxisXBottom(axis);



        Axis yAxis = new Axis();
        yAxis.setName("Liters");
        yAxis.setTextColor(Color.BLACK);
        //yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = yAxisLimit;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }

    public boolean onTouchEvent(MotionEvent touchevent){
        switch(touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(x1 < x2){
                    Intent i = new Intent(this, HomePage.class);
                    startActivity(i);
                }
                if(x1 > x2){
//                    Intent i = new Intent(this, TrackingPage2.class);
//                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
