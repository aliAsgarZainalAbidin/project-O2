package com.oa.needyou.pekerja.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

public class TrackingService extends Service {

    private static final String CHANNEL_ID = "my_channel";
    private final IBinder mBinder = new LocalBinde();
    private static final long UPDATE_INTERVAL_IN_MIL = 1000;
    private static final long FASTES_UPDATE_INTERVAL_IN_MIL = UPDATE_INTERVAL_IN_MIL/2;
    private static final int NOTI_ID = 1223;
    private boolean mChangingCOnfiguration=false;
    private NotificationManager mNotificationManager;

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private Handler mServiceHandler;
    private Location mLocation;

    public TrackingService(){

    }

    @Override
    public void onCreate() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                onNewLocation(locationResult.getLastLocation());
            }
        };
    }

    private void onNewLocation(Location lastLocation) {
        mLocation = lastLocation;
        EventBus.getDefault().postSticky(new SendLocationToActivity(mLocation));
    }

    public class LocalBinde extends Binder {
        TrackingService getserVice(){return TrackingService.this;}
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    currentLocation = location;
//
//                    myRef.setValue("latitude : "+currentLocation.getLatitude()+" Dan Longitude : "+
//                            currentLocation.getLongitude());
//
//                }
//            }
//        });

        return Service.START_STICKY;

    }
}
