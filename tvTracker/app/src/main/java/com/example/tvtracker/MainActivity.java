package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       
        //getAllButtons
        Button loginStart = (Button) findViewById(R.id.button_loginStart);
        loginStart.setOnClickListener(this);

        Button signUpStart = (Button)findViewById(R.id.button_signUpStart);
        signUpStart.setOnClickListener(this);

        Button logout = (Button) findViewById(R.id.button_logoutNotif);
        //logout.setOnClickListener(this);
    }

    public void openLoginActivity(){
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_loginStart:
                Intent intent = new Intent(this,login.class);
                startActivity(intent);
                break;
            case R.id.button_signUpStart:
                setContentView(R.layout.activity_sign_up);
                break;
            default:
                break;
        }
    }
}