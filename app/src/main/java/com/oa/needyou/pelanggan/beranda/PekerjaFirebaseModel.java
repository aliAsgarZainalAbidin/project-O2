package com.oa.needyou.pelanggan.beranda;

public class PekerjaFirebaseModel {

    private String id_pekerja;
    private String kode_order;
    private String latitude_pekerja;
    private String longitude_pekerja;
    private String nama_pekerja;
    private String status_pekerja;

    public String getKode_order() {
        return kode_order;
    }

    public void setKode_order(String kode_order) {
        this.kode_order = kode_order;
    }

    public PekerjaFirebaseModel(String id_pekerja, String kode_order, String latitude_pekerja, String longitude_pekerja, String nama_pekerja, String status_pekerja) {
        this.id_pekerja = id_pekerja;
        this.kode_order = kode_order;
        this.latitude_pekerja = latitude_pekerja;
        this.longitude_pekerja = longitude_pekerja;
        this.nama_pekerja = nama_pekerja;
        this.status_pekerja = status_pekerja;
    }

    public PekerjaFirebaseModel(String id_pekerja, String latitude_pekerja, String longitude_pekerja, String nama_pekerja, String status_pekerja) {
        this.id_pekerja = id_pekerja;
        this.latitude_pekerja = latitude_pekerja;
        this.longitude_pekerja = longitude_pekerja;
        this.nama_pekerja = nama_pekerja;
        this.status_pekerja = status_pekerja;
    }

    public PekerjaFirebaseModel() {

    }

    public String getId_pekerja() {
        return id_pekerja;
    }

    public void setId_pekerja(String id_pekerja) {
        this.id_pekerja = id_pekerja;
    }

    public String getLatitude_pekerja() {
        return latitude_pekerja;
    }

    public void setLatitude_pekerja(String latitude_pekerja) {
        this.latitude_pekerja = latitude_pekerja;
    }

    public String getLongitude_pekerja() {
        return longitude_pekerja;
    }

    public void setLongitude_pekerja(String longitude_pekerja) {
        this.longitude_pekerja = longitude_pekerja;
    }

    public String getNama_pekerja() {
        return nama_pekerja;
    }

    public void setNama_pekerja(String nama_pekerja) {
        this.nama_pekerja = nama_pekerja;
    }

    public String getStatus_pekerja() {
        return status_pekerja;
    }

    public void setStatus_pekerja(String status_pekerja) {
        this.status_pekerja = status_pekerja;
    }
}
