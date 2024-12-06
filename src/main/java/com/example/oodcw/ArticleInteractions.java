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

    public String getArticleID() {
        return articleID;
    }

    public boolean isLiked() {
        return liked;
    }

}
