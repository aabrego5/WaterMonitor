package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;

public class LoginPage extends AppCompatActivity {
    public static final String USERNAME = "com.example.watermoniter.username";
    Button login_button;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Realm.init(this);
        realm = Realm.getDefaultInstance(); // opens "myrealm.realm"

        try{
        login_button = (Button) findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent=new Intent(LoginPage.this,HomePage.class);

                // get the entered username and send it to the home page
                EditText text = (EditText) findViewById(R.id.username_input);
                String username = text.getText().toString();
                intent.putExtra(USERNAME, username);

                startActivity(intent);


            }
        });
        } finally {
            realm.close();
        }
    }
}
