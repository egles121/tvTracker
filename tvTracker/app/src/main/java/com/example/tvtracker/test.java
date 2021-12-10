package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.tvtracker.DB.TvShowsQuery;

import java.util.ArrayList;

public class test extends AppCompatActivity {
private RecyclerView mRecyclerView;
private RecyclerView.Adapter mAdapter;// bridge between our data and recycler view (cant laod all items at once in recyclerview, adapter puts as many as we need
private RecyclerView.LayoutManager mLayoutManager; // assigns single items in our list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btn = new Button(this);
        ArrayList<cardsExampleItem> exampleList = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            //loop through Ids
            //exampleList.add(new cardsExampleItem(TvShowsQuery.getName(i), "2005-2013", btn));
        }
        exampleList.add(new cardsExampleItem(TvShowsQuery.getName(), "2005-2013", btn));
        exampleList.add(new cardsExampleItem(TvShowsQuery.getName(), "2008-2013", btn));
        //exampleList.add(new cardsExampleItem("Squid Game", "2021", btn));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new exampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void createMovieList(){

    }
}