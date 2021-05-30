package com.oa.needyou.pekerja.beranda;

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
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.beranda.model.PekerjaFirebaseModel;
import com.oa.needyou.pekerja.beranda.model.ResultSaldo;
import com.oa.needyou.pekerja.beranda.model.SaldoResponModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaPekerjaFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private DatabaseReference database;

    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private LocationManager mLocationManager;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2000;
    private long FASTES_INTERVAL = 5000;
    private LatLng latLng;
    private boolean isPermission;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private Handler handler = new Handler();
    private GoogleMap googleMap1;
    private ImageView btn_my_location;

    private boolean firstTime = true;

    private ProgressBar progressBar;

    private double latitude;
    private double longitude;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private final String GET_ID = "get_id";
    private final String GET_NAMA = "get_nama";
    private final String GET_STATUS = "get_status";

    private String id;
    private String nama;
    private String status;

    private TextView tv_ket;
    private TextView tv_saldo;
    private TextView tv_power;

    private ImageView img_power;

    private List<ResultSaldo> results = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beranda_pekerja, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance().getReference();

        tv_power = view.findViewById(R.id.tv_power);
        tv_ket = view.findViewById(R.id.tv_ket);
        tv_ket.setVisibility(View.GONE);
        tv_saldo = view.findViewById(R.id.tv_saldo);
        img_power = view.findViewById(R.id.img_power);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        img_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Pekerja").child(id).child("status_pekerja");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String status = (String) dataSnapshot.getValue();
                        if (status.equals("Aktif")){
                            myRef.setValue("Non-Aktif");
                            tv_power.setText("Sedang Off");
                            img_power.setImageResource(R.drawable.poweroff);
                            img_power.setClickable(true);

                        } else if (status.equals("Non-Aktif")){
                            img_power.setImageResource(R.drawable.poweron);
                            tv_power.setText("Sedang On");
                            img_power.setClickable(true);
                            myRef.setValue("Aktif");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        if (requestSinglePermission()) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_pekerja);
            assert supportMapFragment != null;
            supportMapFragment.getMapAsync(BerandaPekerjaFragment.this);

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            checkLocation();


        }

        btn_my_location = view.findViewById(R.id.btn_my_location);
        btn_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            }
        });

        getBunde();




    }

    private void getBunde() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (!bundle.isEmpty()) {
            id = bundle.getString(GET_ID);
            nama = bundle.getString(GET_NAMA);
            status = bundle.getString(GET_STATUS);

            readSaldo(id);

            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database1.getReference("Pekerja");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(id)){

                        submitFirebasePekerja(new PekerjaFirebaseModel(id,"-",nama,"-","-",status));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void readSaldo(String id) {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<SaldoResponModel> resultSaldoCall = apiRequestPekerja.getDataSaldo(id);
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

            String saldo = "0";
            saldo = results.get(i).getJumlahSaldo();

            tv_saldo.setText("Rp. "+saldo);

            setStatus(saldo);

        }

    }

    private void setStatus(String saldo) {

        if (Integer.parseInt(saldo) < 2000){
            img_power.setClickable(false);
            tv_power.setText("Sedang Off");
            img_power.setImageResource(R.drawable.poweroff);
            tv_ket.setVisibility(View.VISIBLE);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Pekerja").child(id).child("status_pekerja");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status = (String) dataSnapshot.getValue();
                    if (status.equals("Aktif")){
                        myRef.setValue("Non-Aktif");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            img_power.setClickable(true);
            tv_ket.setVisibility(View.GONE);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Pekerja").child(id).child("status_pekerja");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status = (String) dataSnapshot.getValue();
                    if (status.equals("Aktif")){
                        img_power.setImageResource(R.drawable.poweron);
                        tv_power.setText("Sedang On");
                    } else if (status.equals("Non-Aktif")){
                        img_power.setImageResource(R.drawable.poweroff);
                        tv_power.setText("Sedang Off");
                    }else if (status.equals("Sibuk")){
                        img_power.setImageResource(R.drawable.poweroff);
                        tv_power.setText("Sedang Sibuk");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(getActivity())
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

    private boolean checkLocation() {

        if (!isLocationEnable()) {
            showAlert();
        }
        return isLocationEnable();

    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
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
        locationManager = (LocationManager) Objects.requireNonNull(getContext()).getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap1 = googleMap;
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        if (latLng != null) {
            googleMap1.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).icon(bitmapDescriptor(getActivity(), R.drawable.icon_worker)));
            if (firstTime) {
                firstTime = false;
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            }
        }

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
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdate();
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (currentLocation == null){
            startLocationUpdate();
        }
//        else {
//            Toast.makeText(getActivity(), "Location Not Detected", Toast.LENGTH_SHORT).show();
//        }
    }

    private void startLocationUpdate() {

        progressBar.setVisibility(View.GONE);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTES_INTERVAL);

        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
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

    @Override
    public void onLocationChanged(Location location) {


        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());
        getBunde();

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database1.getReference("Pekerja");
        myRef.child(id).child("latitude_pekerja").setValue(latitude);
        myRef.child(id).child("longitude_pekerja").setValue(longitude);

        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_pekerja);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(BerandaPekerjaFragment.this);

    }

    private void submitFirebasePekerja(PekerjaFirebaseModel pekerjaFirebaseModel) {

        String id_pekerja = pekerjaFirebaseModel.getId_pekerja();
        database.child("Pekerja").child(id_pekerja).setValue(pekerjaFirebaseModel);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mGoogleApiClient != null){
            mGoogleApiClient.connect();

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }

    }
}
