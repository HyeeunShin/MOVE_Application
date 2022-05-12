package com.example.move;

public class ReviewItems {

    private int id;
    private String title;
    private String writeDate;
    private int rating;
    private String watchDate;
    private String posterPath;
    private String shortReview;
    private String review;

    public ReviewItems(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public int getRating() { return rating; }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getShortReview() {
        return shortReview;
    }

    public void setShortReview(String shortReview) {
        this.shortReview = shortReview;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getWatchDate() { return watchDate; }

    public void setWatchDate(String watchDate) { this.watchDate = watchDate; }
}
