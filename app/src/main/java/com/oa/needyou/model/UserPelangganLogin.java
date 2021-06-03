package com.oa.needyou.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserPelangganLogin implements Parcelable {
    String id;
    String nama;
    String email;
    String pass;
    String telpon;
    String gender;
    String alamat;
    String path;
    String status;
    boolean isLogin;

    protected UserPelangganLogin(Parcel in) {
        id = in.readString();
        nama = in.readString();
        email = in.readString();
        pass = in.readString();
        telpon = in.readString();
        gender = in.readString();
        alamat = in.readString();
        path = in.readString();
        status = in.readString();
        isLogin = in.readByte() != 0;
    }

    public static final Creator<UserPelangganLogin> CREATOR = new Creator<UserPelangganLogin>() {
        @Override
        public UserPelangganLogin createFromParcel(Parcel in) {
            return new UserPelangganLogin(in);
        }

        @Override
        public UserPelangganLogin[] newArray(int size) {
            return new UserPelangganLogin[size];
        }
    };

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public UserPelangganLogin() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nama);
        parcel.writeString(email);
        parcel.writeString(pass);
        parcel.writeString(telpon);
        parcel.writeString(gender);
        parcel.writeString(alamat);
        parcel.writeString(path);
        parcel.writeString(status);
        parcel.writeByte((byte) (isLogin ? 1 : 0));
    }
}
