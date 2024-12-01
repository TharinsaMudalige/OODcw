package com.example.oodcw;

import java.time.LocalDateTime;

public class ArticleInteractions {
    private int userID;       // Foreign key linking to Users table
    private String articleID; // Foreign key linking to Articles table
    private boolean liked;
    private LocalDateTime timeViewed;

    public ArticleInteractions(int userID, String articleID, boolean liked, LocalDateTime timeViewed) {
        this.userID = userID;
        this.articleID = articleID;
        this.liked = liked;
        this.timeViewed = timeViewed;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public LocalDateTime getTimeViewed() {
        return timeViewed;
    }

    public void setTimeViewed(LocalDateTime timeViewed) {
        this.timeViewed = timeViewed;
    }
}
