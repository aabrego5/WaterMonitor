package com.example.watermonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watermonitor.models.Appliance;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddValvePage extends AppCompatActivity {
    Button addValve, home, delete;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_valve_page);

        addValve = (Button) findViewById(R.id.final_add_valve_button);
        home = (Button) findViewById(R.id.home);
        delete = (Button) findViewById(R.id.delete);
        final EditText applianceName = (EditText) findViewById(R.id.appliance_input);

        addValve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String appliance = applianceName.getText().toString();
                final Appliance app = new Appliance(appliance, new Date(), 100, LoginPage.check_username);

                realm = null;
                try {
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(app);

                        }
                    });
                } finally {
                    if(realm != null) realm.close();
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddValvePage.this,HomePage.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String appliance = applianceName.getText().toString();


                realm = null;
                try {
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            //RealmResults<Appliance> results = realm.where(Appliance.class).contains("username", LoginPage.check_username).contains("appliance", appliance).findAll();


                            //if(!results.isEmpty()){
                            RealmResults<Appliance> results = realm.where(Appliance.class).equalTo("username", LoginPage.check_username).equalTo("appliance", appliance).findAll();
                            results.deleteAllFromRealm();
                        }
                    });
                } finally {
                    if(realm != null) realm.close();
                }
            }
        });

    }
}
