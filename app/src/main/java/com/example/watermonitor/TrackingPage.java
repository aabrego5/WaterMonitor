package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class TrackingPage extends AppCompatActivity {
    Button option1, option2, option3, home;
    LineChartView lineChartViewToday;
    LineChartView lineChartViewThisWeek;
    LineChartView lineChartViewThisMonth;
    LineChartView lineChartViewThisYear;
    LineChartView lineChartViewYears;
    String[] axisDataToday = {"12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm",
            "9pm", "10pm", "11pm"};
    int[] yAxisDataToday = {5, 2, 1, 3, 2, 6, 5, 4, 5, 1, 9, 1};


    String[] axisDataThisWeek = {"M", "Tu", "W", "Th", "F", "Sa", "Su"};
    int[] yAxisDataThisWeek = {50, 20, 15, 30, 40, 50, 40};

    String[] axisDataThisMonth = {"W1", "W2", "W3", "W4"};
    int[] getyAxisDataThisMonth = {15, 10, 15, 20};

    String[] axisDataThisYear = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec"};
    int[] yAxisDataThisYear = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};

    String[] axisDataYears = {"2018", "2019", "2020"};
    int[] yAxisDataYears = {250, 200, 150};
    //LineChartData dataYears;

    TextView tvProgressLabel;




    float x1, x2, y1, y2; //for swiping between page applications


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_page);
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        home = (Button) findViewById(R.id.home);
        lineChartViewToday = findViewById(R.id.chartToday);
        lineChartViewThisWeek = findViewById(R.id.chartThisWeek);
        lineChartViewThisMonth = findViewById(R.id.chartThisMonth);
        lineChartViewThisYear = findViewById(R.id.chartThisYear);
        lineChartViewYears = findViewById(R.id.chartYears);


        //yAxisLimit needs to be appropriately adjusted
        display(axisDataToday, yAxisDataToday, lineChartViewToday, 10);
        display(axisDataThisWeek, yAxisDataThisWeek, lineChartViewThisWeek, 60);
        display(axisDataThisMonth, getyAxisDataThisMonth, lineChartViewThisMonth, 40);
        display(axisDataThisYear, yAxisDataThisYear, lineChartViewThisYear, 100);
        display(axisDataYears, yAxisDataYears, lineChartViewYears, 300);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        int progress = seekBar.getProgress();
        tvProgressLabel = findViewById(R.id.textView);
        tvProgressLabel.setText("Valve Opening: " + progress);

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
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvProgressLabel.setText("Valve Opening: " + progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };


    public void display(String[] axisData, int[] yAxisData, LineChartView lineChartView, int yAxisLimit){
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();


        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
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
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);



        Axis yAxis = new Axis();
        yAxis.setName("Gallons");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
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
