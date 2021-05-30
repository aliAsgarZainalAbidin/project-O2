package com.oa.needyou.pelanggan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderModel {


    @Expose
    @SerializedName("id_pekerja")
    private String id_pekerja;

    @Expose
    @SerializedName("id_pelanggan")
    private String id_pelanggan;

    @Expose
    @SerializedName("path_order")
    private String path_order;

    @Expose
    @SerializedName("keterangan_order")
    private String keterangan_order;

    @Expose
    @SerializedName("biaya_order")
    private String biaya_order;

    @Expose
    @SerializedName("alamat_order")
    private String alamat_order;

    @Expose
    @SerializedName("latitude_order")
    private String latitude_order;

    @Expose
    @SerializedName("longitude_order")
    private String longitude_order;

    @Expose
    @SerializedName("status_order")
    private String status_order;

    @Expose
    @SerializedName("tanggal_order")
    private String tanggal_order;

    @Expose
    @SerializedName("jam_order")
    private String jam_order;

    @Expose
    @SerializedName("kode_order")
    private String kode_order;

    @Expose
    @SerializedName("value")
    private String value;

    @Expose
    @SerializedName("message")
    private String message;


    public OrderModel(){

    }

    public OrderModel(String id_pekerja, String id_pelanggan, String path_order, String keterangan_order, String biaya_order, String alamat_order, String latitude_order, String longitude_order, String status_order, String tanggal_order, String jam_order, String kode_order, String value, String message) {
        this.id_pekerja = id_pekerja;
        this.id_pelanggan = id_pelanggan;
        this.path_order = path_order;
        this.keterangan_order = keterangan_order;
        this.biaya_order = biaya_order;
        this.alamat_order = alamat_order;
        this.latitude_order = latitude_order;
        this.longitude_order = longitude_order;
        this.status_order = status_order;
        this.tanggal_order = tanggal_order;
        this.jam_order = jam_order;
        this.kode_order = kode_order;
        this.value = value;
        this.message = message;
    }

    public String getId_pekerja() {
        return id_pekerja;
    }

    public void setId_pekerja(String id_pekerja) {
        this.id_pekerja = id_pekerja;
    }

    public String getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(String id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String getPath_order() {
        return path_order;
    }

    public void setPath_order(String path_order) {
        this.path_order = path_order;
    }

    public String getKeterangan_order() {
        return keterangan_order;
    }

    public void setKeterangan_order(String keterangan_order) {
        this.keterangan_order = keterangan_order;
    }

    public String getBiaya_order() {
        return biaya_order;
    }

    public void setBiaya_order(String biaya_order) {
        this.biaya_order = biaya_order;
    }

    public String getAlamat_order() {
        return alamat_order;
    }

    public void setAlamat_order(String alamat_order) {
        this.alamat_order = alamat_order;
    }

    public String getLatitude_order() {
        return latitude_order;
    }

    public void setLatitude_order(String latitude_order) {
        this.latitude_order = latitude_order;
    }

    public String getLongitude_order() {
        return longitude_order;
    }

    public void setLongitude_order(String longitude_order) {
        this.longitude_order = longitude_order;
    }

    public String getStatus_order() {
        return status_order;
    }

    public void setStatus_order(String status_order) {
        this.status_order = status_order;
    }

    public String getTanggal_order() {
        return tanggal_order;
    }

    public void setTanggal_order(String tanggal_order) {
        this.tanggal_order = tanggal_order;
    }

    public String getJam_order() {
        return jam_order;
    }

    public void setJam_order(String jam_order) {
        this.jam_order = jam_order;
    }

    public String getKode_order() {
        return kode_order;
    }

    public void setKode_order(String kode_order) {
        this.kode_order = kode_order;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
