package com.example.oodcw;

import java.util.List;

public class ArticleManager {
    //Creating instances of classes
    private final ApiArticleFetcher apiFetcher = new ApiArticleFetcher();
    private final ArticleCategorize categorize = new ArticleCategorize();
    private final DatabaseHandler dbHandler = new DatabaseHandler();

    public void fetchAndStoreArticles(String query) {
        List<Article> fetchedArticles = apiFetcher.fetchArticles(query, 1); // Fetch articles
        int runSpecificCount = 0; //Hold the no of articles added during this run for the current category

        //Iterate through the fetched articles
        for (Article article : fetchedArticles) {
            if (runSpecificCount >= 30) { // Stop if the limit for the current category is reached
                System.out.println("Run-specific limit reached for category '" + query + "'. Skipping further additions.");
                break;
            }

            if (!article.isValid()) { // Skips invalid articles
                System.out.println("Skipped invalid article: " + article);
                continue;
            }

            //Checks if the article is already stored in the database to avoid storing the same article
            if (!dbHandler.isArticleExists(article.getArticle_id())) {
                String category = categorize.categorizeArticle(article.getTitle(), article.getContent());
                if (category.equalsIgnoreCase(query)) { // Ensure the category of the article matches the intended category
                    article.setCategory(category);

                    boolean success = dbHandler.addArticle(article); //Stores the article in the database
                    if (success) {
                        runSpecificCount++; // Increment the counter once stored
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
