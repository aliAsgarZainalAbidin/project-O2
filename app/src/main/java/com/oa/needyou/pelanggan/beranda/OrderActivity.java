package com.oa.needyou.pelanggan.beranda;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.needyou.needyou.R;
import com.oa.needyou.pelanggan.DashboardPelangganActivity;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.OrderModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private final String GET_ID = "get_id";
    private final String GET_ID_PEKERJA = "get_id_pekerja";
    private String id;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    private GoogleMap map;
    private String latitud2, longitud2;
    private String latitudKirim, longitudKirim;


    private Button btn_cancle;
    private TextView tv_back;
    private TextView tv_tbn_sekarang;
    private TextView tv_alamat_map;
    private ImageView imageView, img_foto, btn_cari_pekerja, btn_my_location;
    private EditText input_keterangan;
    private EditText input_biaya;
    private EditText input_alamat;

    private String alamat_latlig;
    private Bitmap bitmap;
    private CardView cv_img;

    private boolean firstTime = true;
    private boolean sekali_ubah = true;
    private LatLng latLng;

    private Handler handler = new Handler();
    private String tanggal, jam;

    PlaceAutocompleteFragment placeAutoComplete;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private RelativeLayout slidingUpMap;
    private RelativeLayout relativ_loading;

    private ArrayList<PekerjaFirebaseModel> models = new ArrayList<>();
    private Map<Integer, String> mapPekerja = new HashMap<>();

    private int random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getBundle();

        input_keterangan = findViewById(R.id.input_keterangan);
        input_biaya = findViewById(R.id.input_biaya);
        input_alamat = findViewById(R.id.input_alamat);

        pekerjaFirebase();

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d("Maps", "Place selected: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        handler.postDelayed(runnable, 1000);
        slidingUpMap = findViewById(R.id.slidingUpMap);
        btn_my_location = findViewById(R.id.btn_my_location);
        relativ_loading = findViewById(R.id.relativ_loading);
        btn_cancle = findViewById(R.id.btn_cancle);
        tv_tbn_sekarang = findViewById(R.id.tv_tbn_sekarang);
        slidingUpMap.setVisibility(View.GONE);

        relativ_loading.setVisibility(View.GONE);
        btn_cari_pekerja = findViewById(R.id.btn_cari_pekerja);
        btn_cari_pekerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (img_foto.getDrawable() == null) {

                    Toast.makeText(OrderActivity.this, "Gambar Kosong", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(input_keterangan.getText())) {
                    input_keterangan.setError("Lengkapi");
                } else if (TextUtils.isEmpty(input_alamat.getText())) {
                    Toast.makeText(OrderActivity.this, "Lokasi Kerjaan Belum di Tentukan!", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(input_biaya.getText())) {
                    input_biaya.setError("Lengkapi");
                } else {

                    String id_random = String.valueOf(random);
//                    Toast.makeText(OrderActivity.this, "Kirim", Toast.LENGTH_SHORT).show();

//                    sendDataOrder(id,id_random,latitudKirim,longitudKirim);

                    relativ_loading.setVisibility(View.VISIBLE);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Pekerja").child(id_random).child("status_pekerja");
                    Log.d("TAG", "onClick: " + myRef.getPath());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String status = (String) dataSnapshot.getValue();

                            if (status != null) {
                                if (status.equals("Aktif") && sekali_ubah) {
                                    myRef.setValue("Menunggu");
                                    sendDataOrder(id, id_random, latitudKirim, longitudKirim);
                                    sekali_ubah = false;
                                }

                                if (status.equals("Delivery")) {

                                    Bundle bundle = new Bundle();
                                    bundle.putString(GET_ID, id);
                                    bundle.putString(GET_ID_PEKERJA, id_random);
                                    bundle.putString("latitude", latitudKirim);
                                    bundle.putString("longitude", longitudKirim);
                                    Intent intent = new Intent(OrderActivity.this, DeliveryPelangganActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        tv_alamat_map = findViewById(R.id.tv_alamat_map);
        tv_back = findViewById(R.id.tv_back);
        img_foto = findViewById(R.id.img_foto);
        cv_img = findViewById(R.id.cv_img);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, DashboardPelangganActivity.class);
                startActivity(intent);
            }
        });

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pekerjaFirebase();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Pekerja").child(String.valueOf(random)).child("status_pekerja");
                myRef.setValue("Aktif");
                sekali_ubah = true;
                relativ_loading.setVisibility(View.GONE);

            }
        });

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpMap.setVisibility(View.GONE);
            }
        });


        input_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpMap.setVisibility(View.VISIBLE);
            }
        });

        cv_img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        btn_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLng = new LatLng(Double.valueOf(latitud2), Double.valueOf(longitud2));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            }
        });

        tv_tbn_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                latLng = new LatLng(Double.valueOf(latitud2), Double.valueOf(longitud2));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                latitudKirim = latitud2;
                longitudKirim = longitud2;
                getAddress(Double.valueOf(latitud2), Double.valueOf(longitud2));

            }
        });

    }

    private void changeKodeFirebase(String id_pekerja, String kode_order) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(id_pekerja).child("kode_order");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String kode = (String) dataSnapshot.getValue();
                if (kode.equals("-")) {
                    myRef.setValue(kode_order);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendDataOrder(String id_pelanggan, String id_pekerja, String latitude, String longitude) {

        String gambar = imgToString();
        String alamat = input_alamat.getText().toString();
        String keterangan = input_keterangan.getText().toString();
        String biaya = input_biaya.getText().toString();
        String status = "Terima";
        String kode_order = "KDO-" + id_pekerja + id_pelanggan;

        ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<OrderModel> orderModelCall = apiRequsetPelanggan.addOrderan(id_pekerja, id_pelanggan,
                gambar, keterangan, biaya, alamat, latitude, longitude, status, tanggal, jam, kode_order);

        orderModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String kode = response.body().getValue();
                    if (kode.equals("1")) {
//                        Toast.makeText(OrderActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                        Log.e("RETROFIT ", "KODE : " + response.body().getValue());
                        Log.e("RETROFIT ", "Message : 352 " + response.body().getMessage());
                        changeKodeFirebase(id_pekerja, kode_order);
                    } else {

                        Toast.makeText(OrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.e("RETROFIT ", "KODE : " + response.body().getValue());
                        Log.e("RETROFIT ", "Message : 358" + response.body().getMessage());
                    }
                } else {
                    Log.e("RETROFIT", "Respon : Tidak Sukses");
                }

            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {


                Log.e("RETROFIT", "Respon : " + t);

            }
        });


    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        id = bundle.getString(GET_ID);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date datejam = new Date();
            jam = timeFormat.format(datejam);

            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            Date date = new Date();
            tanggal = dateFormat.format(date);

