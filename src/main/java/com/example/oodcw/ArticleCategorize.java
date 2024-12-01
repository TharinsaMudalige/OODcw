package com.example.oodcw;

public class ArticleCategorize {

    public String categorizeArticle(String title, String content) {
        String combinedText = (title + " " + content).toLowerCase();

        if (containsAnyKeyword(combinedText, "technology", "ai", "machine learning", "gadgets",
                "computers", "software", "hardware", "innovation", "coding", "programming", "tech")) {
            return "Technology";

        }else if(containsAnyKeyword(combinedText, "health", "medicine", "wellness", "fitness",
                "mental health", "hospital", "treatment", "vaccine", "nutrition", "disease")){
            return "Health";

        }else if(containsAnyKeyword(combinedText, "sports", "match", "tournament", "league",
                "player", "athlete", "football", "cricket", "basketball", "tennis", "swimming", "badminton")){
            return "Sports";

        }else if(containsAnyKeyword(combinedText, "crime", "criminal", "felony", "theft", "robbery",
                "murder", "homicide", "assault", "kidnapping", "fraud", "vandalism",
                "court", "judge", "jury", "investigation", "forensic", "arrest",
                "gang", "cartel", "mafia", "hacking", "cybercrime",
                "money laundering", "terrorism", "bribery", "narcotics", "trafficking")){
            return "Crime";

        }else if(containsAnyKeyword(combinedText, "politics", "election", "government",
                "parliament", "congress", "minister", "president", "vote")){
            return "Politics";

        }else if(containsAnyKeyword(combinedText, "business", "finance", "economy", "market", "stock",
                "investment", "trade", "corporate", "company", "entrepreneur", "startup")){
            return "Business";

        }else{
            return "Other";
        }
    }

    private boolean containsAnyKeyword(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
