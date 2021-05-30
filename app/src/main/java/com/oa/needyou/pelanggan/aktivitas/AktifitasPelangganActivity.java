package com.oa.needyou.pelanggan.aktivitas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.model.OrderResponModel;
import com.oa.needyou.pekerja.model.PekerjaModel;
import com.oa.needyou.pekerja.model.Result;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AktifitasPelangganActivity extends AppCompatActivity {


    public static final String EXTRA_DATA = "extra_data";

    private TextView tv_tgl;
    private TextView tv_jam;
    private TextView tv_title;
    private TextView tv_tarif;
    private TextView tv_penerima;
    private TextView tv_lokasi;
    private RatingBar rating1;

    private String initRating;

    private ProgressBar progressBar;

    private String id_order;

    private String id, nama, usia, pekerjaan, gender, email, pass, telpon, path, status;

    private List<PekerjaModel> pekerjaModels = new ArrayList<>();
    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktifitas_pelanggan);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        tv_tgl = findViewById(R.id.tv_tgl);
        img1 = findViewById(R.id.img1);
        tv_jam = findViewById(R.id.tv_jam);
        tv_title = findViewById(R.id.tv_title);
        tv_tarif = findViewById(R.id.tv_tarif);
        tv_penerima = findViewById(R.id.tv_penerima);
        tv_lokasi = findViewById(R.id.tv_lokasi);
        rating1 = findViewById(R.id.rating);
        rating1.setIsIndicator(true);


        rating1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if (initRating.isEmpty()){
                    new SweetAlertDialog(AktifitasPelangganActivity.this)
                            .setTitleText("Memberi Bintang "+rating+" ?")
                            .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                rating1.setRating(0);
                                    sweetAlertDialog.cancel();


                                }
                            })
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    updateRatingOrder(id_order, rating);
                                    sweetAlertDialog.cancel();
                                }
                            })
                            .show();
                }



            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ambilData();
    }

    private void updateRatingOrder(String id_order, float rating) {

        String sendRating = String.valueOf(rating);

        ApiRequsetPelanggan apiRequsetPelanggan  = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<OrderResponModel> orderResponModelCall = apiRequsetPelanggan.updateRatingOrder(id_order, sendRating);
        orderResponModelCall.enqueue(new Callback<OrderResponModel>() {
            @Override
            public void onResponse(Call<OrderResponModel> call, Response<OrderResponModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    Log.e("updateDatabaseTerima", "Kode"+ response.body().getKode());

                }
                Log.e("updateDatabaseTerima", "Gagal");
            }

            @Override
            public void onFailure(Call<OrderResponModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("updateDatabaseTerima", "Error");
            }
        });

    }

    private void ambilData(){

        Result result = getIntent().getParcelableExtra(EXTRA_DATA);

        if (result != null){

            id_order = result.getmIdOrder();
            tv_tgl.setText(result.getmTanggalOrder());
            tv_jam.setText(result.getmJamOrder());
            tv_title.setText(result.getmKeteranganOrder());
            tv_tarif.setText("Rp. "+result.getmBiayaOrder());
            tv_lokasi.setText(result.getmAlamatOrder());

            initRating = result.getmRatinglOrder();
            if (!initRating.isEmpty()){
                rating1.setIsIndicator(true);
                rating1.setRating(Float.parseFloat(initRating));
            } else {
                rating1.setIsIndicator(false);
            }

            String id_pekerja = result.getmIdPelanggan();
            getPekerja(id_pekerja);

        }

    }

    private void getPekerja(String id_pekerja) {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<List<PekerjaModel>> listCall = apiRequestPekerja.readOneIDPekerja(id_pekerja);
        listCall.enqueue(new Callback<List<PekerjaModel>>() {
            @Override
            public void onResponse(Call<List<PekerjaModel>> call, Response<List<PekerjaModel>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    pekerjaModels = response.body();
                    initData(pekerjaModels);

                } else {
                    Log.e("Retro", "Respon : Not Success");
                }
            }

            @Override
            public void onFailure(Call<List<PekerjaModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Retro", "Respon : ERROR > " + t);
            }
        });

    }

    private void initData(List<PekerjaModel> list) {

        for (int a = 0; a < pekerjaModels.size(); a++) {

            id = pekerjaModels.get(a).getId_pekerja();
            nama = pekerjaModels.get(a).getNama_pekerja();
            pekerjaan = pekerjaModels.get(a).getPekerjaan_pekerja();
            gender = pekerjaModels.get(a).getGender_pekerja();
            email = pekerjaModels.get(a).getEmail_pekerja();
            pass = pekerjaModels.get(a).getPass_pekerja();
            telpon = pekerjaModels.get(a).getTelpon_pekerja();
            path = pekerjaModels.get(a).getPath_profil();
            status = pekerjaModels.get(a).getStatus();
            usia = pekerjaModels.get(a).getUsia_pekerja();

        }

        tv_penerima.setText(nama);

    }

}