//            Toast.makeText(FormLaporanActivity.this, tanggal+"-"+jam, Toast.LENGTH_SHORT).show();
            handler.postDelayed(this, 5000);
        }
    };

    private String imgToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            img_foto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }

    private void getAddress(double latitud, double longitud) {
        Geocoder geocoder;
        List<Address> addressList;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addressList = geocoder.getFromLocation(latitud, longitud, 1);
            String address = addressList.get(0).getAddressLine(0);
            address = address.replaceAll("'", " ");
            Log.d("TAG", "getAddress: " + address);
            alamat_latlig = address;
            tv_alamat_map.setText(address);
            input_alamat.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void pekerjaFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja");
        Query queryRef = myRef.orderByChild("status_pekerja").equalTo("Aktif");

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Integer> listIdPekerja = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    PekerjaFirebaseModel pekerjaFirebaseModel = new PekerjaFirebaseModel();
                    listIdPekerja.add(Integer.parseInt(data.child("id_pekerja").getValue().toString()));

                    pekerjaFirebaseModel.setId_pekerja(data.child("id_pekerja").getValue().toString());
                    pekerjaFirebaseModel.setKode_order(data.child("kode_order").getValue().toString());
                    pekerjaFirebaseModel.setLatitude_pekerja(data.child("latitude_pekerja").toString());
                    pekerjaFirebaseModel.setLongitude_pekerja(data.child("longitude_pekerja").toString());
                    pekerjaFirebaseModel.setNama_pekerja(data.child("nama_pekerja").toString());
                    pekerjaFirebaseModel.setStatus_pekerja(data.child("status_pekerja").toString());
                    models.add(pekerjaFirebaseModel);
                }

                if (listIdPekerja.size()>1){
                    random = getRandomElement(listIdPekerja);
                } else if (listIdPekerja.size()==1) {
                    random = listIdPekerja.get(0);
                }
                Log.d("TAG", "onDataChange: " + random);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        queryRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                PekerjaFirebaseModel pekerjaFirebaseModel = dataSnapshot.getValue(PekerjaFirebaseModel.class);
//                models.add(pekerjaFirebaseModel);
//                random = (int) (Math.random() * models.size() + 1);
//                Log.d("TAG", "onChildAdded: "+models.size() +1+" = "+ random );
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    public int getRandomElement(List<Integer> list) {
        int value;
        Random rand = new Random();
        Log.d("TAG", "getRandomElement: "+list.size());
        value = list.get(rand.nextInt(list.size()));
        Log.d("TAG", "getRandomElement: " + value);
        return value;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                map.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Titik Lokasi Bantuan");
                latitudKirim = String.valueOf(latLng.latitude);
                longitudKirim = String.valueOf(latLng.longitude);
                getAddress(latLng.latitude, latLng.longitude);
                map.clear();
                map.addMarker(markerOptions);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        latitud2 = String.valueOf(location.getLatitude());
        longitud2 = String.valueOf(location.getLongitude());

        //move map camera
        if (firstTime) {
            firstTime = false;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(OrderActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

}
