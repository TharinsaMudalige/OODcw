package com.example.oodcw;

import java.util.List;

public class ArticleManager {
    private final ApiArticleFetcher apiFetcher = new ApiArticleFetcher();
    private final ArticleCategorize categorize = new ArticleCategorize();
    private final DatabaseHandler dbHandler = new DatabaseHandler();

    public void fetchAndStoreArticles(String query) {
        List<Article> fetchedArticles = apiFetcher.fetchArticles(query, 1); // Fetch only 1 page (10 articles per query)
        int runSpecificCount = 0; // Track articles added during this run for the current category

        for (Article article : fetchedArticles) {
            if (runSpecificCount >= 30) { // Stop if the run-specific limit for the category is reached
                System.out.println("Run-specific limit reached for category '" + query + "'. Skipping further additions.");
                break;
            }

            if (!article.isValid()) { // Skip invalid articles
                System.out.println("Skipped invalid article: " + article);
                continue;
            }

            if (!dbHandler.isArticleExists(article.getArticle_id())) { // Avoid duplicates in the database
                String category = categorize.categorizeArticle(article.getTitle(), article.getContent());
                if (category.equalsIgnoreCase(query)) { // Ensure it matches the intended category
                    article.setCategory(category);
                    boolean success = dbHandler.addArticle(article);
                    if (success) {
                        runSpecificCount++; // Increment count for this run
                        System.out.println("Stored article: " + article.getTitle());
                    }
                }
            } else {
                System.out.println("Skipped duplicate article: " + article.getTitle());
            }
        }

        System.out.println("Total articles stored during this run for category '" + query + "': " + runSpecificCount);
    }
}
