package com.oa.needyou.pekerja.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PekerjaModel {

    @SerializedName("id_pekerja")
    @Expose
    private String  id_pekerja;

    @SerializedName("nama_pekerja")
    @Expose
    private String  nama_pekerja;

    @SerializedName("usia_pekerja")
    @Expose
    private String  usia_pekerja;

    @SerializedName("pekerjaan_pekerja")
    @Expose
    private String  pekerjaan_pekerja;

    @SerializedName("gender_pekerja")
    @Expose
    private String  gender_pekerja;

    @SerializedName("email_pekerja")
    @Expose
    private String  email_pekerja;

    @SerializedName("pass_pekerja")
    @Expose
    private String  pass_pekerja;

    @SerializedName("telpon_pekerja")
    @Expose
    private String  telpon_pekerja;

    @SerializedName("path_profil")
    @Expose
    private String  path_profil;

    @SerializedName("status")
    @Expose
    private String  status;

    public PekerjaModel(String id_pekerja, String nama_pekerja, String usia_pekerja,
                        String pekerjaan_pekerja, String gender_pekerja, String email_pekerja,
                        String pass_pekerja, String telpon_pekerja, String path_profil,
                        String status) {
        this.id_pekerja = id_pekerja;
        this.nama_pekerja = nama_pekerja;
        this.usia_pekerja = usia_pekerja;
        this.pekerjaan_pekerja = pekerjaan_pekerja;
        this.gender_pekerja = gender_pekerja;
        this.email_pekerja = email_pekerja;
        this.pass_pekerja = pass_pekerja;
        this.telpon_pekerja = telpon_pekerja;
        this.path_profil = path_profil;
        this.status = status;
    }

    public String getId_pekerja() {
        return id_pekerja;
    }

    public void setId_pekerja(String id_pekerja) {
        this.id_pekerja = id_pekerja;
    }

    public String getNama_pekerja() {
        return nama_pekerja;
    }

    public void setNama_pekerja(String nama_pekerja) {
        this.nama_pekerja = nama_pekerja;
    }

    public String getUsia_pekerja() {
        return usia_pekerja;
    }

    public void setUsia_pekerja(String usia_pekerja) {
        this.usia_pekerja = usia_pekerja;
    }

    public String getPekerjaan_pekerja() {
        return pekerjaan_pekerja;
    }

    public void setPekerjaan_pekerja(String pekerjaan_pekerja) {
        this.pekerjaan_pekerja = pekerjaan_pekerja;
    }

    public String getGender_pekerja() {
        return gender_pekerja;
    }

    public void setGender_pekerja(String gender_pekerja) {
        this.gender_pekerja = gender_pekerja;
    }

    public String getEmail_pekerja() {
        return email_pekerja;
    }

    public void setEmail_pekerja(String email_pekerja) {
        this.email_pekerja = email_pekerja;
    }

    public String getPass_pekerja() {
        return pass_pekerja;
    }

    public void setPass_pekerja(String pass_pekerja) {
        this.pass_pekerja = pass_pekerja;
    }

    public String getTelpon_pekerja() {
        return telpon_pekerja;
    }

    public void setTelpon_pekerja(String telpon_pekerja) {
        this.telpon_pekerja = telpon_pekerja;
    }

    public String getPath_profil() {
        return path_profil;
    }

    public void setPath_profil(String path_profil) {
        this.path_profil = path_profil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
