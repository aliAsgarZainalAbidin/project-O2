package com.oa.needyou.pelanggan.akun.model;

import com.google.gson.annotations.SerializedName;

public class UpdateResponModel {
    @SerializedName("value")
    private String kode;

    @SerializedName("message")
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
}
