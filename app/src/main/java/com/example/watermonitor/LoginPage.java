package com.example.watermonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.watermonitor.models.Usage;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class LoginPage extends AppCompatActivity {
    //public static final String USERNAME = "com.example.watermoniter.username";
    Button login_button, create_account_button;
    EditText username_text;
    EditText password_text;
    Realm realm;
    Usage user;
    private String check_username;
    private String check_password;
    public static String check_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login_button = (Button) findViewById(R.id.login_button);
        create_account_button = (Button) findViewById(R.id.create_account_button);
        username_text = (EditText) findViewById(R.id.username_input);
        password_text = (EditText) findViewById(R.id.password_input);

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, CreateAccountPage.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                realm = null;
                try {
                    realm = Realm.getDefaultInstance();
                    // get the entered username and send it to the home page

                    check_username = username_text.getText().toString();
                    check_password = password_text.getText().toString();

                    RealmResults<Usage> results = realm.where(Usage.class).contains("username", check_username).findAll();


                    if (!results.isEmpty()) {
                            check_name = results.get(0).user_name;
                            //username and password match
                            Intent intent = new Intent(LoginPage.this, HomePage.class);
                            startActivity(intent);

                    }
                }finally {
                    if(realm != null) realm.close();
                }

            }
        });
    }
}
