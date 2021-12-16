package com.example.tvtracker;

import android.widget.CheckBox;
import android.widget.ImageView;

public class TvShowItem {
    private String episodeInfo;
    private String episodeNo;
    private int episodeId;
    public CheckBox check;
    private boolean isSelected;



    public TvShowItem(String episodeInfo, String episodeNo, int episodeId, CheckBox check){
        this.episodeInfo = episodeInfo;
        this.episodeNo = episodeNo;
        this.check = check;
        this.episodeId = episodeId;
        isSelected = false;

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }
}
