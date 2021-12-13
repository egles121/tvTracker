package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TvShowDetails extends AppCompatActivity {
    private ArrayList<TvShowItem> list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TvShowAdapter mAdapter;// bridge between our data and recycler view (cant load all items at once in recyclerview, adapter puts as many as we need
    private RecyclerView.LayoutManager mLayoutManager; // assigns single items in our list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);

        mRecyclerView = findViewById(R.id.recycler_tvShows);
        ImageView checkBox = new ImageView(this);
        TextView tvShowTitle = (TextView) findViewById(R.id.tvShowTitle);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(TvShowDetails.this);
        int tvShowId = sharedPref.getInt("tvShowId", 0);

        RestRequests restRequests = new RestRequests(TvShowDetails.this);

        restRequests.getShowName(tvShowId, new RestRequests.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(TvShowDetails.this, "Error at Favorites request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String tvShowName) {
               tvShowTitle.setText(tvShowName);
                /*list.add(new TvShowItem(tvShowName, checkBox));
                mRecyclerView.setHasFixedSize(true); // if we know
                mLayoutManager = new LinearLayoutManager(TvShowDetails.this);
                mAdapter = new TvShowAdapter(list);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);*/
            }
        });

    }
}