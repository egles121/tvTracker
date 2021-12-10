package com.example.tvtracker;

import android.widget.Button;

public class ExampleItem {
    private String mtx1;
    private String mtx2;
    private Button mbtn;


    public ExampleItem(String txt1, String txt2, Button btn){
        mtx1=txt1;
        mtx2 = txt2;
        mbtn=btn;
    }

    public String getText1(){
        return mtx1;
    }

    public String getText2(){
        return mtx2;
    }

    public Button getMbtn() {return mbtn;}

}
