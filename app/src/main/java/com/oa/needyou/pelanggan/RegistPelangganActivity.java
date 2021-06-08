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
import android.widget.Toast;

import com.needyou.needyou.R;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.PelangganModel;
import com.oa.needyou.pelanggan.model.ResponseModelPelanggan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistPelangganActivity extends AppCompatActivity {


    private EditText et_username;
    private EditText et_email;
    private EditText et_pass1;

    private Button btn_regist;
    private ProgressBar progressBar;

    private ImageView img_showPass;

    private boolean showPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_pelanggan);

        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email_update);
        et_pass1 = findViewById(R.id.et_pass1);
        btn_regist = findViewById(R.id.btn_regist);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

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

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(RegistPelangganActivity.this, "klik", Toast.LENGTH_SHORT).show();
                String ceknama = et_username.getText().toString();
                String cekemail = et_email.getText().toString();
                String cekpassword = et_pass1.getText().toString();
                if (ceknama.isEmpty()) {
                    et_username.setError("Nama Kosong");
                    et_username.setFocusable(true);
                } else if (cekemail.isEmpty()) {
                    et_email.setError("Email Kosong");
                    et_email.setFocusable(true);
                } else if (cekpassword.isEmpty()) {
                    et_pass1.setError("Password Kosong");
                    et_pass1.setFocusable(true);
                } else {
                    tambah_Pelanggan();
                }
            }
        });

    }



    private void tambah_Pelanggan(){
                progressBar.setVisibility(View.VISIBLE);

        String cek_email = et_email.getText().toString();
        final ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);

        Call<List<PelangganModel>> getChek = apiRequsetPelanggan.cekEmail(cek_email);
        getChek.enqueue(new Callback<List<PelangganModel>>() {
            @Override
            public void onResponse(Call<List<PelangganModel>> call, Response<List<PelangganModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (!response.body().isEmpty()){
                        Toast.makeText(RegistPelangganActivity.this, "Email Sudah Digunakan..", Toast.LENGTH_SHORT).show();
                    }else {
//                        Toast.makeText(RegistPelangganActivity.this, "Email Belum di gunakan", Toast.LENGTH_SHORT).show();
                        String fullnama =et_username.getText().toString();
                        String email =et_email.getText().toString();
                        String pass =et_pass1.getText().toString();

                        Call<ResponseModelPelanggan> addPelanggan = apiRequsetPelanggan.addPelanggan(fullnama,email,pass);
                        addPelanggan.enqueue(new Callback<ResponseModelPelanggan>() {
                            @Override
                            public void onResponse(Call<ResponseModelPelanggan> call, Response<ResponseModelPelanggan> response) {
                                Log.d("RETRO", "response : " + response.body().toString());
                                String kode = response.body().getKode();

                                if (kode.equals("1")){
                                    Toast.makeText(RegistPelangganActivity.this, "Berhasil Registrasi", Toast.LENGTH_SHORT).show();

                                    et_username.setText("");
                                    et_email.setText("");
                                    et_pass1.setText("");

                                    Intent intent = new Intent(RegistPelangganActivity.this, LoginPelangganActivity.class);
                                    startActivity(intent);
                                    finish();


                                } else {
                                    Toast.makeText(RegistPelangganActivity.this, "Data Error, Tidak Berhasil Registrasi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModelPelanggan> call, Throwable t) {

                                progressBar.setVisibility(View.GONE);
                                Log.d("RETRO", "REGIST : " + t);

                                Toast.makeText(RegistPelangganActivity.this, "Gagal Tambah pelanggan", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }

            }

            @Override
            public void onFailure(Call<List<PelangganModel>> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.d("RETRO", "CEK EMAIL : " + t);
                Toast.makeText(RegistPelangganActivity.this, "Gagal Cek Email", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
