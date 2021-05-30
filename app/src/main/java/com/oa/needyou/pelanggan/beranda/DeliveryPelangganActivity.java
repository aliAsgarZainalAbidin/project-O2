package com.oa.needyou.pelanggan.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.needyou.needyou.R;
import com.oa.needyou.URL_IP;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.model.PekerjaModel;
import com.oa.needyou.pelanggan.DashboardPelangganActivity;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.PelangganModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryPelangganActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap map;
    private GoogleMap map2;
    private LatLng latLng;
    private LatLng latLng2;

    private ProgressBar progressBar;

    private String id, nama, usia, pekerjaan, gender, email, pass, telpon, path, status;

    private final String GET_ID = "get_id";
    private final String GET_NAMA = "get_nama";
    private final String GET_GENDER = "get_gender";
    private final String GET_EMAIL = "get_email";
    private final String GET_PASS = "get_pass";
    private final String GET_TELPON = "get_telpon";
    private final String GET_PATH = "get_path";
    private final String GET_STATUS = "get_status";
    private final String GET_ALAMAT = "get_alamat";

    private final String GET_ID_PEKERJA = "get_id_pekerja";

    private String id_pelanggan, id_pekerja;
    private String getLatitude, getLongitude;

    private TextView tv_nama;
    private TextView tv_pekerjaan;
    private TextView tv_usia;
    private ImageView img_foto;
    private TextView tv_home;

    private String id_firebase_pekerja;
    private String latitude_firebase_pekerja;
    private String longitude_firebase_pekerja;
    private String nama_firebase_pekerja;
    private String status_firebase_pekerja;

    private List<String> firebasePekerjaModels = new ArrayList<>();

    private List<PekerjaModel> pekerjaModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_pelanggan);



        img_foto = findViewById(R.id.img_foto);
        tv_home = findViewById(R.id.tv_home);
        tv_nama = findViewById(R.id.tv_nama);
        tv_pekerjaan = findViewById(R.id.tv_pekerjaan);
        tv_usia = findViewById(R.id.tv_usia);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(DeliveryPelangganActivity.this)
                        .setTitleText("Kembal Ke Beranda ?")
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                intentToDashboard(id_pelanggan);


                            }
                        })
                        .show();

            }
        });


        getBundle();

    }

    private void intentToDashboard(String id_pelanggan) {

        ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<List<PelangganModel>> listCall = apiRequsetPelanggan.readIdPelanggan(id_pelanggan);
        listCall.enqueue(new Callback<List<PelangganModel>>() {
            @Override
            public void onResponse(Call<List<PelangganModel>> call, Response<List<PelangganModel>> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().size(); i++) {

                        String namaPelanggan = response.body().get(i).getNama_pelanggan();
                        String emailPelanggan = response.body().get(i).getEmail_pelanggan();
                        String passwordPelanggan = response.body().get(i).getPassword_pelanggan();
                        String telponPelanggan = response.body().get(i).getTelpon_pelanggan();
                        String genderPelanggan = response.body().get(i).getGender_pelanggan();
                        String alamatPelanggan = response.body().get(i).getAlamat_pelanggan();
                        String pathPelanggan = response.body().get(i).getPath_pelanggan();
                        String statusPelanggan = response.body().get(i).getStatus_pelanggan();

                        Bundle bundle = new Bundle();
                        bundle.putString(GET_ID, id_pelanggan);
                        bundle.putString(GET_NAMA, namaPelanggan);
                        bundle.putString(GET_EMAIL, emailPelanggan);
                        bundle.putString(GET_PASS, passwordPelanggan);
                        bundle.putString(GET_TELPON, telponPelanggan);
                        bundle.putString(GET_GENDER, genderPelanggan);
                        bundle.putString(GET_ALAMAT, alamatPelanggan);
                        bundle.putString(GET_PATH, pathPelanggan);
                        bundle.putString(GET_STATUS, statusPelanggan);

                        Intent intent = new Intent(DeliveryPelangganActivity.this, DashboardPelangganActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
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

        if (bundle != null) {
            id_pelanggan = bundle.getString(GET_ID);
            id_pekerja = bundle.getString(GET_ID_PEKERJA);
            getLatitude = bundle.getString("latitude");
            getLongitude = bundle.getString("longitude");

            latLng2 = new LatLng(Double.valueOf(getLatitude),Double.valueOf(getLongitude));

//            Toast.makeText(this, id_pekerja+" - "+getLatitude + " - "+getLongitude, Toast.LENGTH_SHORT).show();

            getDataPekerja(id_pekerja);
            getDataPekerjaFireBase(id_pekerja);

        } else {
            Toast.makeText(this, "Data Bundle Kosong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataPekerjaFireBase(String id_pekerja) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Pekerja").child(id_pekerja);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                FirebasePekerjaModel model = new FirebasePekerjaModel();

                id_firebase_pekerja = dataSnapshot.child("id_pekerja").getValue(String.class);
                latitude_firebase_pekerja = dataSnapshot.child("latitude_pekerja").getValue(String.class);
                longitude_firebase_pekerja = dataSnapshot.child("longitude_pekerja").getValue(String.class);
                nama_firebase_pekerja = dataSnapshot.child("nama_pekerja").getValue(String.class);
                status_firebase_pekerja = dataSnapshot.child("status_pekerja").getValue(String.class);
//                Toast.makeText(DeliveryPelangganActivity.this, a, Toast.LENGTH_SHORT).show();

                procesDataFirebase(id_firebase_pekerja,latitude_firebase_pekerja,longitude_firebase_pekerja,
                        nama_firebase_pekerja,status_firebase_pekerja);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void procesDataFirebase(String id_firebase_pekerja, String latitude_firebase_pekerja,
                                    String longitude_firebase_pekerja, String nama_firebase_pekerja,
                                    String status_firebase_pekerja) {



        latLng = new LatLng(Double.valueOf(latitude_firebase_pekerja), Double.valueOf(longitude_firebase_pekerja));

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

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
        String foto = URL_IP.ALAMAT_IP+path;
        Glide.with(DeliveryPelangganActivity.this).load(foto).error(R.drawable.img_circle).placeholder(R.drawable.img_circle).into(img_foto);

//        Picasso.with(DeliveryPelangganActivity.this).load(foto).error(R.drawable.img_circle).placeholder(R.drawable.img_circle).into(img_foto);
        tv_nama.setText(nama);
        tv_pekerjaan.setText(pekerjaan);
        tv_usia.setText(usia);

    }

    private void getDataPekerja(String id_pekerja) {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<List<PekerjaModel>> listCall = apiRequestPekerja.readOneIDPekerja(id_pekerja);
        listCall.enqueue(new Callback<List<PekerjaModel>>() {
            @Override
            public void onResponse(Call<List<PekerjaModel>> call, Response<List<PekerjaModel>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    pekerjaModels = response.body();
                    initData(pekerjaModels);

                } else {
                    Log.e("Retro", "Respon : Not Success");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PekerjaModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Retro", "Respon : ERROR > " + t);
            }
        });
    }

    private BitmapDescriptor bitmapDescriptor(Context context, int vactorResid) {

        int height = 80;
        int width = 80;
        Drawable vectorDrawble = ContextCompat.getDrawable(context, vactorResid);
        vectorDrawble.setBounds(0, 0, height, width);
        Bitmap bitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawble.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map2 = googleMap;
        map.clear();
        map.addMarker(new MarkerOptions().position(latLng).title("Posisi Pekerja").icon(bitmapDescriptor(this, R.drawable.icon_worker)));

        map2.addMarker(new MarkerOptions().position(latLng2).title("Lokasi Pekerjaan").icon(bitmapDescriptor(this, R.drawable.ic_person_pin_circle_blue)));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(latLng);
        builder.include(latLng2);
        LatLngBounds bounds = builder.build();
        int padding = 10; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map2.animateCamera(cu);


    }
}
