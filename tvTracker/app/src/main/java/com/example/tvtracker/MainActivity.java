package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton notif;
    private Button login;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.button_loginStart);
        signup = (Button)  findViewById(R.id.button_signUpStart);

        login.setOnClickListener(v -> openLoginActivity());
        signup.setOnClickListener(v -> openSignUpActivity());
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void openSignUpActivity(){
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }
}