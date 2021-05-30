package com.oa.needyou.pekerja.model;

import com.google.gson.annotations.SerializedName;

public class OrderResponModel {

    @SerializedName("kode")
    private String kode;

    @SerializedName("pesan")
    private String pesan;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public OrderResponModel(String kode, String pesan) {
        this.kode = kode;
        this.pesan = pesan;
    }
}
