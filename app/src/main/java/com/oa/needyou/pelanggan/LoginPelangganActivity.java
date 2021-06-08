package com.oa.needyou.pelanggan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.oa.needyou.model.UserPelangganLogin;
import com.oa.needyou.model.UserPreference;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.PelangganModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPelangganActivity extends AppCompatActivity {


    private EditText et_username;
    private EditText et_pass1;
    private Button btn_login;
    private TextView tv_salah;
    private ProgressBar progressBar;
    private UserPreference sharedPreferences;

    private ImageView img_showPass;

    private boolean showPassword = true;

    private final String GET_ID = "get_id";
    private final String GET_NAMA = "get_nama";
    private final String GET_GENDER = "get_gender";
    private final String GET_EMAIL = "get_email";
    private final String GET_PASS = "get_pass";
    private final String GET_TELPON = "get_telpon";
    private final String GET_PATH = "get_path";
    private final String GET_STATUS = "get_status";
    private final String GET_ALAMAT = "get_alamat";

    private String id,nama,email,pass,telpon,gender,alamat,path,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pelanggan);

        sharedPreferences = new UserPreference(this);
        if (sharedPreferences.isPelangganLogin()){
            UserPelangganLogin userPelangganLogin = sharedPreferences.getUserPelanggan();
            Bundle bundle = new Bundle();
            bundle.putString(GET_ID, userPelangganLogin.getId());
            bundle.putString(GET_NAMA, userPelangganLogin.getNama());
            bundle.putString(GET_EMAIL, userPelangganLogin.getEmail());
            bundle.putString(GET_PASS, userPelangganLogin.getPass());
            bundle.putString(GET_TELPON, userPelangganLogin.getTelpon());
            bundle.putString(GET_GENDER, userPelangganLogin.getGender());
            bundle.putString(GET_ALAMAT, userPelangganLogin.getAlamat());
            bundle.putString(GET_PATH, userPelangganLogin.getPath());
            bundle.putString(GET_STATUS, userPelangganLogin.getStatus());

            Intent intent = new Intent(LoginPelangganActivity.this, DashboardPelangganActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
        }

        img_showPass = findViewById(R.id.img_showPass);

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


        et_username = findViewById(R.id.et_username);
        et_pass1 = findViewById(R.id.et_pass1);
        btn_login = findViewById(R.id.btn_login);
        tv_salah = findViewById(R.id.tv_salah);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekusername = et_username.getText().toString();
                String cekpassword = et_pass1.getText().toString();
                if (cekusername.isEmpty()) {
                    tv_salah.setText("Email Kosong");
                    et_username.setFocusable(true);
                } else if (cekpassword.isEmpty()) {
                    tv_salah.setText("Password Kosong");
                    et_pass1.setFocusable(true);
                } else {
                    cekuserPelanggan();
                }
            }
        });

    }

    private void cekuserPelanggan() {
        progressBar.setVisibility(View.VISIBLE);
        String email1;
        email1 = et_username.getText().toString().trim();
        String password;
        password = et_pass1.getText().toString().trim();

        ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<List<PelangganModel>> readOnePelanggan = apiRequsetPelanggan.readOnePelanggan(email1,password);
        readOnePelanggan.enqueue(new Callback<List<PelangganModel>>() {
            @Override
            public void onResponse(Call<List<PelangganModel>> call, Response<List<PelangganModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (!response.body().isEmpty()){
                        tv_salah.setText("");

                        for (int i = 0; i < response.body().size(); i++) {

                            id = response.body().get(i).getId_pelanggan();
                            nama = response.body().get(i).getNama_pelanggan();
                            email = response.body().get(i).getEmail_pelanggan();
                            pass = response.body().get(i).getPassword_pelanggan();
                            telpon = response.body().get(i).getTelpon_pelanggan();
                            gender = response.body().get(i).getGender_pelanggan();
                            alamat = response.body().get(i).getAlamat_pelanggan();
                            path = response.body().get(i).getPath_pelanggan();
                            status = response.body().get(i).getStatus_pelanggan();

                        }

                        Bundle bundle = new Bundle();
                        bundle.putString(GET_ID, id);
                        bundle.putString(GET_NAMA, nama);
                        bundle.putString(GET_EMAIL, email);
                        bundle.putString(GET_PASS, pass);
                        bundle.putString(GET_TELPON, telpon);
                        bundle.putString(GET_GENDER, gender);
                        bundle.putString(GET_ALAMAT, alamat);
                        bundle.putString(GET_PATH, path);
                        bundle.putString(GET_STATUS, status);

                        UserPelangganLogin userPelangganLogin = new UserPelangganLogin();
                        userPelangganLogin.setId(id);
                        userPelangganLogin.setNama(nama);
                        userPelangganLogin.setEmail(email);
                        userPelangganLogin.setPass(pass);
                        userPelangganLogin.setTelpon(telpon);
                        userPelangganLogin.setGender(gender);
                        userPelangganLogin.setAlamat(alamat);
                        userPelangganLogin.setPath(path);
                        userPelangganLogin.setStatus(status);
                        userPelangganLogin.setLogin(true);

                        sharedPreferences.setUserPelangganLogin(userPelangganLogin);

                        Toast.makeText(LoginPelangganActivity.this, "Login Sukses..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPelangganActivity.this, DashboardPelangganActivity.class);
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        finish();

                    }else {
                        tv_salah.setText("Username atau Password anda salah !");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PelangganModel>> call, Throwable t) {

                Toast.makeText(LoginPelangganActivity.this, "EROR", Toast.LENGTH_SHORT).show();
                Log.d("PELANGGAN", "RESPON : "+t);


            }
        });

    }
}
