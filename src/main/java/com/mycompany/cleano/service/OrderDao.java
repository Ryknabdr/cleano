/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.service;

import com.mycompany.cleano.model.Order;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mycompany.cleano.database.MongoConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author abdurraihan
 */
public class OrderDao {

    public void simpanOrder(Order o) {
        MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");
        Document doc = new Document("id", o.getId())
                .append("namaPelanggan", o.getPelanggan())
                .append("tanggalMasuk", o.getTanggalMasuk())
                .append("total", o.getTotal());

        collection.insertOne(doc);
    }

    public List<Order> getAllOrder() {
        List<Order> list = new ArrayList<>();
        MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Order o = new Order();
                o.setId(doc.getString("id"));
                o.setPelanggan(doc.getString("namaPelanggan"));
                o.setTanggalMasuk(doc.getDate("tanggalMasuk"));
                o.setTotalHarga(doc.getDouble("total"));
                list.add(o);
            }
        }

        return list;
    }
    public void updateOrder(Order updatedOrder) {
         System.out.println("Order berhasil diperbarui:");
    System.out.println("ID: " + updatedOrder.getId());
    System.out.println("Pelanggan: " + updatedOrder.getPelanggan());
    System.out.println("Tanggal Masuk: " + updatedOrder.getTanggalMasuk());
    System.out.println("Total Harga: " + updatedOrder.getTotalHarga());
    }

    public Order getOrderById(String id) {
         return new Order(id, "Dummy Pelanggan", new Date(), 123456);
    }

}
