package com.example.oodcw;


public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String userName;  //the username of the user
    private String userPassword;

    public User(int userID, String firstName, String lastName, String userName, String userPassword) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(String userPassword, String userName, String firstName, String lastName) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
