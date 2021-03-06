package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.slice.Slice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watermonitor.models.Appliance;
import com.example.watermonitor.models.Usage;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class HomePage extends AppCompatActivity {
    PieChartView pieChartView;
    List<SliceValue> pieData = new ArrayList<>();
    PieChartData pieChartData = new PieChartData(pieData);
    Realm realm;
    Button valve_button, add_button, about, realtime;
    Usage user;
    Appliance appl;

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

//        finish();
//        startActivity(getIntent());


        valve_button = (Button) findViewById(R.id.adjust_valves_button);
        valve_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,ValveAdjustmentPage.class);
                startActivity(intent);

            }
        });

        add_button = (Button) findViewById(R.id.add_valve_button);
        add_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,AddValvePage.class);
                startActivity(intent);

            }
        });

        about = (Button) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,AboutPage.class);
                startActivity(intent);

            }
        });

//        realtime = (Button) findViewById(R.id.realtime);
//        about.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                Intent intent = new Intent(HomePage.this,RealTime.class);
//                startActivity(intent);
//
//            }
//        });


        //Create a welcome message using the entered username
        Intent intent = getIntent();
        String welcome_message = "Hello, " + LoginPage.check_name + "!";
        TextView textView = findViewById(R.id.greeting);
        textView.setText(welcome_message);

        realm = null;



        pieChartView = findViewById(R.id.chart);

        try {
            realm = Realm.getDefaultInstance();
            //RealmResults<Usage> results = realm.where(Usage.class).contains("username",LoginPage.check_username).findAll();
            final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();

            final int[] arr_colors = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN};
            if (!results_app.isEmpty()) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        for(int i = 0; i < results_app.size(); i++){
                            pieData.add(new SliceValue(25, arr_colors[i]).setLabel(results_app.get(i).appliance));
                        }
                    }
                });

                //Intent intent = new Intent(CreateAccountPage.this, LoginPage.class);
                //startActivity(intent);
            }
        }finally{
            if(realm != null) realm.close();
        }
       // pieChartView = findViewById(R.id.chart);

        //SliceValue bSink = new SliceValue(15,Color.BLUE);
        //bSink.setLabel("Option1");
        //pieData.add(bSink);


        //pieData.add(new SliceValue(15, Color.BLUE).setLabel("Bathroom Sink"));


//        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Option2"));
//        pieData.add(new SliceValue(10, Color.RED).setLabel("Option3"));
//        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Option4"));
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