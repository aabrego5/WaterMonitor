package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.watermonitor.models.Usage;

import io.realm.Realm;

public class CreateAccountPage extends AppCompatActivity {

    Button create_account_button;
    Realm realm;
    Usage user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);

        Realm.init(this);
        realm = Realm.getDefaultInstance(); // opens "myrealm.realm"

        realm.beginTransaction();
        user = realm.createObject(Usage.class);
        realm.commitTransaction();


        create_account_button = findViewById(R.id.create_account_button);
        final EditText text_name = findViewById(R.id.user_name_input);
        final EditText text_username = findViewById(R.id.username_input);
        final EditText text_password = findViewById(R.id.password_input);
        user.user_name = text_name.getText().toString();
        user.username = text_username.getText().toString();
        user.password = text_password.getText().toString();

        create_account_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(!text_username.equals(user.user_name)){
                    user.user_name = text_name.getText().toString();
                    user.username = text_username.getText().toString();
                    user.password = text_password.getText().toString();
                    Intent intent=new Intent(CreateAccountPage.this, LoginPage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
