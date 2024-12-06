package com.example.oodcw;

public class ArticleCategorize {

    //Method to categorize articles based on the title and content
    public String categorizeArticle(String title, String content) {
        //Combines the title and content converted to lower case for the matching of keywords
        String combinedText = (title + " " + content).toLowerCase();

        //Keywords related to Technology
        if (containsAnyKeyword(combinedText, "technology", "ai", "machine learning", "gadgets",
                "computers", "software", "hardware", "innovation", "coding", "programming", "tech")) {
            return "Technology";

            //Keywords related to Health
        }else if(containsAnyKeyword(combinedText, "health", "medicine", "wellness", "fitness",
                "mental health", "hospital", "treatment", "vaccine", "nutrition", "disease")){
            return "Health";

            //Keywords related to Sports
        }else if(containsAnyKeyword(combinedText, "sports", "match", "tournament", "league",
                "player", "athlete", "football", "cricket", "basketball", "tennis", "swimming", "badminton")){
            return "Sports";

            //Keywords related to Crime
        }else if(containsAnyKeyword(combinedText, "crime", "criminal", "felony", "theft", "robbery",
                "murder", "homicide", "assault", "kidnapping", "fraud", "vandalism",
                "court", "judge", "jury", "investigation", "forensic", "arrest",
                "gang", "cartel", "mafia", "hacking", "cybercrime",
                "money laundering", "terrorism", "bribery", "narcotics", "trafficking")){
            return "Crime";

            //Keywords related to Politics
        }else if(containsAnyKeyword(combinedText, "politics", "election", "government",
                "parliament", "congress", "minister", "president", "vote")){
            return "Politics";

            //Keywords related to Business
        }else if(containsAnyKeyword(combinedText, "business", "finance", "economy", "market", "stock",
                "investment", "trade", "corporate", "company", "entrepreneur", "startup")){
            return "Business";

            //Categorizes the article as other if no keywords are matched
        }else{
            return "Other";
        }
    }

    private boolean containsAnyKeyword(String text, String... keywords) {
        //Iterates through the keywords to check if they are there in the combinedText
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
