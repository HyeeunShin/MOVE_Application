package com.example.move;

public class Movie {
    private String title;
    private String poster_path;
    private String release_date;
    private String vote_average;



    public Movie(String title, String poster_path,  String release_date, String vote_average) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;


    }

    public String getTitle() {
        return title;
    }
    public String getPoster_path() { return poster_path;}
    public String getRelease_date() {
        return release_date;
    }
    public String getVote_average() {return vote_average;}
}