package com.oa.needyou.model;

import com.google.gson.annotations.SerializedName;

public class DataUser {

    @SerializedName("alamat_user")
    private String mAlamatUser;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("jekel_user")
    private String mJekelUser;
    @SerializedName("level_user")
    private String mLevelUser;
    @SerializedName("nama_user")
    private String mNamaUser;
    @SerializedName("password_user")
    private String mPasswordUser;
    @SerializedName("telepon_user")
    private String mTeleponUser;
    @SerializedName("username_user")
    private String mUsernameUser;

    public String getAlamatUser() {
        return mAlamatUser;
    }

    public void setAlamatUser(String alamatUser) {
        mAlamatUser = alamatUser;
    }

    public String getIdUser() {
        return mIdUser;
    }

    public void setIdUser(String idUser) {
        mIdUser = idUser;
    }

    public String getJekelUser() {
        return mJekelUser;
    }

    public void setJekelUser(String jekelUser) {
        mJekelUser = jekelUser;
    }

    public String getLevelUser() {
        return mLevelUser;
    }

    public void setLevelUser(String levelUser) {
        mLevelUser = levelUser;
    }

    public String getNamaUser() {
        return mNamaUser;
    }

    public void setNamaUser(String namaUser) {
        mNamaUser = namaUser;
    }

    public String getPasswordUser() {
        return mPasswordUser;
    }

    public void setPasswordUser(String passwordUser) {
        mPasswordUser = passwordUser;
    }

    public String getTeleponUser() {
        return mTeleponUser;
    }

    public void setTeleponUser(String teleponUser) {
        mTeleponUser = teleponUser;
    }

    public String getUsernameUser() {
        return mUsernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        mUsernameUser = usernameUser;
    }

}
