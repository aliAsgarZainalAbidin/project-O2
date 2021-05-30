package com.oa.needyou.pekerja;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.beranda.model.ResultSaldo;
import com.oa.needyou.pekerja.beranda.model.SaldoResponModel;
import com.oa.needyou.pekerja.directionhelpers.TaskLoadedCallback;
import com.oa.needyou.pekerja.model.OrderResponModel;
import com.oa.needyou.pekerja.model.PekerjaModel;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.PelangganModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryPekerjaActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        TaskLoadedCallback {


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

    private GoogleMap map;
    private GoogleMap map2;
    private LatLng latLng1;
    private LatLng latLng2;
    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private LocationManager mLocationManager;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    private boolean isPermission;

    private long UPDATE_INTERVAL = 2000;
    private long FASTES_INTERVAL = 5000;

    private ProgressBar progressBar;

    private boolean firstTime = true;

    private MarkerOptions mark1,mark2;
    private Polyline currentPolyline;

    private ImageView btn_my_location;
    private ImageView img_call;
    private ImageView img_sms;

    private TextView btn_sampai;
    private TextView tv_nama_pelanggan;
    private TextView tv_alamat_pekerjaan;
    private String telpon;

    private final String GET_ID = "get_id";
    private final String GET_NAMA = "get_nama";
    private final String GET_USIA = "get_usia";
    private final String GET_PEKERJAAN = "get_pekerjaan";
    private final String GET_GENDER = "get_gender";
    private final String GET_EMAIL = "get_email";
    private final String GET_PASS = "get_pass";
    private final String GET_TELPON = "get_telpon";

    private String id;
    private String nama;
    private String usia;
    private String pekerjaan;
    private String gender;
    private String email;
    private String pass;
    private String telpon2;
    private String path;
    private String status;

    private String saldo = "0";
    private List<ResultSaldo> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_pekerja);
        btn_my_location = findViewById(R.id.btn_my_location);
        img_sms = findViewById(R.id.img_sms);
        img_call = findViewById(R.id.img_call);
        btn_sampai = findViewById(R.id.btn_sampai);
        tv_alamat_pekerjaan = findViewById(R.id.tv_alamat_pekerjaan);
        tv_nama_pelanggan = findViewById(R.id.tv_nama_pelanggan);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getBundle();

        btn_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DeliveryPekerjaActivity.this)
                        .setTitleText("Anda Tiba di Tujuan.")
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                updateFirebaseSibuk(id_pekerja);
                                updateDatabaseSibuk();
                                updateSaldo(id_pekerja);
                                intentToDashboard(id_pekerja);


                            }
                        })
                        .show();
            }
        });

        img_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeliveryPekerjaActivity.this, "Soon..", Toast.LENGTH_SHORT).show();

            }
        });

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telpon));
                startActivity(intent);
            }
        });

        btn_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng1);
                builder.include(latLng2);
                LatLngBounds bounds = builder.build();
