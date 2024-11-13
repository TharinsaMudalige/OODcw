package com.example.oodcw;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.MongoClientURI;

public class DatabaseHandler {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> usersCollection;

    //Initializing the DB connection
    public DatabaseHandler() {
        try{
            //Replacing with the MongoDB URI
            MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
            mongoClient = new MongoClient(uri);

            //Connecting to the database
            database = mongoClient.getDatabase("smartReadDB");

            //Connecting to the users collection
            usersCollection = database.getCollection("users");

        } catch(Exception e){
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
        }
    }

    public void addUser(User user){

    }

}
