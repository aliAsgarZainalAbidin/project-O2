package com.oa.needyou.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    public static final String PREFS_NAME = "prefs_name";
    private final String GET_ID = "get_id";
    private final String GET_NAMA = "get_nama";
    private final String GET_USIA = "get_usia";
    private final String GET_PEKERJAAN = "get_pekerjaan";
    private final String GET_GENDER = "get_gender";
    private final String GET_EMAIL = "get_email";
    private final String GET_PASS = "get_pass";
    private final String GET_TELPON = "get_telpon";
    private final String GET_PATH = "get_path";
    private final String GET_STATUS = "get_status";

    public static final String ID_PELANGGAN = "ID_PELANGGAN";
    public static final String NAMA_PELANGGAN = "NAMA_PELANGGAN";
    public static final String EMAIL_PELANGGAN = "EMAIL_PELANGGAN";
    public static final String PASS_PELANGGAN = "PASS_PELANGGAN";
    public static final String TELPON_PELANGGAN = "TELPON_PELANGGAN";
    public static final String GENDER_PELANGGAN = "GENDER_PELANGGAN";
    public static final String ALAMAT_PELANGGAN = "ALAMAT_PELANGGAN";
    public static final String PATH_PELANGGAN = "PATH_PELANGGAN";
    public static final String STATUS_PELANGGAN = "STATUS_PELANGGAN";
    public static final String IS_PELANGGAN_LOGIN = "IS_PELANGGAN_LOGIN";

    public static final String IS_LOGIN = "is_login";

    private boolean isLogin = false;

    private final SharedPreferences sharedPreferences;

    public UserPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setUserPelangganLogin(UserPelangganLogin userPelangganLogin){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_PELANGGAN, userPelangganLogin.id);
        editor.putString(NAMA_PELANGGAN, userPelangganLogin.nama);
        editor.putString(EMAIL_PELANGGAN, userPelangganLogin.email);
        editor.putString(PASS_PELANGGAN, userPelangganLogin.pass);
        editor.putString(TELPON_PELANGGAN, userPelangganLogin.telpon);
        editor.putString(GENDER_PELANGGAN, userPelangganLogin.gender);
        editor.putString(ALAMAT_PELANGGAN, userPelangganLogin.alamat);
        editor.putString(PATH_PELANGGAN, userPelangganLogin.path);
        editor.putString(STATUS_PELANGGAN, userPelangganLogin.status);
        editor.putBoolean(IS_PELANGGAN_LOGIN, true);
        editor.apply();
    }

    public void setUserLogin(UserPekerjaLogin userPekerjaLogin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GET_ID, userPekerjaLogin.id);
        editor.putString(GET_NAMA, userPekerjaLogin.nama);
        editor.putString(GET_USIA, userPekerjaLogin.usia);
        editor.putString(GET_PEKERJAAN, userPekerjaLogin.pekerjaan);
        editor.putString(GET_GENDER, userPekerjaLogin.gender);
        editor.putString(GET_TELPON, userPekerjaLogin.telpon);
        editor.putString(GET_PATH, userPekerjaLogin.path);
        editor.putString(GET_STATUS, userPekerjaLogin.status);
        editor.putString(GET_EMAIL, userPekerjaLogin.email);
        editor.putString(GET_PASS, userPekerjaLogin.password);
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GET_ID,null);
        editor.putString(GET_NAMA, null);
        editor.putString(GET_USIA, null);
        editor.putString(GET_PEKERJAAN, null);
        editor.putString(GET_GENDER, null);
        editor.putString(GET_TELPON, null);
        editor.putString(GET_PATH, null);
        editor.putString(GET_STATUS, null);
        editor.putString(GET_EMAIL, null);
        editor.putString(GET_PASS, null);
        editor.putBoolean(IS_LOGIN, false);
        editor.apply();
    }

    public void logoutPelanggan() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_PELANGGAN, null);
        editor.putString(NAMA_PELANGGAN, null);
        editor.putString(EMAIL_PELANGGAN, null);
        editor.putString(PASS_PELANGGAN, null);
        editor.putString(TELPON_PELANGGAN, null);
        editor.putString(GENDER_PELANGGAN, null);
        editor.putString(ALAMAT_PELANGGAN, null);
        editor.putString(PATH_PELANGGAN, null);
        editor.putString(STATUS_PELANGGAN, null);
        editor.putBoolean(IS_PELANGGAN_LOGIN, false);
        editor.apply();
    }

    public UserPekerjaLogin getUser() {
        UserPekerjaLogin userPekerjaLogin = new UserPekerjaLogin();
        userPekerjaLogin.setId(sharedPreferences.getString(GET_ID,""));
        userPekerjaLogin.setNama(sharedPreferences.getString(GET_NAMA,""));
        userPekerjaLogin.setUsia(sharedPreferences.getString(GET_USIA,""));
        userPekerjaLogin.setPekerjaan(sharedPreferences.getString(GET_PEKERJAAN,""));
        userPekerjaLogin.setGender(sharedPreferences.getString(GET_GENDER,""));
        userPekerjaLogin.setTelpon(sharedPreferences.getString(GET_TELPON,""));
        userPekerjaLogin.setPath(sharedPreferences.getString(GET_PATH,""));
        userPekerjaLogin.setStatus(sharedPreferences.getString(GET_STATUS,""));
        userPekerjaLogin.setEmail(sharedPreferences.getString(GET_EMAIL, ""));
        userPekerjaLogin.setPassword(sharedPreferences.getString(GET_PASS, ""));
        userPekerjaLogin.setLogin(sharedPreferences.getBoolean(IS_LOGIN, false));
        return userPekerjaLogin;
    }

    public UserPelangganLogin getUserPelanggan() {
        UserPelangganLogin userPelangganLogin = new UserPelangganLogin();
        userPelangganLogin.setId(sharedPreferences.getString(ID_PELANGGAN,""));
        userPelangganLogin.setNama(sharedPreferences.getString(NAMA_PELANGGAN,""));
        userPelangganLogin.setGender(sharedPreferences.getString(GENDER_PELANGGAN,""));
        userPelangganLogin.setAlamat(sharedPreferences.getString(ALAMAT_PELANGGAN,""));
        userPelangganLogin.setTelpon(sharedPreferences.getString(TELPON_PELANGGAN,""));
        userPelangganLogin.setPath(sharedPreferences.getString(PATH_PELANGGAN,""));
        userPelangganLogin.setStatus(sharedPreferences.getString(STATUS_PELANGGAN,""));
        userPelangganLogin.setEmail(sharedPreferences.getString(EMAIL_PELANGGAN, ""));
        userPelangganLogin.setPass(sharedPreferences.getString(PASS_PELANGGAN, ""));
        userPelangganLogin.setLogin(sharedPreferences.getBoolean(IS_PELANGGAN_LOGIN, false));
        return userPelangganLogin;
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public boolean isPelangganLogin() {
        return sharedPreferences.getBoolean(IS_PELANGGAN_LOGIN,false);
    }
}
