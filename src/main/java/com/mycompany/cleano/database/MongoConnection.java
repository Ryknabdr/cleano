/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author abdurraihan
 */
public class MongoConnection {
     private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "cleano";

    private static final MongoClient mongoClient = MongoClients.create(URI);
    private static final MongoDatabase database = mongoClient.getDatabase(DB_NAME);

    public static MongoDatabase getDatabase() {
        return database;
    }
}

