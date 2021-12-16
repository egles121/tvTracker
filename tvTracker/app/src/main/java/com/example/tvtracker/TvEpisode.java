package com.example.tvtracker;

public class TvEpisode {
    String name;
    int seasonNr;
    int episodeNr;
    int episodeId;



    public TvEpisode(String name, int seasonNr, int episodeNr, int episodeId) {
        this.name = name;
        this.seasonNr = seasonNr;
        this.episodeNr = episodeNr;
        this.episodeId = episodeId;
    }

    public TvEpisode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeasonNr() {
        return seasonNr;
    }

    public void setSeasonNr(int seasonNr) {
        this.seasonNr = seasonNr;
    }

    public int getEpisodeNr() {
        return episodeNr;
    }

    public void setEpisodeNr(int episodeNr) {
        this.episodeNr = episodeNr;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }
}
