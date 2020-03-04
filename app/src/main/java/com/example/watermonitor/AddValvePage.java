package com.example.watermonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watermonitor.models.Appliance;

import java.util.Date;

import io.realm.Realm;

public class AddValvePage extends AppCompatActivity {
    Button addValve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_valve_page);

        addValve = (Button) findViewById(R.id.final_add_valve_button);
        addValve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText applianceName = (EditText) findViewById(R.id.appliance_input);
                String appliance = applianceName.getText().toString();
                final Appliance app = new Appliance(appliance, new Date(), 100, "JaneDoe");

                Realm realm = null;
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
    }
}
