package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.watermonitor.models.Usage;

import io.realm.Realm;

public class LoginPage extends AppCompatActivity {
    //public static final String USERNAME = "com.example.watermoniter.username";
    Button login_button;
    EditText username_text;
    EditText password_text;
    Realm realm;
    Usage user;
    public static String check_username;
    public static String check_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        user = realm.createObject(Usage.class);
        realm.commitTransaction();


        login_button = (Button) findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // get the entered username and send it to the home page
                username_text = (EditText) findViewById(R.id.username_input);
                password_text = (EditText) findViewById(R.id.password_input);
                check_username = username_text.getText().toString();
                check_password = password_text.getText().toString();
                if(user.username.equals(check_username)){
                    if(user.password.equals(check_password)){
                        //username and password match
                        Intent intent=new Intent(LoginPage.this,HomePage.class);
                        startActivity(intent);
                    }
                }

            }
        });


    }
}
