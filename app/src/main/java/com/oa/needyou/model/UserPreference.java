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
    public static final String IS_LOGIN = "is_login";

    private boolean isLogin = false;

    private final SharedPreferences sharedPreferences;

    public UserPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setUserLogin(UserLogin userLogin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GET_ID, userLogin.id);
        editor.putString(GET_NAMA, userLogin.nama);
        editor.putString(GET_USIA, userLogin.usia);
        editor.putString(GET_PEKERJAAN, userLogin.pekerjaan);
        editor.putString(GET_GENDER, userLogin.gender);
        editor.putString(GET_TELPON, userLogin.telpon);
        editor.putString(GET_PATH, userLogin.path);
        editor.putString(GET_STATUS, userLogin.status);
        editor.putString(GET_EMAIL, userLogin.email);
        editor.putString(GET_PASS, userLogin.password);
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

    public UserLogin getUser() {
        UserLogin userLogin = new UserLogin();
        userLogin.setId(sharedPreferences.getString(GET_ID,""));
        userLogin.setNama(sharedPreferences.getString(GET_NAMA,""));
        userLogin.setUsia(sharedPreferences.getString(GET_USIA,""));
        userLogin.setPekerjaan(sharedPreferences.getString(GET_PEKERJAAN,""));
        userLogin.setGender(sharedPreferences.getString(GET_GENDER,""));
        userLogin.setTelpon(sharedPreferences.getString(GET_TELPON,""));
        userLogin.setPath(sharedPreferences.getString(GET_PATH,""));
        userLogin.setStatus(sharedPreferences.getString(GET_STATUS,""));
        userLogin.setEmail(sharedPreferences.getString(GET_EMAIL, ""));
        userLogin.setPassword(sharedPreferences.getString(GET_PASS, ""));
        userLogin.setLogin(sharedPreferences.getBoolean(IS_LOGIN, false));
        return userLogin;
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }
}
