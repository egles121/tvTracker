package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tvtracker.DB.TvShowsQuery;

import java.util.Calendar;

public class favorites extends AppCompatActivity {
    private TextView greeting;
    private Button logout;
    private ImageButton notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        userGreeting();

        logout = (Button) findViewById(R.id. button_logoutFavorites);
        logout.setOnClickListener(v -> userLogout());

        notifications = (ImageButton) findViewById(R.id.button_notificationsFav);
        notifications.setOnClickListener(v -> userNotifications());

        TextView tx1 = findViewById(R.id. list_tvshow1);
        tx1.setText(TvShowsQuery.getName());

    }

    //change greeting based on hour of day
    public void userGreeting(){
        greeting = (TextView) findViewById(R.id.greeting);
        Calendar c = Calendar.getInstance();
        int currentTime = c.get(Calendar.HOUR_OF_DAY);

        if (currentTime >= 5 && currentTime < 10) {
            greeting.setText("Good morning,");
        } else if (currentTime >= 10 && currentTime < 13) {
            greeting.setText("Good day,");
        } else if (currentTime >= 13 && currentTime < 17) {
            greeting.setText("Good afternoon,");
        } else if (currentTime >= 17 && currentTime < 21) {
            greeting.setText("Good evening,");
        } else {
            greeting.setText("Good night,");
        }
    }

    public void userLogout(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void userNotifications(){
        Intent intent = new Intent(this, notifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivity(intent);
    }




}