package com.oa.needyou.pekerja.pendapatan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.needyou.needyou.R;
import com.oa.needyou.URL_IP;
import com.oa.needyou.pekerja.DashboardPekerjaActivity;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.model.OrderModel;
import com.oa.needyou.pekerja.model.OrderResponModel;
import com.oa.needyou.pekerja.model.PekerjaModel;
import com.oa.needyou.pekerja.model.Result;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.PelangganModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSibukActivity extends AppCompatActivity {

    private String id_order;
    private String id_pekerja;
    private String id_pelanggan;
    private String path_order;
    private String keterangan_order;
    private String biaya_order;
    private String alamat_order;
    private String latitude_order;
    private String longitude_order;
    private String status_order;
    private String tanggal_order;
    private String jam_order;
    private String kode_order;

    private ProgressBar progressBar;

    private Button btn_terima;

    private ImageView img_order;
    private TextView input_keterangan;
    private TextView input_biaya;
    private TextView et_nama;
    private TextView et_telpon;
    private TextView et_alamat;

    private final String GET_ID = "get_id";
    private String id_pekerja2;


    private final String GET_NAMA = "get_nama";
    private final String GET_USIA = "get_usia";
    private final String GET_PEKERJAAN = "get_pekerjaan";
    private final String GET_GENDER = "get_gender";
    private final String GET_EMAIL = "get_email";
    private final String GET_PASS = "get_pass";
    private final String GET_TELPON = "get_telpon";
    private final String GET_PATH = "get_path";
    private final String GET_STATUS = "get_status";
    private String id2, nama, email, pass, telpon, gender, alamat, path, status;
    private String id, nama2, email2, pass2, telpon2, gender2, alamat2, path2, status2,pekerjaan2,usia2;

    private List<Result> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sibuk);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        img_order = findViewById(R.id.img_order);
        input_keterangan = findViewById(R.id.input_keterangan);
        input_biaya = findViewById(R.id.input_biaya);
        et_nama = findViewById(R.id.et_nama_update);
        et_telpon = findViewById(R.id.et_telpon_update);
        et_alamat = findViewById(R.id.et_alamat_update);
        btn_terima = findViewById(R.id.btn_terima);

        btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabaseSelesai();
                updateFirebaseSelesai();
                updateFirebaseKodeSelesai();

                intentToDashboard(id_pekerja2);

            }
        });

        getBundle();

    }

    private void intentToDashboard(String id_pekerja) {
        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<List<PekerjaModel>> listCall = apiRequestPekerja.readOneIDPekerja(id_pekerja);
        listCall.enqueue(new Callback<List<PekerjaModel>>() {
            @Override
            public void onResponse(Call<List<PekerjaModel>> call, Response<List<PekerjaModel>> response) {
                if (response.isSuccessful()){

                    for (int a = 0; a < response.body().size(); a++) {

                        id = response.body().get(a).getId_pekerja();
                        nama2 = response.body().get(a).getNama_pekerja();
                        pekerjaan2 = response.body().get(a).getPekerjaan_pekerja();
                        gender2 = response.body().get(a).getGender_pekerja();
                        email2 = response.body().get(a).getEmail_pekerja();
                        pass2 = response.body().get(a).getPass_pekerja();
                        telpon2 = response.body().get(a).getTelpon_pekerja();
                        path2 = response.body().get(a).getPath_profil();
                        status2 = response.body().get(a).getStatus();
                        usia2 = response.body().get(a).getUsia_pekerja();

                        Bundle bundle = new Bundle();
                        bundle.putString(GET_ID, id);
                        bundle.putString(GET_NAMA, nama2);
                        bundle.putString(GET_USIA, usia2);
                        bundle.putString(GET_PEKERJAAN, pekerjaan2);
                        bundle.putString(GET_GENDER, gender2);
                        bundle.putString(GET_EMAIL, email2);
                        bundle.putString(GET_PASS, pass2);
                        bundle.putString(GET_TELPON, telpon2);
                        bundle.putString(GET_PATH, path2);
                        bundle.putString(GET_STATUS, status2);
                        Intent intent = new Intent(DetailSibukActivity.this, DashboardPekerjaActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    }


                }
            }

            @Override
            public void onFailure(Call<List<PekerjaModel>> call, Throwable t) {

            }
        });
    }

    private void updateDatabaseSelesai() {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderResponModel> orderResponModelCall = apiRequestPekerja.updateStatusOrder(id_order,"Selesai");
        orderResponModelCall.enqueue(new Callback<OrderResponModel>() {
            @Override
            public void onResponse(Call<OrderResponModel> call, Response<OrderResponModel> response) {
                if (response.isSuccessful()){
                    Log.e("updateDatabaseTerima", "Kode"+ response.body().getKode());
                }
                Log.e("updateDatabaseTerima", "Gagal");
            }

            @Override
            public void onFailure(Call<OrderResponModel> call, Throwable t) {
                Log.e("updateDatabaseTerima", "Error");
            }
        });

    }

    private void updateFirebaseSelesai() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(id_pekerja2).child("status_pekerja");
        myRef.setValue("Aktif");

    }

    private void updateFirebaseKodeSelesai() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(id_pekerja2).child("kode_order");
        myRef.setValue("-");

    }

    private void getDataFirebase(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(id).child("kode_order");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String kode_order = (String) dataSnapshot.getValue();

                getDataOrder(kode_order);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataOrder(String kode_order) {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderModel> orderModelCall = apiRequestPekerja.getDataOrderkode(kode_order);
        orderModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    results = response.body().getResult();
                    Log.e("Retro", "Kode : " + response.body().getKode());
                    Log.e("Retro", "RESULT : " + results);
//                    Log.e("Retro", "RESULT : " + results.get(0).getIdPekerja());

                    initResultOrder(results);
                } else {
                    Log.e("Retro", "Gagal");
                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                Log.e("Retro", "Error : " + t);
            }
        });

    }

    private void initResultOrder(List<Result> results2) {

        for (int i = 0; i < results.size(); i++) {

            id_order = results.get(i).getmIdOrder();
            id_pekerja = results.get(i).getmIdPekerja();
            id_pelanggan = results.get(i).getmIdPelanggan();
            path_order = results.get(i).getmPathOrder();
            keterangan_order = results.get(i).getmKeteranganOrder();
            biaya_order = results.get(i).getmBiayaOrder();
            alamat_order = results.get(i).getmAlamatOrder();
            latitude_order = results.get(i).getmLatitudeOrder();
            longitude_order = results.get(i).getmLongitudeOrder();
            status_order = results.get(i).getmStatusOrder();
            tanggal_order = results.get(i).getmTanggalOrder();
            jam_order = results.get(i).getmJamOrder();
            kode_order = results.get(i).getmKodeOrder();
            String foto = URL_IP.ALAMAT_IP+path_order;
            Picasso.with(DetailSibukActivity.this).load(foto).into(img_order);
            input_keterangan.setText(keterangan_order);
            input_biaya.setText("Rp. " + biaya_order);
            et_alamat.setText(alamat_order);
            readPelangganId(id_pelanggan);
        }
    }

    private void readPelangganId(String id_pelanggan) {

        ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<List<PelangganModel>> listCall = apiRequsetPelanggan.readIdPelanggan(id_pelanggan);
        listCall.enqueue(new Callback<List<PelangganModel>>() {
            @Override
            public void onResponse(Call<List<PelangganModel>> call, Response<List<PelangganModel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    for (int i = 0; i < response.body().size(); i++) {

                        id2 = response.body().get(i).getId_pelanggan();
                        nama = response.body().get(i).getNama_pelanggan();
                        email = response.body().get(i).getEmail_pelanggan();
                        pass = response.body().get(i).getPassword_pelanggan();
                        telpon = response.body().get(i).getTelpon_pelanggan();
                        gender = response.body().get(i).getGender_pelanggan();
                        alamat = response.body().get(i).getAlamat_pelanggan();
                        path = response.body().get(i).getPath_pelanggan();
                        status = response.body().get(i).getStatus_pelanggan();
                        et_nama.setText(nama);
                        et_telpon.setText(telpon);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PelangganModel>> call, Throwable t) {

            }
        });


    }



    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id_pekerja2 = bundle.getString(GET_ID);
        getDataFirebase(id_pekerja2);
    }
}
