package com.example.tvtracker;

import android.widget.CheckBox;
import android.widget.ImageView;

public class TvShowItem {
    private String episodeInfo;
    private String episodeNo;
    public CheckBox check;


    public TvShowItem(String episodeInfo, String episodeNo, CheckBox check){
        this.episodeInfo = episodeInfo;
        this.episodeNo = episodeNo;
        this.check = check;
    }

    public String getText1(){
        return episodeInfo;
    }

    public String getText2(){
        return episodeNo;
    }

    public CheckBox getBox() {
        return check;
    }
}
