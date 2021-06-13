package com.oa.needyou.pelanggan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.needyou.needyou.R;
import com.oa.needyou.pelanggan.aktivitas.AktifitasFragment;
import com.oa.needyou.pelanggan.akun.AkunFragment;
import com.oa.needyou.pelanggan.beranda.BerandaFragment;
import com.oa.needyou.pelanggan.pembayaran.PembayaranFragment;
import com.oa.needyou.pelanggan.pesan.PesanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardPelangganActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private final String GET_ID = "get_id";
    private final String GET_NAMA = "get_nama";
    private final String GET_GENDER = "get_gender";
    private final String GET_EMAIL = "get_email";
    private final String GET_PASS = "get_pass";
    private final String GET_TELPON = "get_telpon";
    private final String GET_PATH = "get_path";
    private final String GET_STATUS = "get_status";
    private final String GET_ALAMAT = "get_alamat";

    private String id,nama,email,pass,telpon,gender,alamat,path,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pelanggan);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getBundle();
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        openFragment(new BerandaFragment());
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        id = bundle.getString(GET_ID);
        nama = bundle.getString(GET_NAMA);
        email = bundle.getString(GET_EMAIL);
        pass = bundle.getString(GET_PASS);
        telpon = bundle.getString(GET_TELPON);
        gender = bundle.getString(GET_GENDER);
        alamat = bundle.getString(GET_ALAMAT);
        path = bundle.getString(GET_PATH);
        status = bundle.getString(GET_STATUS);
    }
    public void openFragment(Fragment fragment){

        Bundle bundle = new Bundle();
        bundle.putString(GET_ID, id);
        bundle.putString(GET_NAMA, nama);
        bundle.putString(GET_EMAIL, email);
        bundle.putString(GET_PASS, pass);
        bundle.putString(GET_TELPON, telpon);
        bundle.putString(GET_GENDER, gender);
        bundle.putString(GET_ALAMAT, alamat);
        bundle.putString(GET_PATH, path);
        bundle.putString(GET_STATUS, status);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment,fragment);
        fragment.setArguments(bundle);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.btn_beranda:
                    openFragment(new BerandaFragment());
                    return true;

                case R.id.btn_akun:
                    openFragment(new AkunFragment());
                    return true;

                case R.id.btn_aktifitas:
                    openFragment(new AktifitasFragment());
                    return true;

                case R.id.btn_pembayaran:
                    openFragment(new PembayaranFragment());
                    return true;

//                case R.id.btn_pesan:
//                    openFragment(new PesanFragment());
//                    return true;

            }

            return false;
        }
    };

}
