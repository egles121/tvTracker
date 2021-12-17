package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tvtracker.DB.TvShowsQuery;
import com.example.tvtracker.DB.User_dataQuery;
import com.example.tvtracker.REST.RequestSingleton;
import com.example.tvtracker.REST.RestRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class Favorites extends AppCompatActivity {
    private TextView greeting;
    private Button logout;
    private ImageButton notifications;
    private Button main;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;// bridge between our data and recycler view (cant laod all items at once in recyclerview, adapter puts as many as we need
    private RecyclerView.LayoutManager mLayoutManager; // assigns single items in our list
    private ArrayList<ExampleItem> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Bundle bundle = getIntent().getExtras();
        String nickname = bundle.getString("usernameInput");
        userGreeting(nickname);

        logout = (Button) findViewById(R.id.button_logoutFavorites);
        logout.setOnClickListener(v -> logoutFunction(this));

        notifications = (ImageButton) findViewById(R.id.button_notificationsFav);
        notifications.setOnClickListener(v -> userNotifications());

        main = (Button) findViewById(R.id.button_mainFav);
        main.setOnClickListener(v -> mainScreen());

        mRecyclerView = findViewById(R.id.favorite_recycler);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Favorites.this);
        int userId = sharedPref.getInt("userId", 0);

        //Return a TV show name
        RestRequests restRequests = new RestRequests(Favorites.this);
        //get the favorite tv shows array from the database
        ArrayList<Integer> favoriteTvShows = User_dataQuery.favoriteShows(userId);

        ImageView favoriteStar = new ImageView(this);

        for (int i = 0; i < favoriteTvShows.size(); i++) {
            restRequests.getShowName(favoriteTvShows.get(i), new RestRequests.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(Favorites.this, "Error at Favorites request", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(String tvShowName) {
                    list.add(new ExampleItem(tvShowName, "hello", favoriteStar));
                    mRecyclerView.setHasFixedSize(true); // if we know
                    mLayoutManager = new LinearLayoutManager(Favorites.this);
                    mAdapter = new ExampleAdapter(list);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                    mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
                        @Override
                        public void onStarClick(int position) {
                            //get the string value of TV show name from each list item we unselect
                            //String unselected = (String) (list.getItemAtPosition(position));
                            ExampleItem currentItem = list.get(position);
                            String unselected = currentItem.getText1();

                            //make the request to retrieve the TV show ID for the name we retrieved from the list item we checked
                            restRequests.getShowId(unselected, new RestRequests.IDResponseListener() {
                                @Override
                                public void onError(String message) {
                                    Toast.makeText(Favorites.this, "Error in HomeActivity getShowId request", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onResponse(int id) {
                                    //delete the unchecked tv show from the user_data table
                                    User_dataQuery.delFavoriteShow(userId, id);
                                }
                            });

                            deleteFavorite(position);
                        }

                        public void onItemClick(int position) {
                            ExampleItem currentItem = list.get(position);
                            String tvShowName = currentItem.getText1();
                            Toast.makeText(Favorites.this, tvShowName, Toast.LENGTH_LONG).show();
                            //make the request to retrieve the TV show ID for the name we retrieved from the list item we checked
                            restRequests.getShowId(tvShowName, new RestRequests.IDResponseListener() {
                                @Override
                                public void onError(String message) {
                                    Toast.makeText(Favorites.this, "Error in HomeActivity getShowId request", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onResponse(int id) {
                                    //go to TV show page
                                    Toast.makeText(Favorites.this, "The ID is " + String.valueOf(id), Toast.LENGTH_LONG).show();

                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("tvShowId", id);
                                    editor.apply();

                                    Intent intent = new Intent(Favorites.this, TvShowDetails.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    //change greeting based on hour of day
    public void userGreeting(String name) {
        greeting = (TextView) findViewById(R.id.greeting);
        Calendar c = Calendar.getInstance();
        int currentTime = c.get(Calendar.HOUR_OF_DAY);
        TextView nickname = (TextView) findViewById(R.id.greeting2);
        nickname.setText(name);

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

    public void logoutFunction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
    }

    public void userNotifications() {
        Intent intent = new Intent(this, Notifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void mainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void deleteFavorite(int position) {
        list.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
