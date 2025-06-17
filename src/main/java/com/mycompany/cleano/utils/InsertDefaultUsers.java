/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;
import com.mycompany.cleano.database.MongoConnection;
/**
 *
 * @author abdurraihan
 */
public class InsertDefaultUsers {
    
    public static void main(String[] args) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> users = db.getCollection("users");

        insertUser(users, "admin", "admin123", "Admin");
        insertUser(users, "karyawan", "karyawan123", "Karyawan");

        System.out.println("Data pengguna berhasil dimasukkan!");
    }

    private static void insertUser(MongoCollection<Document> collection, String username, String password, String role) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Document user = new Document()
                .append("username", username)
                .append("password", hashedPassword)
                .append("role", role);

        collection.insertOne(user);
    }
}
