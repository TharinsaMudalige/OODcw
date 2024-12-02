package com.example.oodcw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.concurrent.*;


public class RecommendationEngine {
    private final DatabaseHandler databaseHandler;
    private final ExecutorService executorService;

    public RecommendationEngine() {
        this.databaseHandler = new DatabaseHandler();
        // Create a thread pool for handling multiple requests
        this.executorService = Executors.newFixedThreadPool(5); // 5 threads for parallelism
    }

    // Concurrent method to recommend articles
    public Future<List<Article>> recommendArticlesAsync(User user) {
        return executorService.submit(() -> recommendArticles(user)); // Submitting the task asynchronously
    }

    // Synchronous recommendation logic
    public List<Article> recommendArticles(User user) {
        // Step 1: Get all user interactions
        List<ArticleInteractions> interactions = user.getArticleInteractions();

        if (interactions.isEmpty()) {
            // No interactions yet, fetch 7 articles from each category (42 in total)
            List<Article> fallbackArticles = new ArrayList<>();
            List<String> allCategories = List.of("Technology", "Sports", "Health", "Crime", "Politics", "Business");

            for (String category : allCategories) {
                List<Article> categoryArticles = databaseHandler.getArticlesByCategory(category, 7);
                fallbackArticles.addAll(categoryArticles);
            }

            return fallbackArticles;
        }

        // Step 2: Categorize liked and viewed articles
        Map<String, Integer> categoryWeights = new HashMap<>();
        for (ArticleInteractions interaction : interactions) {
            Article article = databaseHandler.getArticleByID(interaction.getArticleID()); // Fetch article from DB
            if (article != null) {
                String category = article.getCategory();
                if (interaction.isLiked()) {
                    categoryWeights.put(category, categoryWeights.getOrDefault(category, 0) + 2); // Higher weight for likes
                } else {
                    categoryWeights.put(category, categoryWeights.getOrDefault(category, 0) + 1); // Lower weight for views
                }
            }
        }

        // Step 3: Sort categories by their weights (most important categories first)
        List<String> sortedCategories = categoryWeights.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Step 4: Fetch recommended articles by category
        List<Article> recommendedArticles = new ArrayList<>();
        for (String category : sortedCategories) {
            List<Article> articles = databaseHandler.getArticlesByCategory(category, 5); // Fetch 5 articles per category
            recommendedArticles.addAll(articles);
        }

        // Step 5: Add diversity (articles from unexplored categories)
        List<String> allCategories = List.of("Technology", "Sports", "Health", "Crime", "Politics", "Business");
        List<String> unexploredCategories = new ArrayList<>(allCategories);
        unexploredCategories.removeAll(sortedCategories);

        for (String category : unexploredCategories) {
            List<Article> articles = databaseHandler.getArticlesByCategory(category, 2); // Fetch 2 articles per unexplored category
            recommendedArticles.addAll(articles);
        }

        // Step 6: Limit total recommendations to 30 articles
        return recommendedArticles.stream().limit(30).collect(Collectors.toList());
    }

    // Shutdown the ExecutorService when the application closes
    public void shutdown() {
        executorService.shutdown();
    }
}
