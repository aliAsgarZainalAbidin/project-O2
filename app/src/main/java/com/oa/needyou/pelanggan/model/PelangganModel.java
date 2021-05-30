package com.oa.needyou.pelanggan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PelangganModel {

    @SerializedName("id_pelanggan")
    @Expose
    private String  id_pelanggan;

    @SerializedName("nama_pelanggan")
    @Expose
    private String  nama_pelanggan;

    @SerializedName("email_pelanggan")
    @Expose
    private String  email_pelanggan;

    @SerializedName("password_pelanggan")
    @Expose
    private String  password_pelanggan;

    @SerializedName("telpon_pelanggan")
    @Expose
    private String  telpon_pelanggan;

    @SerializedName("gender_pelanggan")
    @Expose
    private String  gender_pelanggan;

    @SerializedName("alamat_pelanggan")
    @Expose
    private String  alamat_pelanggan;

    @SerializedName("path_pelanggan")
    @Expose
    private String  path_pelanggan;

    @SerializedName("status_pelanggan")
    @Expose
    private String  status_pelanggan;

    public PelangganModel(String id_pelanggan, String nama_pelanggan, String email_pelanggan, String password_pelanggan, String telpon_pelanggan, String gender_pelanggan, String alamat_pelanggan, String path_pelanggan, String status_pelanggan) {
        this.id_pelanggan = id_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.email_pelanggan = email_pelanggan;
        this.password_pelanggan = password_pelanggan;
        this.telpon_pelanggan = telpon_pelanggan;
        this.gender_pelanggan = gender_pelanggan;
        this.alamat_pelanggan = alamat_pelanggan;
        this.path_pelanggan = path_pelanggan;
        this.status_pelanggan = status_pelanggan;
    }

    public String getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(String id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getEmail_pelanggan() {
        return email_pelanggan;
    }

    public void setEmail_pelanggan(String email_pelanggan) {
        this.email_pelanggan = email_pelanggan;
    }

    public String getPassword_pelanggan() {
        return password_pelanggan;
    }

    public void setPassword_pelanggan(String password_pelanggan) {
        this.password_pelanggan = password_pelanggan;
    }

    public String getTelpon_pelanggan() {
        return telpon_pelanggan;
    }

    public void setTelpon_pelanggan(String telpon_pelanggan) {
        this.telpon_pelanggan = telpon_pelanggan;
    }

    public String getGender_pelanggan() {
        return gender_pelanggan;
    }

    public void setGender_pelanggan(String gender_pelanggan) {
        this.gender_pelanggan = gender_pelanggan;
    }

    public String getAlamat_pelanggan() {
        return alamat_pelanggan;
    }

    public void setAlamat_pelanggan(String alamat_pelanggan) {
        this.alamat_pelanggan = alamat_pelanggan;
    }

    public String getPath_pelanggan() {
        return path_pelanggan;
    }

    public void setPath_pelanggan(String path_pelanggan) {
        this.path_pelanggan = path_pelanggan;
    }

    public String getStatus_pelanggan() {
        return status_pelanggan;
    }

    public void setStatus_pelanggan(String status_pelanggan) {
        this.status_pelanggan = status_pelanggan;
    }
}
