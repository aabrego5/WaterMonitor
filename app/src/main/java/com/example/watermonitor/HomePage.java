package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.slice.Slice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.digi.xbee.api.android.XBeeBLEDevice;
import com.digi.xbee.api.exceptions.BluetoothAuthenticationException;
import com.digi.xbee.api.exceptions.XBeeException;

public class HomePage extends AppCompatActivity{
    PieChartView pieChartView;
    List<SliceValue> pieData = new ArrayList<>();
    PieChartData pieChartData = new PieChartData(pieData);
    //Button kitchen;

    // Constants.
    // TODO: replace with the Bluetooth MAC address of your XBee device.
    //private static final String BLE_MAC_ADDR = "08:6B:D7:52:B3:7B";
    // TODO: replace with the Bluetooth password of your XBee device.
    //private static final String BLE_PASSWORD = "1234";

    // Variables.
    //private XBeeBLEDevice myXBeeDevice = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        pieChartView = (PieChartView) findViewById(R.id.chart);
        //kitchen = (Button) findViewById(R.id.kitchen);

        SliceValue bSink = new SliceValue(15,Color.BLUE);
        bSink.setLabel("Bathroom Sink");
        pieData.add(bSink);


        //pieData.add(new SliceValue(15, Color.BLUE).setLabel("Bathroom Sink"));

        SliceValue slice1 = new SliceValue(25, Color.GRAY).setLabel("Kitchen Sink");
        SliceValue slice2 = new SliceValue(10, Color.RED).setLabel("Shower");
        SliceValue slice3 = new SliceValue(60, Color.MAGENTA).setLabel("Toilet");
        pieData.add(slice1);
        pieData.add(slice2);
        pieData.add(slice3);
        pieChartData.setHasLabels(true);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setClickable(true);
        pieChartView.setValueTouchEnabled(true);
        boolean clickable = pieChartView.isClickable();



        pieChartView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                    Intent intent=new Intent(HomePage.this,TrackingPage.class);
                    startActivity(intent);


            }
        });


        //Initializing realm db, only needs to be done nce
        Realm.init(this);


    }





    public void onChartSingleTapped(MotionEvent me){

    }

    /**
     * Displays the given message.
     *
     * @param message The message to show.
     */
    private void showToastMessage(final String message) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(HomePage.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
    }
}


//This code tries to open the communication with an XBee device over Bluetooth Low Energy.
// If it succeeds, the application displays a toast message with the information of the XBee device.
// Otherwise, it displays a Could not
//https://www.digi.com/resources/documentation/digidocs/90001438/tasks/t_create_xb_android_app_scratch.htm
//https://www.digi.com/resources/documentation/digidocs/90001438/containers/cont_create_android_app.htm?TocPath=User%20Guide%7CAndroid%20integration%7CCreate%20an%20XBee%20Android%20application%7C_____0