//            int padding = 5; // offset from edges of the map in pixels

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.15); // offset from edges of the map 15% of screen
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,  width, height, padding);
                map2.animateCamera(cu);

            }
        });

        if (requestSinglePermission()) {
//            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_pekerja);
//            assert supportMapFragment != null;
//            supportMapFragment.getMapAsync(this);

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            checkLocation();
        }
    }

    private void updateSaldo(String id_pekerja) {

        long saldoUpdate = Long.parseLong(saldo)-2000;

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderResponModel> orderResponModelCall = apiRequestPekerja.updateSaldoPekerja(id_pekerja, String.valueOf(saldoUpdate));
        orderResponModelCall.enqueue(new Callback<OrderResponModel>() {
            @Override
            public void onResponse(Call<OrderResponModel> call, Response<OrderResponModel> response) {
                if (response.isSuccessful()) {
                    Log.e("updateDatabaseTerima", "Kode" + response.body().getKode());
                }
                Log.e("updateDatabaseTerima", "Gagal");
            }

            @Override
            public void onFailure(Call<OrderResponModel> call, Throwable t) {
                Log.e("updateDatabaseTerima", "Error");
            }
        });

    }

    private void updateFirebaseSibuk(String id_pekerja) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(id_pekerja).child("status_pekerja");
        myRef.setValue("Sibuk");

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
                        nama = response.body().get(a).getNama_pekerja();
                        pekerjaan = response.body().get(a).getPekerjaan_pekerja();
                        gender = response.body().get(a).getGender_pekerja();
                        email = response.body().get(a).getEmail_pekerja();
                        pass = response.body().get(a).getPass_pekerja();
                        telpon2 = response.body().get(a).getTelpon_pekerja();
                        path = response.body().get(a).getPath_profil();
                        status = response.body().get(a).getStatus();
                        usia = response.body().get(a).getUsia_pekerja();



                        Bundle bundle = new Bundle();
                        bundle.putString(GET_ID, id);
                        bundle.putString(GET_NAMA, nama);
                        bundle.putString(GET_USIA, usia);
                        bundle.putString(GET_PEKERJAAN, pekerjaan);
                        bundle.putString(GET_GENDER, gender);
                        bundle.putString(GET_EMAIL, email);
                        bundle.putString(GET_PASS, pass);
                        bundle.putString(GET_TELPON, telpon2);
                        bundle.putString(GET_PATH, path);
                        bundle.putString(GET_STATUS, status);
                        Intent intent = new Intent(DeliveryPekerjaActivity.this, DashboardPekerjaActivity.class);
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

    private boolean checkLocation() {

        if (!isLocationEnable()) {
            showAlert();
        }
        return isLocationEnable();

    }


    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Yout Locations Settings is set to 'off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnable() {
        locationManager = (LocationManager) Objects.requireNonNull(this).getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        return isPermission;
    }


    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id_order = bundle.getString(GET_ID1);
        id_pekerja = bundle.getString(GET_ID_PEKERJA);
        id_pelanggan = bundle.getString(GET_ID_PELANGGAN);
        String GET_PATH2 = "get_path";
        path_order = bundle.getString(GET_PATH2);
        keterangan_order = bundle.getString(GET_KETERANGAN);
        biaya_order = bundle.getString(GET_BIAYA);
        alamat_order = bundle.getString(GET_ALAMAT);
        latitude_order = bundle.getString(GET_LATITUDE);
        longitude_order = bundle.getString(GET_LONGITUDE);
        String GET_STATUS2 = "get_status";
        status_order = bundle.getString(GET_STATUS2);
        tanggal_order = bundle.getString(GET_TANGGAL);
        jam_order = bundle.getString(GET_JAM);
        kode_order = bundle.getString(GET_KODE);

        tv_alamat_pekerjaan.setText(alamat_order);

        getDataPelanggan(id_pelanggan);
        getSaldoPekerja(id_pekerja);

        Log.e("Pelanggan","Latitude : "+latitude_order);

        latLng2 = new LatLng(Double.valueOf(latitude_order), Double.valueOf(longitude_order));
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_pekerja);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

    }

    private void getSaldoPekerja(String id_pekerja) {
        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<SaldoResponModel> resultSaldoCall = apiRequestPekerja.getDataSaldo(id_pekerja);
        resultSaldoCall.enqueue(new Callback<SaldoResponModel>() {
            @Override
            public void onResponse(Call<SaldoResponModel> call, Response<SaldoResponModel> response) {
                if (response.isSuccessful()) {

                    results = response.body().getResult();
                    initSaldo(results);
                }
            }

            @Override
            public void onFailure(Call<SaldoResponModel> call, Throwable t) {

            }
        });
    }

    private void initSaldo(List<ResultSaldo> results) {
        for (int i = 0; i < results.size(); i++) {
            saldo = results.get(i).getJumlahSaldo();
        }
    }

    private void getDataPelanggan(String id_pelanggan) {

        ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<List<PelangganModel>> listCall = apiRequsetPelanggan.readIdPelanggan(id_pelanggan);
        listCall.enqueue(new Callback<List<PelangganModel>>() {
            @Override
            public void onResponse(Call<List<PelangganModel>> call, Response<List<PelangganModel>> response) {
                if (response.isSuccessful()){
                    for (int i = 0; i < response.body().size(); i++) {

                        tv_nama_pelanggan.setText(response.body().get(i).getNama_pelanggan());
                        telpon = response.body().get(i).getTelpon_pelanggan();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PelangganModel>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {


//        String latitude = Double.toString(location.getLatitude());
//        String longitude = Double.toString(location.getLongitude());

        latLng1 = new LatLng(location.getLatitude(), location.getLongitude());

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database1.getReference("Pekerja");
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        myRef.child(id_pekerja).child("latitude_pekerja").setValue(latitude);
        myRef.child(id_pekerja).child("longitude_pekerja").setValue(longitude);

        map.clear();
//        map2.clear();
        mark1 = new MarkerOptions().position(latLng2)
                .title("Lokasi Pekerjaan")
                .icon(bitmapDescriptor(this, R.drawable.ic_person_pin_circle_blue));
        mark2 = new MarkerOptions().position(latLng1).icon(bitmapDescriptor(this, R.drawable.icon_worker));

        map.addMarker(mark1);
        map.addMarker(mark2);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_pekerja);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        //move map camera
        if (firstTime) {
            firstTime = false;

//            String url = getUrl(mark1.getPosition(), mark2.getPosition(),"driving");
//            new FetchURL(DeliveryPekerjaActivity.this).execute(url, "driving");

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(latLng1);
            builder.include(latLng2);
            LatLngBounds bounds = builder.build();
//            int padding = 5; // offset from edges of the map in pixels

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.15); // offset from edges of the map 15% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,  width, height, padding);
            map.animateCamera(cu);
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdate();
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (currentLocation == null){
            startLocationUpdate();
        }

    }

    private void startLocationUpdate() {

        progressBar.setVisibility(View.GONE);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTES_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    }

    private void updateDatabaseSibuk() {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderResponModel> orderResponModelCall = apiRequestPekerja.updateStatusOrder(id_order, "Sibuk");
        orderResponModelCall.enqueue(new Callback<OrderResponModel>() {
            @Override
            public void onResponse(Call<OrderResponModel> call, Response<OrderResponModel> response) {
                if (response.isSuccessful()) {
                    Log.e("updateDatabaseTerima", "Kode" + response.body().getKode());
                }
                Log.e("updateDatabaseTerima", "Gagal");
            }

            @Override
            public void onFailure(Call<OrderResponModel> call, Throwable t) {
                Log.e("updateDatabaseTerima", "Error");
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        if (mGoogleApiClient != null){
            mGoogleApiClient.connect();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }
}
