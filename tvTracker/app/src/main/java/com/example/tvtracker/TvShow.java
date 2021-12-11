package com.example.tvtracker;

public class TvShow {
    Double score;
    Show show;

    public TvShow() {
    }

    public TvShow(Double score, Show show) {
        this.score = score;
        this.show = show;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
