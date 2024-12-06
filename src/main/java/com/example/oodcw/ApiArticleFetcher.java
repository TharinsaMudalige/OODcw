package com.example.oodcw;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiArticleFetcher {

    //API key
    private static final String API_KEY = "e85b73d315544e85821df6e963bf584f";
    //Url for the NewsAPI
    private static final String API_URL = "https://newsapi.org/v2/everything?q=%s&page=%d&apiKey=" + API_KEY;

    //Method to fetch articles based on the query and page
    public List<Article> fetchArticles(String query, int page) {
        List<Article> articles = new ArrayList<>(); //To store fetched articles
        try {
            //Formatting the url
            String apiUrlWithPage = String.format(API_URL, query, page);
            URL url = new URL(apiUrlWithPage);
            //Opening an HTTP connection to the API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); //Request method set to GET

            //Reading the API response
            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();  //Parsing the JSON response
                JsonArray articlesArray = jsonResponse.getAsJsonArray("articles");  //Extracting array from the response

                //Looping through each article in the array
                for (int i = 0; i < articlesArray.size(); i++) {
                    JsonObject articleJson = articlesArray.get(i).getAsJsonObject();

                    //Extracting individual fields
                    String articleId = articleJson.has("url") && !articleJson.get("url").isJsonNull()
                            ? articleJson.get("url").getAsString() : null;
                    String title = articleJson.has("title") && !articleJson.get("title").isJsonNull()
                            ? articleJson.get("title").getAsString() : null;
                    String content = articleJson.has("description") && !articleJson.get("description").isJsonNull()
                            ? articleJson.get("description").getAsString() : null;
                    String source = articleJson.has("source") && articleJson.get("source").getAsJsonObject().has("name") &&
                            !articleJson.get("source").getAsJsonObject().get("name").isJsonNull()
                            ? articleJson.get("source").getAsJsonObject().get("name").getAsString() : null;

                    //Creates an article object from the fields
                    Article article = new Article(articleId, title, content, null, source);
                    //Adds the article if it's valid
                    if (article.isValid()) {
                        articles.add(article);
                    } else {
                        System.out.println("Ignored invalid article from API response.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Returns list of articles fetched
        return articles;
    }
}
