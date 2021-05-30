
package com.oa.needyou.pekerja.beranda.model;

import com.google.gson.annotations.SerializedName;

public class ResultSaldo {

    @SerializedName("id_saldo")
    private String mIdSaldo;
    @SerializedName("jumlah_saldo")
    private String mJumlahSaldo;
    @SerializedName("saldo_id_pekerja")
    private String mSaldoIdPekerja;
    @SerializedName("saldo_nama_pekerja")
    private String mSaldoNamaPekerja;

    public String getIdSaldo() {
        return mIdSaldo;
    }

    public void setIdSaldo(String idSaldo) {
        mIdSaldo = idSaldo;
    }

    public String getJumlahSaldo() {
        return mJumlahSaldo;
    }

    public void setJumlahSaldo(String jumlahSaldo) {
        mJumlahSaldo = jumlahSaldo;
    }

    public String getSaldoIdPekerja() {
        return mSaldoIdPekerja;
    }

    public void setSaldoIdPekerja(String saldoIdPekerja) {
        mSaldoIdPekerja = saldoIdPekerja;
    }

    public String getSaldoNamaPekerja() {
        return mSaldoNamaPekerja;
    }

    public void setSaldoNamaPekerja(String saldoNamaPekerja) {
        mSaldoNamaPekerja = saldoNamaPekerja;
    }

}
