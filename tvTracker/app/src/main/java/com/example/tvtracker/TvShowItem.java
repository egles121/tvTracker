package com.example.tvtracker;

import android.widget.ImageView;

public class TvShowItem {
    private String episodeInfo;
    public ImageView check;


    public TvShowItem(String episodeInfo, ImageView check){
        this.episodeInfo =episodeInfo;
        this.check = check;
    }

    public String getText1(){
        return episodeInfo;
    }


    public ImageView getBox() {
        return check;
    }
}
