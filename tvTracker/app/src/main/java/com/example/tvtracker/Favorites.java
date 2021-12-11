package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tvtracker.DB.TvShowsQuery;
import com.example.tvtracker.REST.RequestSingleton;
import com.example.tvtracker.REST.RestRequests;

import java.util.Calendar;

public class Favorites extends AppCompatActivity {
    private TextView greeting;
    private Button logout;
    private ImageButton notifications;
    private Button main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        userGreeting();

        logout = (Button) findViewById(R.id. button_logoutFavorites);
        logout.setOnClickListener(v -> logoutFunction(this));

        notifications = (ImageButton) findViewById(R.id.button_notificationsFav);
        notifications.setOnClickListener(v -> userNotifications());

        // TextView tx1 = findViewById(R.id. list_tvshow1);
        TextView tx2 = findViewById(R.id. list_tvshow2);
        TextView tx3 = findViewById(R.id. list_tvshow3);
        // tx1.setText(TvShowsQuery.getName(251));

        //RequestQueue requestQueue = Volley.newRequestQueue(Favorites.this);


        String url = "https://api.tvmaze.com/shows/1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    tx2.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tx2.setText("something wrong");
            }
        });
        RequestSingleton.getInstance(Favorites.this).addToRequestQueue(request);

        //Return a TV show name
        RestRequests restRequests = new RestRequests(Favorites.this);

        restRequests.getShowName(3, new RestRequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(Favorites.this, "Error23", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onResponse (String tvShowName) {
                //Toast.makeText(Favorites.this, "Error23", Toast.LENGTH_LONG).show();
                tx3.setText(tvShowName);
            }
        });



        //tx3.setText(RestRequests.getShowName(25, Favorites.this));
        //Toast.makeText(Favorites.this, RestRequests.getShowName(25, Favorites.this), Toast.LENGTH_LONG).show();
        //String title = getApplicationContext().toString();
        //Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();

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

    public void logoutFunction (Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
    }

    public void userNotifications(){
        Intent intent = new Intent(this, Notifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivity(intent);
    }




}