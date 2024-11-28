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

    private static final String API_KEY = "e85b73d315544e85821df6e963bf584f";
    private static final String API_URL = "https://newsapi.org/v2/everything?q=%s&page=%d&apiKey=" + API_KEY;

    public List<Article> fetchArticles(String query, int page) {
        List<Article> articles = new ArrayList<>();
        try {
            String apiUrlWithPage = String.format(API_URL, query, page);
            URL url = new URL(apiUrlWithPage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray articlesArray = jsonResponse.getAsJsonArray("articles");

                for (int i = 0; i < articlesArray.size(); i++) {
                    JsonObject articleJson = articlesArray.get(i).getAsJsonObject();

                    String articleId = articleJson.has("url") && !articleJson.get("url").isJsonNull()
                            ? articleJson.get("url").getAsString() : null;
                    String title = articleJson.has("title") && !articleJson.get("title").isJsonNull()
                            ? articleJson.get("title").getAsString() : null;
                    String content = articleJson.has("description") && !articleJson.get("description").isJsonNull()
                            ? articleJson.get("description").getAsString() : null;
                    String source = articleJson.has("source") && articleJson.get("source").getAsJsonObject().has("name") &&
                            !articleJson.get("source").getAsJsonObject().get("name").isJsonNull()
                            ? articleJson.get("source").getAsJsonObject().get("name").getAsString() : null;

                    Article article = new Article(articleId, title, content, null, source);
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
        return articles;
    }
}
