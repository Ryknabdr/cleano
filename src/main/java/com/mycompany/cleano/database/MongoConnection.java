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

    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    static {
        try {
            mongoClient = MongoClients.create(URI);
            database = mongoClient.getDatabase(DB_NAME);
            System.out.println("✅ Koneksi MongoDB berhasil ke database: " + DB_NAME);
        } catch (Exception e) {
            System.err.println("❌ Gagal konek ke MongoDB: " + e.getMessage());
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoClient getClient() {
        return mongoClient;
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("🔌 Koneksi MongoDB ditutup.");
        }
    }
}

