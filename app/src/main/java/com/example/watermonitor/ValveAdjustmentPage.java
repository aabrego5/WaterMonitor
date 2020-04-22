package com.example.watermonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watermonitor.models.Appliance;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class ValveAdjustmentPage extends AppCompatActivity {
    public static ArrayList<ValveStatus> valves;
    //public static RecyclerView valveAdjustment;
    //public static MyAdapter adapter;
    Button save_button, home_button;
    Realm realm;
    SeekBar seekbar1, seekbar2, seekbar3, seekbar4;
    TextView valve1, valve2, valve3, valve4;
    float pval;

    MqttAndroidClient mqttAndroidClient;
    String clientId = "ExampleAndroidClient";
    final String serverUri = "tcp:mqtt.eclipse.org:1883";
    final String publishTopic1 = "/cc3200/ToggleLEDCmdL1";
    final String publishTopic2 = "/cc3200/ToggleLEDCmdL2";
    final String publishTopic3 = "/cc3200/ToggleLEDCmdL3";
    final String publishMessage = "AHHHH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valve_adj);

        //save_button = (Button) findViewById(R.id.save_button);
        home_button = (Button) findViewById(R.id.home_button);

        seekbar1 = (SeekBar) findViewById(R.id.seekbar1);
        seekbar2 = (SeekBar) findViewById(R.id.seekbar2);
        seekbar3 = (SeekBar) findViewById(R.id.seekbar3);
        seekbar4 = (SeekBar) findViewById(R.id.seekbar4);
        valve1 = (TextView) findViewById(R.id.valve1);
        valve2 = (TextView) findViewById(R.id.valve2);
        valve3 = (TextView) findViewById(R.id.valve3);
        valve4 = (TextView) findViewById(R.id.valve4);

        valve1.setVisibility(View.INVISIBLE);
        valve2.setVisibility(View.INVISIBLE);
        valve3.setVisibility(View.INVISIBLE);
        valve4.setVisibility(View.INVISIBLE);

        seekbar1.setVisibility(View.INVISIBLE);
        seekbar2.setVisibility(View.INVISIBLE);
        seekbar3.setVisibility(View.INVISIBLE);
        seekbar4.setVisibility(View.INVISIBLE);


        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ValveAdjustmentPage.this,HomePage.class);
                startActivity(intent);
            }
        });

        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        try {
            //addToHistory("Connecting to " + serverUri);
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    System.out.println("Your shits not working bro");
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }

        realm = null;
        try{
            realm = Realm.getDefaultInstance();
            final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
            if (!results_app.isEmpty()) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for(int i = 0; i < results_app.size(); i++){
                            if(i == 0){
                                seekbar1.setProgress((int)results_app.get(i).amount);
                            }else if(i == 1){
                                seekbar2.setProgress((int)results_app.get(i).amount);
                            }else if(i == 2){
                                seekbar3.setProgress((int)results_app.get(i).amount);
                            }else if(i == 3){
                                seekbar4.setProgress((int)results_app.get(i).amount);

                            }
                        }
                    }
                });
            }

        }finally{
            if(realm != null) realm.close();
        }




        realm = null;

        try{
            realm = Realm.getDefaultInstance();
            final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
            if (!results_app.isEmpty()) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for(int i = 0; i < results_app.size(); i++){
                            if(i == 0){
                                valve1.setVisibility(View.VISIBLE);
                                seekbar1.setVisibility(View.VISIBLE);
                                valve1.setText(results_app.get(i).appliance);
                            }else if(i == 1){
                                valve2.setVisibility(View.VISIBLE);
                                seekbar2.setVisibility(View.VISIBLE);
                                valve2.setText(results_app.get(i).appliance);
                            }else if(i == 2){
                                valve3.setVisibility(View.VISIBLE);
                                seekbar3.setVisibility(View.VISIBLE);
                                valve3.setText(results_app.get(i).appliance);
                            }else if(i == 3){
                                valve4.setVisibility(View.VISIBLE);
                                seekbar4.setVisibility(View.VISIBLE);
                                valve4.setText(results_app.get(i).appliance);
                            }
                        }
                    }
                });
            }

        }finally{
            if(realm != null) realm.close();
        }


        //int pval = 0;


       // if(seekbar1.getVisibility() == View.VISIBLE) {
            realm = null;
            seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                //int pval = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pval = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //write custom code to on start progress
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    try {
                        //final Appliance app = new Appliance(appliance, new Date(), 100, LoginPage.check_username, false);
                        realm = Realm.getDefaultInstance();
                        final RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", valve1.getText().toString()).findAll();
                        //final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
                        final Appliance appl = new Appliance(results.get(0).appliance,
                                results.get(0).date,
                                results.get(0).amount,
                                results.get(0).username,
                                results.get(0).isChanged);
                        //tView.setText(pval + "/" + seekBar.getMax());
                        if (pval != appl.amount) {
                            appl.isChanged = true;
                            publishMessage();
                        }
                        appl.amount = pval;

                        //final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", appl.appliance).findAll();
                                results.deleteAllFromRealm();
                                realm.insertOrUpdate(appl);

                            }
                        });
                    } finally {
                        if (realm != null) realm.close();
                    }
                }
            });
       // }

       // if(seekbar2.getVisibility() == View.VISIBLE) {
            realm = null;
            //int pval = 0;


            seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                //int pval = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pval = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //write custom code to on start progress
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    try {
                        //final Appliance app = new Appliance(appliance, new Date(), 100, LoginPage.check_username, false);
                        realm = Realm.getDefaultInstance();
                        final RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", valve2.getText().toString()).findAll();
                        final Appliance appl = new Appliance(results.get(0).appliance,
                                results.get(0).date,
                                results.get(0).amount,
                                results.get(0).username,
                                results.get(0).isChanged);
                        //tView.setText(pval + "/" + seekBar.getMax());
                        if (pval != appl.amount) {
                            appl.isChanged = true;
                        }
                        appl.amount = pval;
                        //final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", appl.appliance).findAll();
                                results.deleteAllFromRealm();
                                realm.insertOrUpdate(appl);

                            }
                        });
                    } finally {
                        if (realm != null) realm.close();
                    }
                }
            });

     //   }

     //   if(seekbar3.getVisibility() == View.VISIBLE) {
            realm = null;
            //int pval = 0;


            seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                //int pval = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pval = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //write custom code to on start progress
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    try {
                        //final Appliance app = new Appliance(appliance, new Date(), 100, LoginPage.check_username, false);
                        realm = Realm.getDefaultInstance();
                        final RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", valve3.getText().toString()).findAll();
                        final Appliance appl = new Appliance(results.get(0).appliance,
                                results.get(0).date,
                                results.get(0).amount,
                                results.get(0).username,
                                results.get(0).isChanged);
                        //tView.setText(pval + "/" + seekBar.getMax());
                        if (pval != appl.amount) {
                            appl.isChanged = true;
                        }
                        appl.amount = pval;
                        //final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", appl.appliance).findAll();
                                results.deleteAllFromRealm();
                                realm.insertOrUpdate(appl);

                            }
                        });
                    } finally {
                        if (realm != null) realm.close();
                    }
                }
            });

     //   }



     //   if(seekbar4.getVisibility() == View.VISIBLE) {
            realm = null;
            //int pval = 0;


            seekbar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                //int pval = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    pval = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //write custom code to on start progress
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    try {
                        //final Appliance app = new Appliance(appliance, new Date(), 100, LoginPage.check_username, false);
                        realm = Realm.getDefaultInstance();
                        final RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", valve4.getText().toString()).findAll();
                        final Appliance appl = new Appliance(results.get(0).appliance,
                                results.get(0).date,
                                results.get(0).amount,
                                results.get(0).username,
                                results.get(0).isChanged);
                        //tView.setText(pval + "/" + seekBar.getMax());
                        if (pval != appl.amount) {
                            appl.isChanged = true;
                        }
                        appl.amount = pval;
                        //final RealmResults<Appliance> results_app = realm.where(Appliance.class).contains("username", LoginPage.check_username).findAll();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", appl.appliance).findAll();
                                results.deleteAllFromRealm();
                                realm.insertOrUpdate(appl);

                            }
                        });
                    } finally {
                        if (realm != null) realm.close();
                    }
                }
            });

      //  }


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
        //valveAdjustment = findViewById(R.id.ValveAdjustment);

        // access the database for the appliances
//        valves = new ArrayList<ValveStatus>();
//        Realm realm = null;
//        try {
//            realm = Realm.getDefaultInstance();
//            RealmResults<Appliance> appliances = realm.where(Appliance.class).findAll();
//            for(Appliance app : appliances) {
//                valves.add(new ValveStatus(app.appliance, (int) app.amount));
//            }
//        } finally {
//            if(realm != null) realm.close();
//        }
//
//        // Create adapter passing in the dummy data
//        adapter = new MyAdapter(valves);
        // Attach the adapter to the recyclerview to populate items
        //valveAdjustment.setAdapter(adapter);
        // Set layout manager to position the items
        //valveAdjustment.setLayoutManager(new LinearLayoutManager(this));
    }
    public void publishMessage(){

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
            mqttAndroidClient.publish(publishTopic1, message);
            mqttAndroidClient.publish(publishTopic2, message);
            mqttAndroidClient.publish(publishTopic3, message);
        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}