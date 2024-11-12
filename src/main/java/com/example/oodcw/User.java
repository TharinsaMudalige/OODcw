package com.example.oodcw;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String userID;  //the username of the user
    private String userPassword;
    private List<String> likedArticles;  //stores a list of liked articles

    public User(String userID, String userPassword) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.likedArticles = new ArrayList<>();
    }

    //Getters and Setters
    public String getUserID() {
        return userID;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public List<String> getLikedArticles() {
        return likedArticles; //return a list of all the liked articles
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void addLikedArticles(String article) {
        likedArticles.add(article);  //Adding a liked article to the list
    }





}
