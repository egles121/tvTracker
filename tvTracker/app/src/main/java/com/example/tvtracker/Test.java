package com.example.tvtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;
import android.content.Intent;

import com.example.tvtracker.REST.RestRequests;

import java.util.ArrayList;

public class Test extends AppCompatActivity {
private RecyclerView mRecyclerView;
private ScrollView mScrollView;
private RecyclerView.Adapter mAdapter;// bridge between our data and recycler view (cant laod all items at once in recyclerview, adapter puts as many as we need
private RecyclerView.LayoutManager mLayoutManager; // assigns single items in our list
    private Button favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        favorites = (Button) findViewById(R.id.button_home_fav);
        favorites.setOnClickListener(v -> favoritesScreen());

        Button btn = new Button(this);
        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem("test", "test2", btn));

        RestRequests restRequests = new RestRequests(Test.this);

        restRequests.getTvShowList("a", new RestRequests.TvShowListResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(Test.this, "error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(TvShow tvShow) {
                Toast.makeText(Test.this, tvShow.toString(), Toast.LENGTH_LONG).show();
            }
        });

        /*for (int i = 250; i <= 260; i++) {
            //loop through Ids
            exampleList.add(new ExampleItem(TvShowsQuery.getName(i), "2005-2013", btn));
         }*/
        //exampleList.add(new cardsExampleItem(TvShowsQuery.getName(251), "2005-2013", btn));
        //exampleList.add(new cardsExampleItem(TvShowsQuery.getName(252), "2008-2013", btn));
        //exampleList.add(new cardsExampleItem("Squid Game", "2021", btn));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        //say when reach the end of the scroll
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    Toast.makeText(Test.this, "Last", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    public void favoritesScreen() {
        Intent intent = new Intent (this, Favorites.class);
        startActivity(intent);
    }

}