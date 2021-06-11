package com.oa.needyou.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserPekerjaLogin implements Parcelable {
    String id;
    String nama;
    String usia;
    String pekerjaan;
    String gender;
    String telpon;
    String path;
    String status;
    String email;
    String password;
    boolean isPekerjaLogin;

    public UserPekerjaLogin() {
    }

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

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPekerjaLogin() {
        return isPekerjaLogin;
    }

    public void setLogin(boolean login) {
        isPekerjaLogin = login;
    }

    public static Creator<UserPekerjaLogin> getCREATOR() {
        return CREATOR;
    }

    public UserPekerjaLogin(String id, String nama, String usia, String pekerjaan, String gender, String telpon, String path, String status, String email, String password, boolean isPekerjaLogin) {
        this.id = id;
        this.nama = nama;
        this.usia = usia;
        this.pekerjaan = pekerjaan;
        this.gender = gender;
        this.telpon = telpon;
        this.path = path;
        this.status = status;
        this.email = email;
        this.password = password;
        this.isPekerjaLogin = isPekerjaLogin;
    }

    protected UserPekerjaLogin(Parcel in) {
        id = in.readString();
        nama = in.readString();
        usia = in.readString();
        pekerjaan = in.readString();
        gender = in.readString();
        telpon = in.readString();
        path = in.readString();
        status = in.readString();
        email = in.readString();
        password = in.readString();
        isPekerjaLogin = in.readByte() != 0;
    }

    public static final Creator<UserPekerjaLogin> CREATOR = new Creator<UserPekerjaLogin>() {
        @Override
        public UserPekerjaLogin createFromParcel(Parcel in) {
            return new UserPekerjaLogin(in);
        }

        @Override
        public UserPekerjaLogin[] newArray(int size) {
            return new UserPekerjaLogin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nama);
        parcel.writeString(usia);
        parcel.writeString(pekerjaan);
        parcel.writeString(gender);
        parcel.writeString(telpon);
        parcel.writeString(path);
        parcel.writeString(status);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeByte((byte) (isPekerjaLogin ? 1 : 0));
    }
}
