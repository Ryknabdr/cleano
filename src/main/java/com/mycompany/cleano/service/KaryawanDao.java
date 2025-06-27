/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.service;
import com.mycompany.cleano.database.MongoConnection;
import com.mycompany.cleano.model.Karyawan;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
/**
 *
 * @author abdurraihan
 */
public class KaryawanDao {
    private final MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("karyawan");
    
    public void simpanKaryawan(Karyawan k) {
        Document doc = new Document("id", k.getId())
                .append("nama", k.getNama())
                .append("jabatan", k.getJabatan())
                .append("noHp", k.getNoHp());
        collection.insertOne(doc);
    }

    public List<Karyawan> getAllKaryawan() {
        List<Karyawan> list = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Karyawan k = new Karyawan();
                k.setId(doc.getString("id"));
                k.setNama(doc.getString("nama"));
                k.setJabatan(doc.getString("jabatan"));
                k.setNoHp(doc.getString("noHp"));
                list.add(k);
            }
        }
        return list;
    }

    public Karyawan getKaryawanById(String id) {
        Document doc = collection.find(new Document("id", id)).first();
        if (doc != null) {
            return new Karyawan(
                doc.getString("id"),
                doc.getString("nama"),
                doc.getString("jabatan"),
                doc.getString("noHp")
            );
        }
        return null;
    }
}
