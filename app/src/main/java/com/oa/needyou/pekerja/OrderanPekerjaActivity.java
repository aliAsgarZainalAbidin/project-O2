package com.oa.needyou.pekerja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.needyou.needyou.R;
import com.oa.needyou.URL_IP;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.model.OrderModel;
import com.oa.needyou.pekerja.model.OrderResponModel;
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

public class OrderanPekerjaActivity extends AppCompatActivity {

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


    private final String GET_ID1 = "get_id";
    private final String GET_ID_PEKERJA = "get_id_pekerja";
    private final String GET_ID_PELANGGAN = "get_id_pelanggan";
    private final String GET_PATH = "get_path";
    private final String GET_KETERANGAN = "get_keterangan";
    private final String GET_BIAYA = "get_biaya";
    private final String GET_ALAMAT = "get_alamat";
    private final String GET_LATITUDE = "get_latitude";
    private final String GET_LONGITUDE = "get_longitude";
    private final String GET_STATUS = "get_status";
    private final String GET_TANGGAL = "get_tanggal";
    private final String GET_JAM = "get_jam";
    private final String GET_KODE = "get_kode";


    private String idPekerja;

    private String id, nama, email, pass, telpon, gender, alamat, path, status;

    private List<Result> results = new ArrayList<>();

    private ProgressBar progressBar;

    private int[] imageArray = {R.drawable.btn0, R.drawable.btn0, R.drawable.btn1,
            R.drawable.btn2, R.drawable.btn3,
            R.drawable.btn4, R.drawable.btn5,
            R.drawable.btn6, R.drawable.btn7,
            R.drawable.btn8, R.drawable.btn9,
            R.drawable.btn10};

    private final String GET_ID = "get_id";

    private ImageView btn_terima;
    private ImageView btn_tolak;
    private TextView tv_time;

    private ImageView img_order;
    private TextView input_keterangan;
    private TextView input_biaya;
    private TextView et_nama;
    private TextView et_telpon;
    private TextView et_alamat;

    private boolean isCanceled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderan_pekerja);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        img_order = findViewById(R.id.img_order);
        input_keterangan = findViewById(R.id.input_keterangan);
        input_biaya = findViewById(R.id.input_biaya);
        et_nama = findViewById(R.id.et_nama_update);
        et_telpon = findViewById(R.id.et_telpon_update);
        et_alamat = findViewById(R.id.et_alamat_update);
        btn_terima = findViewById(R.id.btn_terima);
        btn_tolak = findViewById(R.id.btn_tolak);
        tv_time = findViewById(R.id.tv_time);

        btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString(GET_ID1, id_order);
                bundle.putString(GET_ID_PEKERJA, id_pekerja);
                bundle.putString(GET_ID_PELANGGAN, id_pelanggan);
                bundle.putString(GET_PATH, path_order);
                bundle.putString(GET_KETERANGAN, keterangan_order);
                bundle.putString(GET_BIAYA, biaya_order);
                bundle.putString(GET_ALAMAT, alamat_order);
                bundle.putString(GET_LATITUDE, latitude_order);
                bundle.putString(GET_LONGITUDE, longitude_order);
                bundle.putString(GET_STATUS, status_order);
                bundle.putString(GET_TANGGAL, tanggal_order);
                bundle.putString(GET_JAM, jam_order);
                bundle.putString(GET_KODE, kode_order);

                isCanceled=true;
                updateFirebaseTerima();
                updateDatabaseTerima();
                Intent intent = new Intent(OrderanPekerjaActivity.this, DeliveryPekerjaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();


            }
        });

        btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFirebaseTolak();
                updateDatabaseTolak();
                updateFirebaseKode();
                isCanceled=true;
                finish();
            }
        });

        getBundle();
    }

    private void updateDatabaseTerima() {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderResponModel> orderResponModelCall = apiRequestPekerja.updateStatusOrder(id_order,"Delivery");
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

    private void updateDatabaseTolak() {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderResponModel> orderResponModelCall = apiRequestPekerja.updateStatusOrder(id_order,"Tolak");
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

    private void updateFirebaseKode() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(idPekerja).child("kode_order");
        myRef.setValue("-");
    }

    private void updateFirebaseTolak() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(idPekerja).child("status_pekerja");
        myRef.setValue("Aktif");
    }

    private void updateFirebaseTerima() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(idPekerja).child("status_pekerja");
        myRef.setValue("Delivery");

    }

    private void loopWaktu() {

        new CountDownTimer(11000, 1000) {
            int no = 10;

            public void onTick(long millisUntilFinished) {

                if (isCanceled){
                    cancel();
                } else {
                    int index = (int) (millisUntilFinished / 1000);
                    btn_terima.setImageResource(imageArray[index]);
                    tv_time.setText(String.valueOf(no));
                    no--;
                }
            }

            public void onFinish() {
//                Toast.makeText(OrderanPekerjaActivity.this, "Selesai", Toast.LENGTH_SHORT).show();
                updateFirebaseTolak();
                updateDatabaseTolak();
                finish();
            }

        }.start();
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        idPekerja = bundle.getString(GET_ID);

        getDataFirebase(idPekerja);

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
        Call<OrderModel> orderModelCall = apiRequestPekerja.getDataOrderSingle(kode_order);
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
            Picasso.with(OrderanPekerjaActivity.this).load(foto).into(img_order);
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
                    loopWaktu();

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

    @Override
    protected void onDestroy() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int id = Integer.parseInt(id_pekerja);
        if ( id > 0){
            DatabaseReference myRef = database.getReference("Pekerja").child(String.valueOf(id)).child("status_pekerja");
            myRef.setValue("Non-Aktif");
        }
        super.onDestroy();
    }
}
