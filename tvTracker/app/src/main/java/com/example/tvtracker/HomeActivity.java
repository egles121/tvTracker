package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvtracker.DB.User_dataQuery;
import com.example.tvtracker.REST.RestRequests;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();
        int userId = bundle.getInt("userId");
        String username = bundle.getString("usernameInput");

        Button favorites = (Button) findViewById(R.id.button_home_fav);
        favorites.setOnClickListener(v -> favoritesScreen(userId, username));

        ListView list = (ListView) findViewById(R.id. home_listView) ;

        RestRequests restRequests = new RestRequests(HomeActivity.this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = search.getText().toString();
                Toast.makeText(HomeActivity.this, searchValue, Toast.LENGTH_LONG).show();
            restRequests.getTvShowList(searchValue, new RestRequests.TvShowListResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(HomeActivity.this, "error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(ArrayList<String> result) {
                ArrayAdapter arrayAdapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_multiple_choice, result);
                list.setAdapter(arrayAdapter);


            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // change the checkbox state
                CheckedTextView checkedTextView = ((CheckedTextView)view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                if(checkedTextView.isChecked()) {
                    //get the string value of TV show name from each list item
                    String selected = (String) (list.getItemAtPosition(position));

                    //make the request to retrieve the TV show ID for the name we retrieved from the list item we checked
                    restRequests.getShowId(selected, new RestRequests.IDResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(HomeActivity.this, "Error in HomeActivity getShowId request", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(int id) {
                            //save the favorited tv show id and user id in the user_data table
                            User_dataQuery.saveFavoriteShow(userId,id);
                        }
                    });
                } else {
                    //get the string value of TV show name from each list item we unselect
                    String unselected = (String) (list.getItemAtPosition(position));

                    //make the request to retrieve the TV show ID for the name we retrieved from the list item we checked
                    restRequests.getShowId(unselected, new RestRequests.IDResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(HomeActivity.this, "Error in HomeActivity getShowId request", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(int id) {
                            //delete the unchecked tv show from the user_data table
                            User_dataQuery.delFavoriteShow(userId,id);
                        }
                    });
                }
            }
        });


    }

    public void favoritesScreen(int userId, String username) {
        Intent intent = new Intent (this, Favorites.class);
        intent.putExtra("userId", userId);
        intent.putExtra("usernameInput", username);
        startActivity(intent);
    }

}
