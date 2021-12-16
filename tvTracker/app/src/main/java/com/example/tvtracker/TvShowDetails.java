package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvtracker.DB.User_dataQuery;
import com.example.tvtracker.REST.RestRequests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TvShowDetails extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TvAdapter mAdapter;// bridge between our data and recycler view (cant load all items at once in recyclerview, adapter puts as many as we need
    private RecyclerView.LayoutManager mLayoutManager; // assigns single items in our list
    private ImageView favStar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);

        mRecyclerView = findViewById(R.id.recycler_tvShows);
        CheckBox checkBox = new CheckBox(this);

        TextView tvShowTitle = (TextView) findViewById(R.id.tvShowTitle);
        TextView tvShowSummary = (TextView) findViewById(R.id.tvShowsDetails_Desc);
        TextView tvShowGenreList = (TextView) findViewById(R.id.tvShowDetails_Genres);

        ArrayList<TvShowItem> tvEpisodes = new ArrayList<TvShowItem>();

        SharedPreferences sharedPref = getSharedPreferences("userPref", TvShowDetails.this.MODE_PRIVATE);
        int tvShowId = sharedPref.getInt("tvShowId", 0);

        RestRequests restRequests = new RestRequests(TvShowDetails.this);

        restRequests.getEpisodeList(tvShowId, new RestRequests.EpisodeListResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(TvShowDetails.this, "Error at TvShowDetails request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(ArrayList<TvEpisode> result) {
                HashMap<Integer, Boolean> episodeListMap = new HashMap<>();

                for (int i = 0; i < result.size(); i++){
                    TvEpisode tvEpisode = result.get(i);
                    //create the hash map
                    episodeListMap.put(tvEpisode.episodeId, false);
                }
                //create array list of episode ids that are watched
                ArrayList<Integer> watchedEpisodes = User_dataQuery.episodesWatched(userId, tvShowId);
                //iterate through our hashmap and change the values in the hashmap to true if the episode is marked as watched
                //in the database and is referenced in the array
                //we cannot save the hashmap to the shared prefs, so we need to convert it to a Set of strings
                for (Integer i : watchedEpisodes) {
                    episodeListMap.put(i, true);
                }

                FileIO.serialize2(episodeListMap, TvShowDetails.this);

                for (int i = 0; i < result.size(); i++) {
                    TvEpisode tvEpisode = result.get(i);


                    checkBox.setSelected(episodeListMap.get(tvEpisode.episodeId));
                    Toast.makeText(TvShowDetails.this, String.valueOf(checkBox.isSelected()), Toast.LENGTH_SHORT).show();
//                    if(episodeListMap.get(tvEpisode.episodeId)) {
//                        Toast.makeText(TvShowDetails.this, String.valueOf(episodeListMap.get(tvEpisode.episodeId)), Toast.LENGTH_SHORT).show();
//                        checkBox.setChecked(true);
//                    }

                    String episodeNr = "S";
                    episodeNr += String.valueOf(tvEpisode.seasonNr) + "E" + String.valueOf(tvEpisode.episodeNr);

                    //ArrayAdapter arrayAdapter = new ArrayAdapter(TvShowDetails.this, android.R.layout.simple_list_item_multiple_choice, testArray);
                    //list.setAdapter(arrayAdapter);

                    tvEpisodes.add(new TvShowItem(tvEpisode.name, episodeNr, tvEpisode.getEpisodeId(), checkBox));
                    mRecyclerView.setHasFixedSize(true); // if we know
                    mLayoutManager = new LinearLayoutManager(TvShowDetails.this);
                    mAdapter = new TvAdapter(tvEpisodes);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);


                    episodeListMap.put(tvEpisode.episodeId, true);

                   /* for (Integer key : episodeListMap.keySet()) {
                        if(episodeListMap.get(key)) {
                            checkBox.setChecked(true);
                        }
                    }*/


                    /*mAdapter.setOnItemClickListenerTv(new TvAdapter.OnItemClickListener()) {*/
                    mAdapter.setOnItemCheckedChangeListener(new TvAdapter.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(int position) {
                            Toast.makeText(TvShowDetails.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                            TvShowItem currentItem = tvEpisodes.get(position);
                            int currEpisodeId= result.get(position).getEpisodeId();

                            CheckBox box = currentItem.getBox();

                            HashMap<Integer, Boolean> episodeListMap = FileIO.deserialize2(TvShowDetails.this);

                            if (!episodeListMap.isEmpty()){

                                if (episodeListMap.get(currEpisodeId) == false) {
                                    box.setChecked(true);
                                    User_dataQuery.saveEpisode(userId,tvShowId, currEpisodeId);
                                    episodeListMap.put(currEpisodeId, true);
                                    FileIO.serialize2(episodeListMap, TvShowDetails.this);


                                } else {
                                    box.setChecked(false);
                                    User_dataQuery.delWatchedEpisode(userId, tvShowId, currEpisodeId);
                                    episodeListMap.put(currEpisodeId, false);
                                    FileIO.serialize2(episodeListMap, TvShowDetails.this);
                                }
                            }
                    /*mAdapter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()){
                        @Override
                        public void onBoxClick(int position) {

                            TvShowItem currentItem = tvEpisodes.get(position);
                            int currEpisodeId= result.get(position).getEpisodeId();

                            CheckBox box = currentItem.getBox();
                            HashMap<Integer, Boolean> episodeListMap = FileIO.deserialize2(TvShowDetails.this);

                            if (!episodeListMap.isEmpty()){

                                if (episodeListMap.get(currEpisodeId) == false) {
                                    box.setChecked(true);
                                    User_dataQuery.saveEpisode(userId,tvShowId, currEpisodeId);
                                    episodeListMap.put(currEpisodeId, true);
                                    FileIO.serialize2(episodeListMap, TvShowDetails.this);


                                } else {
                                    box.setChecked(false);
                                    User_dataQuery.delWatchedEpisode(userId, tvShowId, currEpisodeId);
                                    episodeListMap.put(currEpisodeId, false);
                                    FileIO.serialize2(episodeListMap, TvShowDetails.this);
                                }
                            }
                        }
                    });*/
                         }


            });
        }

        favStar = findViewById(R.id.TvShowFav);
        if (User_dataQuery.showIsFavorite(userId, tvShowId)) {
            favStar.setImageResource(R.drawable.ic_baseline_star_24);
        } else {
            favStar.setImageResource(R.drawable.ic_baseline_star_outline_24);
        }

        //TODO add  Favorite/Unfavorite functionality to the button
     /*   favStar.setOnClickListener(new View.OnClickListener() {
            boolean isFavorite = true;d
            @Override
            public void onClick(View view) {
                                }
            }
        });*/
            }
        });

        restRequests.getShowInfo(tvShowId, new RestRequests.TvShowInfoResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(TvShowDetails.this, "Error at TvShowDetails request", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String ShowName, String ShowDescription, String ShowGenres) {
                tvShowTitle.setText(ShowName);
                tvShowSummary.setText(ShowDescription);
                tvShowGenreList.setText(ShowGenres);
            }
        });


}}