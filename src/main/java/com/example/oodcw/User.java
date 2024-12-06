package com.example.oodcw;


import java.util.ArrayList;
import java.util.List;

public class User {
    private int userID;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private String userPassword;

    private final List<ArticleInteractions> articleInteractions; //User has a list of interacted articles - composition

    public User(int userID, String firstName, String lastName, String userName, String userPassword) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.articleInteractions = new ArrayList<>();
    }

    public User(String firstName, String lastName, String userName, String userPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.articleInteractions = new ArrayList<>();
    }

    //Getters and Setters

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword(){
        return userPassword;
    }

    //Get all the interaction for the user
    public List<ArticleInteractions> getArticleInteractions() {
        return articleInteractions;
    }

    //Add interactions to the list
    public void addArticleInteractions(ArticleInteractions article) {
        if(!articleInteractions.contains(article)){
            articleInteractions.add(article);
        }
    }
}
