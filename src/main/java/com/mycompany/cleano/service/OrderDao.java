/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.service;

import com.mycompany.cleano.model.Order;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mycompany.cleano.database.MongoConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import java.util.stream.Collectors;
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
                .append("tanggalSelesai", o.getTanggalSelesai())
                .append("total", o.getTotalHarga())
                .append("status", o.getStatus()); // ✅ tambahkan status
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
                o.setPelanggan(doc.getString("namaPelanggan")); // ✅ field nama sesuai
                o.setTanggalMasuk(doc.getDate("tanggalMasuk"));
                o.setTotalHarga(doc.getDouble("total"));
                o.setStatus(doc.getString("status")); // ✅ ambil status
                list.add(o);
            }
        }

        return list;
    }

    public void updateOrder(Order updatedOrder) {
        MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");

        // Data yang akan di-update
        Document data = new Document();
        data.append("namaPelanggan", updatedOrder.getPelanggan()); // nama field harus sama dengan saat insert
        data.append("tanggalMasuk", updatedOrder.getTanggalMasuk());
        data.append("total", updatedOrder.getTotalHarga()); // sesuaikan field dengan insert
        data.append("status", updatedOrder.getStatus());

        if (updatedOrder.getTanggalSelesai() != null) {
            data.append("tanggalSelesai", updatedOrder.getTanggalSelesai());

            // Update dengan hanya $set
            Document update = new Document("$set", data);
            collection.updateOne(Filters.eq("id", updatedOrder.getId()), update);
        } else {
            // Jika tanggalSelesai null, kita hapus field tersebut
            Document update = new Document("$set", data)
                    .append("$unset", new Document("tanggalSelesai", ""));
            collection.updateOne(Filters.eq("id", updatedOrder.getId()), update);
        }
    }

    public Order getOrderById(String id) {
        MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");
        Document doc = collection.find(Filters.eq("id", id)).first(); // ✅ pakai field id

        if (doc != null) {
            Order o = new Order();
            o.setId(doc.getString("id"));
            o.setPelanggan(doc.getString("namaPelanggan")); // ✅
            o.setTanggalMasuk(doc.getDate("tanggalMasuk"));
            o.setTanggalSelesai(doc.getDate("tanggalSelesai"));
            o.setTotalHarga(doc.getDouble("total")); // ✅
            o.setStatus(doc.getString("status")); // ✅
            return o;
        }

        return null;
    }

    public void hapusOrder(String id) {
       MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");
    collection.deleteOne(Filters.eq("id", id)); // ❗ pakai "id", bukan "_id"
    }

    public String generateOrderId() {
        MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");

        // Ambil semua ID dan cari angka terbesar
        int max = 0;
        for (Document doc : collection.find()) {
            String id = doc.getString("id"); // contoh: ORD001
            if (id != null && id.startsWith("ORD") && id.length() >= 6) {
                try {
                    int num = Integer.parseInt(id.substring(3)); // ambil angka setelah ORD
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                    // Abaikan jika format ID tidak sesuai
                }
            }
        }

        // Buat ID baru
        return String.format("ORD%03d", max + 1);
    }
public double getTotalPendapatanBulanIni() {
    MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_MONTH, 1);
    Date awalBulan = cal.getTime();
    cal.add(Calendar.MONTH, 1);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    Date awalBulanDepan = cal.getTime();

    double total = 0;
    for (Document doc : collection.find(Filters.and(
            Filters.gte("tanggalMasuk", awalBulan),
            Filters.lt("tanggalMasuk", awalBulanDepan)
    ))) {
        total += doc.getDouble("total");
    }
    return total;
}
public int getJumlahOrderHariIni() {
    MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    Date awalHari = cal.getTime();
    cal.add(Calendar.DATE, 1);
    Date besok = cal.getTime();

    return (int) collection.countDocuments(Filters.and(
        Filters.gte("tanggalMasuk", awalHari),
        Filters.lt("tanggalMasuk", besok)
    ));
}
public Map<String, Double> getPendapatanMingguan() {
    MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");
    Map<String, Double> data = new LinkedHashMap<>();

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);

    for (int i = 6; i >= 0; i--) {
        Calendar hari = (Calendar) cal.clone();
        hari.add(Calendar.DATE, -i);
        Date start = hari.getTime();
        hari.add(Calendar.DATE, 1);
        Date end = hari.getTime();

        double total = 0;
        for (Document doc : collection.find(Filters.and(
                Filters.gte("tanggalMasuk", start),
                Filters.lt("tanggalMasuk", end)
        ))) {
            total += doc.getDouble("total");
        }

        String label = new SimpleDateFormat("EEE").format(start); // "Mon", "Tue", ...
        data.put(label, total);
    }

    return data;
}
public List<Order> getOrderHariIni() {
    List<Order> list = new ArrayList<>();
    MongoCollection<Document> collection = MongoConnection.getDatabase().getCollection("order");

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date startOfDay = cal.getTime();

    cal.add(Calendar.DATE, 1);
    Date endOfDay = cal.getTime();

    MongoCursor<Document> cursor = collection.find(
        Filters.and(
            Filters.gte("tanggalMasuk", startOfDay),
            Filters.lt("tanggalMasuk", endOfDay)
        )
    ).iterator();

    while (cursor.hasNext()) {
        Document doc = cursor.next();
        Order o = new Order();
        o.setId(doc.getString("id"));
        o.setPelanggan(doc.getString("namaPelanggan"));
        o.setTanggalMasuk(doc.getDate("tanggalMasuk"));
        o.setTotalHarga(doc.getDouble("total"));
        o.setStatus(doc.getString("status"));
        list.add(o);
    }

    return list;
}
}
