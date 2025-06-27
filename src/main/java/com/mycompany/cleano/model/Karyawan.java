/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.model;

/**
 *
 * @author abdurraihan
 */
public class Karyawan {
    private String id;
    private String nama;
    private String jabatan;
    private String noHp;

    // Constructor lengkap
    public Karyawan(String id, String nama, String jabatan, String noHp) {
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
        this.noHp = noHp;
    }

    // Constructor kosong
    public Karyawan() {}

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}
