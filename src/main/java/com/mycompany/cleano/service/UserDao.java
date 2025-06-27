/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.service;

import com.mycompany.cleano.database.MongoConnection;
import com.mycompany.cleano.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author abdurraihan
 */
public class UserDao {

    private final MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("pengguna");

    public void simpanUser(User u) {
        Document doc = new Document("username", u.getUsername())
                .append("password", u.getPassword())
                .append("role", u.getRole());
        collection.insertOne(doc);
    }

    public List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                User u = new User();
                u.setUsername(doc.getString("username"));
                u.setPassword(doc.getString("password"));
                u.setRole(doc.getString("role"));
                list.add(u);
            }
        }
        return list;
    }

    public User getUserByUsername(String username) {
        Document doc = collection.find(new Document("username", username)).first();
        if (doc != null) {
            return new User(
                    doc.getString("username"),
                    doc.getString("password"),
                    doc.getString("role")
            );
        }
        return null;
    }
    public boolean updateUsernamePassword(String usernameLama, String passwordLama, String usernameBaru, String passwordBaru) {
    MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("pengguna");
    Document existing = collection.find(Filters.and(
        Filters.eq("username", usernameLama),
        Filters.eq("password", passwordLama)
    )).first();

    if (existing != null) {
        Document update = new Document("$set", new Document("username", usernameBaru).append("password", passwordBaru));
        collection.updateOne(existing, update);
        return true;
    }
    return false;
}

}
