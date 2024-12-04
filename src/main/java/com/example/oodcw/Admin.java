package com.example.oodcw;

import java.util.ArrayList;
import java.util.List;

public class Admin {

    private int adminId;
    private String adminUserName;
    private String adminPassword;
    private List<Article> viewArticles;
    private List<User> viewUsers;


    public Admin(int adminId, String adminUserName, String adminPassword) {
        this.adminId = adminId;
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;
        this.viewArticles = new ArrayList<>();
        this.viewUsers = new ArrayList<>();
    }

    public Admin(String adminName, String adminPassword) {
        this.adminUserName = adminName;
        this.adminPassword = adminPassword;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public List<Article> getViewArticles() {
        return viewArticles;
    }

    public void addViewArticles(List<Article> articles) {
        this.viewArticles.addAll(articles);
    }

    public List<User> getViewUsers() {
        return viewUsers;
    }

    public void addViewUsers(List<User> articles) {
        this.viewUsers.addAll(articles);
    }
}
