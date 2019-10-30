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

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.TextView;

import lecho.lib.hellocharts.view.LineChartView;

public class TrackingPage extends AppCompatActivity {
    LineChartView lineChartViewToday;
    LineChartView lineChartViewThisWeek;
    LineChartView lineChartViewThisMonth;
    LineChartView lineChartViewThisYear;
    LineChartView lineChartViewYears;
    String[] axisDataToday = {"12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm",
            "9pm", "10pm", "11pm"};
    int[] yAxisDataToday = {5, 2, 1, 3, 2, 6, 5, 4, 5, 1, 9, 1};


    String[] axisDataThisWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    int[] yAxisDataThisWeek = {50, 20, 15, 30, 40, 50, 40};

    String[] axisDataThisMonth = {"Week1", "Week2", "Week3", "Week4"};
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
        axis.setName("Years");
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
                    Intent i = new Intent(this, TrackingPageSinkDay.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
