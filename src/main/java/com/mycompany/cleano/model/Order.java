/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.model;

import java.util.Date;

/**
 *
 * @author abdurraihan
 */
public class Order {

    private String id;
    private String pelanggan;
    private Date tanggalMasuk;
    private double totalHarga;
    private Date tanggalSelesai;

    public Order() {
    }

    public Order(String id, String pelanggan, Date tanggalMasuk, double totalHarga) {
        this.id = id;
        this.pelanggan = pelanggan;
        this.tanggalMasuk = tanggalMasuk;
        this.totalHarga = totalHarga;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(String pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", pelanggan=" + pelanggan + ", tanggalMasuk=" + tanggalMasuk + ", totalHarga=" + totalHarga + '}';
    }

    public Object getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(Date date) {
        this.tanggalSelesai = date;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
