package com.oa.needyou.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserLogin implements Parcelable {
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
    boolean isLogin;

    public UserLogin() {
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

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public static Creator<UserLogin> getCREATOR() {
        return CREATOR;
    }

    public UserLogin(String id, String nama, String usia, String pekerjaan, String gender, String telpon, String path, String status, String email, String password, boolean isLogin) {
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
        this.isLogin = isLogin;
    }

    protected UserLogin(Parcel in) {
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
        isLogin = in.readByte() != 0;
    }

    public static final Creator<UserLogin> CREATOR = new Creator<UserLogin>() {
        @Override
        public UserLogin createFromParcel(Parcel in) {
            return new UserLogin(in);
        }

        @Override
        public UserLogin[] newArray(int size) {
            return new UserLogin[size];
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
        parcel.writeByte((byte) (isLogin ? 1 : 0));
    }
}
