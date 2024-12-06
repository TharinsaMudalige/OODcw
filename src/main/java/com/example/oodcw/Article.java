package com.example.oodcw;

public class Article {
    private int id;
    private final String article_id; //the link to the article
    private final String title;
    private final String content;
    private String category;
    private final String source;

    //Parameterized constructors
    public Article(int id, String article_id, String title, String content, String category, String source) {
        this.id = id;
        this.article_id = article_id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.source = source;
    }
    public Article(String article_id, String title, String content, String category, String source) {
        this.article_id = article_id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.source = source;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    //Checks whether any of the fields are empty/null
    public boolean isValid() {
        return article_id != null && !article_id.isBlank() &&
                title != null && !title.isBlank() &&
                content != null && !content.isBlank() &&
                source != null && !source.isBlank();
    }


}
