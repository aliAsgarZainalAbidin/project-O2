package com.oa.needyou.pelanggan.aktivitas.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AktifitasPelangganModel implements Parcelable {

    private String id_aktifitas;
    private String title_aktifitas;
    private String tarif;
    private String id_pelanggan;
    private String nama_pelanggan;
    private String id_pekerja;
    private String nama_pekerja;
    private String tanggal;
    private String jam;
    private String lokasi;
    private String rating;
    private String status;

    public AktifitasPelangganModel(String id_aktifitas, String title_aktifitas, String tarif, String id_pelanggan, String nama_pelanggan, String id_pekerja, String nama_pekerja, String tanggal, String jam, String lokasi, String rating, String status) {
        this.id_aktifitas = id_aktifitas;
        this.title_aktifitas = title_aktifitas;
        this.tarif = tarif;
        this.id_pelanggan = id_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.id_pekerja = id_pekerja;
        this.nama_pekerja = nama_pekerja;
        this.tanggal = tanggal;
        this.jam = jam;
        this.lokasi = lokasi;
        this.rating = rating;
        this.status = status;
    }

    public AktifitasPelangganModel() {

    }

    public String getId_aktifitas() {
        return id_aktifitas;
    }

    public void setId_aktifitas(String id_aktifitas) {
        this.id_aktifitas = id_aktifitas;
    }

    public String getTitle_aktifitas() {
        return title_aktifitas;
    }

    public void setTitle_aktifitas(String title_aktifitas) {
        this.title_aktifitas = title_aktifitas;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected AktifitasPelangganModel(Parcel in) {
        id_aktifitas = in.readString();
        title_aktifitas = in.readString();
        tarif = in.readString();
        id_pelanggan = in.readString();
        nama_pelanggan = in.readString();
        id_pekerja = in.readString();
        nama_pekerja = in.readString();
        tanggal = in.readString();
        jam = in.readString();
        lokasi = in.readString();
        rating = in.readString();
        status = in.readString();
    }

    public static final Creator<AktifitasPelangganModel> CREATOR = new Creator<AktifitasPelangganModel>() {
        @Override
        public AktifitasPelangganModel createFromParcel(Parcel in) {
            return new AktifitasPelangganModel(in);
        }

        @Override
        public AktifitasPelangganModel[] newArray(int size) {
            return new AktifitasPelangganModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_aktifitas);
        dest.writeString(title_aktifitas);
        dest.writeString(tarif);
        dest.writeString(id_pelanggan);
        dest.writeString(nama_pelanggan);
        dest.writeString(id_pekerja);
        dest.writeString(nama_pekerja);
        dest.writeString(tanggal);
        dest.writeString(jam);
        dest.writeString(lokasi);
        dest.writeString(rating);
        dest.writeString(status);
    }
}
