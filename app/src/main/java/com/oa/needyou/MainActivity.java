package com.oa.needyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.MasukPekerjaActivity;
import com.oa.needyou.pelanggan.MasukPelangganActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MasukPekerjaActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MasukPelangganActivity.class);
                startActivity(intent);
            }
        });
    }
}
