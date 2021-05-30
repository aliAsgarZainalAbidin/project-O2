package com.oa.needyou.pekerja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.needyou.needyou.R;

public class RegistPekerjaActivity extends AppCompatActivity {

    private Button btn_website;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_pekerja);

        btn_login = findViewById(R.id.btn_login);
        btn_website = findViewById(R.id.btn_website);

        btn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://needyou.id/"));
                startActivity(browserIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
