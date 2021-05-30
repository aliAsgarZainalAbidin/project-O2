package com.oa.needyou.pelanggan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.needyou.needyou.R;

public class MasukPelangganActivity extends AppCompatActivity {

    private Button btn_signup,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_pelanggan);

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasukPelangganActivity.this,
                        LoginPelangganActivity.class);
                startActivity(intent);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasukPelangganActivity.this,
                        RegistPelangganActivity.class);
                startActivity(intent);
            }
        });

    }
}
