package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.watermonitor.models.Usage;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CreateAccountPage extends AppCompatActivity {

    Button create_account_button;
    Realm realm;
    Usage user;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);

        //Realm.init(this);
        //realm = Realm.getDefaultInstance(); // opens "myrealm.realm"

        //realm.beginTransaction();

        //realm.commitTransaction();


        create_account_button = findViewById(R.id.create_account_button);
        final EditText text_name = findViewById(R.id.user_name_input);
        final EditText text_username = findViewById(R.id.username_input);
        final EditText text_password = findViewById(R.id.password_input);


        create_account_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                username = text_username.getText().toString();
                realm = null;
                try {
                    realm = Realm.getDefaultInstance();
                    user = new Usage(text_username.getText().toString(), text_password.getText().toString(), text_name.getText().toString());
                    RealmResults<Usage> results = realm.where(Usage.class).contains("username",username).findAll();

                    if (results.isEmpty()) {

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                realm.insertOrUpdate(user);
                            }
                        });

                        Intent intent = new Intent(CreateAccountPage.this, LoginPage.class);
                        startActivity(intent);
                    }
                }finally{
                    if(realm != null) realm.close();
                }

            }
        });
    }
}
