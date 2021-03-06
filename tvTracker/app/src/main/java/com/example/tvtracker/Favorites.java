package com.example.tvtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvtracker.DB.User_dataQuery;
import com.example.tvtracker.REST.RestRequests;

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
        int userId = bundle.getInt("userId");
        userGreeting(nickname);

        logout = (Button) findViewById(R.id.button_logoutFavorites);
        logout.setOnClickListener(v -> logoutFunction(this));

        notifications = (ImageButton) findViewById(R.id.button_notificationsFav);
        notifications.setOnClickListener(v -> userNotifications());

        main = (Button) findViewById(R.id.button_mainFav);
        main.setOnClickListener(v -> mainScreen(userId, nickname));

        mRecyclerView = findViewById(R.id.favorite_recycler);

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

                            //make the request to retrieve the TV show ID for the name we retrieved from the list item we checked
                            restRequests.getShowId(tvShowName, new RestRequests.IDResponseListener() {
                                @Override
                                public void onError(String message) {
                                    Toast.makeText(Favorites.this, "Error in HomeActivity getShowId request", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onResponse(int id) {
                                    //go to TV show page

                                    Intent intent = new Intent(Favorites.this, TvShowDetails.class);
                                    intent.putExtra("userId", userId);
                                    intent.putExtra("tvShowId", id);
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
        TextView nick = (TextView) findViewById(R.id.greeting2);
        nick.setText(name);

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

    public void mainScreen(int userId, String username) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("usernameInput", username);
        startActivity(intent);
    }

    public void deleteFavorite(int position) {
        list.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
