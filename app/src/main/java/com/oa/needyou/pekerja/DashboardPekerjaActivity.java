package com.oa.needyou.pekerja;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.akun.AkunPekerjaFragment;
import com.oa.needyou.pekerja.beranda.BerandaPekerjaFragment;
import com.oa.needyou.pekerja.dompet.DompetFragment;
import com.oa.needyou.pekerja.pendapatan.PendapatanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardPekerjaActivity extends AppCompatActivity {


    private final String GET_ID = "get_id";
    private final String GET_NAMA = "get_nama";
    private final String GET_USIA = "get_usia";
    private final String GET_PEKERJAAN = "get_pekerjaan";
    private final String GET_GENDER = "get_gender";
    private final String GET_EMAIL = "get_email";
    private final String GET_PASS = "get_pass";
    private final String GET_TELPON = "get_telpon";
    private final String GET_PATH = "get_path";
    private final String GET_STATUS = "get_status";

    private String id;
    private String nama;
    private String usia;
    private String pekerjaan;
    private String gender;
    private String email;
    private String pass;
    private String telpon;
    private String path;
    private String status;



    private BottomNavigationView bottom_navigation_pekerja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pekerja);

        bottom_navigation_pekerja = findViewById(R.id.bottom_navigation_pekerja);
        getBundle();
        bottom_navigation_pekerja.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        openFragmentPekerja(new BerandaPekerjaFragment());

    }

    private void dataPekerjaFirebase(String id) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pekerja").child(id).child("status_pekerja");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String status = (String) dataSnapshot.getValue();

                assert status != null;
                if (status.equals("Menunggu")){
//                    Toast.makeText(DashboardPekerjaActivity.this, "Ada Orderan : " + status, Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString(GET_ID, id);
                    Intent intent = new Intent(DashboardPekerjaActivity.this, OrderanPekerjaActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        id = bundle.getString(GET_ID);
        nama = bundle.getString(GET_NAMA);
        usia = bundle.getString(GET_USIA);
        pekerjaan = bundle.getString(GET_PEKERJAAN);
        gender = bundle.getString(GET_GENDER);
        email = bundle.getString(GET_EMAIL);
        pass = bundle.getString(GET_PASS);
        telpon = bundle.getString(GET_TELPON);
        path = bundle.getString(GET_PATH);
        status = bundle.getString(GET_STATUS);

        dataPekerjaFirebase(id);

//        Toast.makeText(this, id+nama+email, Toast.LENGTH_SHORT).show();

//        sendToFragment(id,nama,usia,pekerjaan,gender,email,pass,telpon,path,status);
    }

    public void openFragmentPekerja(Fragment fragment){

        Bundle bundle = new Bundle();

        bundle.putString(GET_ID, id);
        bundle.putString(GET_NAMA, nama);
        bundle.putString(GET_USIA, usia);
        bundle.putString(GET_PEKERJAAN, pekerjaan);
        bundle.putString(GET_GENDER, gender);
        bundle.putString(GET_EMAIL, email);
        bundle.putString(GET_PASS, pass);
        bundle.putString(GET_TELPON, telpon);
        bundle.putString(GET_PATH, path);
        bundle.putString(GET_STATUS, status);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(R.id.nav_host_fragment_pekerja,fragment);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.btn_beranda_pekerja:
                    openFragmentPekerja(new BerandaPekerjaFragment());
                    return true;

                case R.id.btn_pendapatan:
                    openFragmentPekerja(new PendapatanFragment());
                    return true;

                case R.id.btn_dompet:
                    openFragmentPekerja(new DompetFragment());
                    return true;

                case R.id.btn_akun_pekerja:
                    openFragmentPekerja(new AkunPekerjaFragment());
                    return true;

            }

            return false;
        }
    };
}
