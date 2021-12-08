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
    private Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.button_login);
        signUp = (Button)  findViewById(R.id. button_signUp);

        login.setOnClickListener(v -> openLoginActivity());
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}