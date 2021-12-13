package com.example.tvtracker;


import android.widget.ImageView;

public class ExampleItem {
    private String mtx1;
    private String mtx2;
    public ImageView favoriteStar;


    public ExampleItem(String txt1, String txt2, ImageView star){
        mtx1=txt1;
        mtx2 = txt2;
        favoriteStar = star;
    }

    public String getText1(){
        return mtx1;
    }

    public String getText2(){
        return mtx2;
    }

    public ImageView getStar() {return favoriteStar;}

}
