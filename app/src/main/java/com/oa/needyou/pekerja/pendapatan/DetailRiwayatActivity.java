package com.oa.needyou.pekerja.pendapatan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.model.Result;

public class DetailRiwayatActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";

    private TextView tv_tgl;
    private TextView tv_jam;
    private TextView tv_title;
    private TextView tv_tarif;
    private TextView tv_penerima;
    private TextView tv_lokasi;
    private RatingBar rating;

    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        tv_tgl = findViewById(R.id.tv_tgl);
        img1 = findViewById(R.id.img1);
        tv_jam = findViewById(R.id.tv_jam);
        tv_title = findViewById(R.id.tv_title);
        tv_tarif = findViewById(R.id.tv_tarif);
        tv_lokasi = findViewById(R.id.tv_lokasi);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();

    }

    private void getData() {

        Result result = getIntent().getParcelableExtra(EXTRA_DATA);
        if (result != null){

            tv_tgl.setText(result.getmTanggalOrder());
            tv_jam.setText(result.getmJamOrder());
            tv_title.setText(result.getmKeteranganOrder());
            tv_tarif.setText("Rp. "+result.getmBiayaOrder());
            tv_lokasi.setText(result.getmAlamatOrder());

        }


    }
}
