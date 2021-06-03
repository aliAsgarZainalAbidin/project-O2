package com.oa.needyou.pekerja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.needyou.needyou.R;
import com.oa.needyou.model.UserLogin;
import com.oa.needyou.model.UserPreference;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.model.PekerjaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPekerjaActivity extends AppCompatActivity {

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

    private EditText et_email, et_pass1;
    private Button btn_login;
    private TextView tv_salah;
    private ProgressBar progressBar;
    private UserPreference sharedPreferences;

    private ImageView img_showPass;

    private boolean showPassword = true;

    private String id, nama, usia, pekerjaan, gender, email, pass, telpon, path, status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pekerja);

        sharedPreferences = new UserPreference(this);
        if (sharedPreferences.isLogin()) {
            UserLogin userLogin = sharedPreferences.getUser();
            Bundle bundle = new Bundle();
            bundle.putString(GET_ID, userLogin.getId());
            bundle.putString(GET_NAMA, userLogin.getNama());
            bundle.putString(GET_USIA, userLogin.getUsia());
            bundle.putString(GET_PEKERJAAN, userLogin.getPekerjaan());
            bundle.putString(GET_GENDER, userLogin.getGender());
            bundle.putString(GET_EMAIL, userLogin.getEmail());
            bundle.putString(GET_PASS, userLogin.getPassword());
            bundle.putString(GET_TELPON, userLogin.getTelpon());
            bundle.putString(GET_PATH, userLogin.getPath());
            bundle.putString(GET_STATUS, userLogin.getStatus());

            Intent intent = new Intent(LoginPekerjaActivity.this, DashboardPekerjaActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
        }

        img_showPass = findViewById(R.id.img_showPass);
        et_email = findViewById(R.id.et_email_update);
        et_pass1 = findViewById(R.id.et_pass1);
        tv_salah = findViewById(R.id.tv_salah);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        img_showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showPassword) {
                    et_pass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword = false;
                } else {
                    et_pass1.setTransformationMethod(null);
                    showPassword = true;
                }
            }
        });

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekusername = et_email.getText().toString();
                String cekpassword = et_pass1.getText().toString();
                if (cekusername.isEmpty()) {
                    tv_salah.setText("Email Kosong");
                    et_email.setFocusable(true);
                } else if (cekpassword.isEmpty()) {
                    tv_salah.setText("Password Kosong");
                    et_pass1.setFocusable(true);
                } else {
                    cekuser();
                }
            }
        });
    }

    private void cekuser() {

        progressBar.setVisibility(View.VISIBLE);
        String email1;
        email1 = et_email.getText().toString().trim();
        String password;
        password = et_pass1.getText().toString().trim();

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<List<PekerjaModel>> readOnepekerja = apiRequestPekerja.readOnePekerja(email1, password);
        readOnepekerja.enqueue(new Callback<List<PekerjaModel>>() {
            @Override
            public void onResponse(Call<List<PekerjaModel>> call, Response<List<PekerjaModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (!response.body().isEmpty()) {
                        tv_salah.setText("");

                        for (int i = 0; i < response.body().size(); i++) {

                            id = response.body().get(i).getId_pekerja();
                            nama = response.body().get(i).getNama_pekerja();
                            usia = response.body().get(i).getUsia_pekerja();
                            pekerjaan = response.body().get(i).getPekerjaan_pekerja();
                            gender = response.body().get(i).getGender_pekerja();
                            email = response.body().get(i).getEmail_pekerja();
                            pass = response.body().get(i).getPass_pekerja();
                            telpon = response.body().get(i).getTelpon_pekerja();
                            path = response.body().get(i).getPath_profil();
                            status = response.body().get(i).getStatus();

                        }
                        Bundle bundle = new Bundle();
                        bundle.putString(GET_ID, id);
                        bundle.putString(GET_NAMA, nama);
                        bundle.putString(GET_USIA, usia);
                        bundle.putString(GET_PEKERJAAN, pekerjaan);
                        bundle.putString(GET_GENDER, gender);
                        bundle.putString(GET_EMAIL, email);
                        bundle.putString(GET_PASS, pass);
                        bundle.putString(GET_TELPON, telpon);
                        bundle.putString(GET_PATH, path);
                        bundle.putString(GET_STATUS, status);

                        UserLogin userLogin = new UserLogin();
                        userLogin.setId(id);
                        userLogin.setNama(nama);
                        userLogin.setUsia(usia);
                        userLogin.setPekerjaan(pekerjaan);
                        userLogin.setGender(gender);
                        userLogin.setEmail(email);
                        userLogin.setPassword(pass);
                        userLogin.setTelpon(telpon);
                        userLogin.setPath(path);
                        userLogin.setStatus(status);
                        userLogin.setLogin(true);

                        sharedPreferences.setUserLogin(userLogin);

                        Toast.makeText(LoginPekerjaActivity.this, "Login Sukses..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPekerjaActivity.this, DashboardPekerjaActivity.class);
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        finish();
                    }

                } else {
                    tv_salah.setText("Username atau Password anda salah !");
                }

            }

            @Override
            public void onFailure(Call<List<PekerjaModel>> call, Throwable t) {

                Toast.makeText(LoginPekerjaActivity.this, "EROR", Toast.LENGTH_SHORT).show();
                Log.d("PEKERJA", "RESPON : " + t);

            }
        });


    }
}
