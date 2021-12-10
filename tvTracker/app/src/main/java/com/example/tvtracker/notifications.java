package com.example.tvtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class notifications extends MainActivity{
private ImageButton favorites;
private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

       favorites = (ImageButton) findViewById(R.id.button_favoritesNotif);
       favorites.setOnClickListener(v -> userFavorites());

        logout = (Button) findViewById(R.id.button_logoutNotif);
        //logout.setOnClickListener(v -> functions.logoutFunction(this));
    }

    public void userFavorites(){
        Intent intent = new Intent(this, favorites.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivity(intent);

    }

}