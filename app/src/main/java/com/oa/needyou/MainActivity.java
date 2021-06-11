package com.oa.needyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.needyou.needyou.R;
import com.oa.needyou.model.UserPekerjaLogin;
import com.oa.needyou.model.UserPelangganLogin;
import com.oa.needyou.model.UserPreference;
import com.oa.needyou.pekerja.DashboardPekerjaActivity;
import com.oa.needyou.pekerja.LoginPekerjaActivity;
import com.oa.needyou.pekerja.MasukPekerjaActivity;
import com.oa.needyou.pelanggan.DashboardPelangganActivity;
import com.oa.needyou.pelanggan.LoginPelangganActivity;
import com.oa.needyou.pelanggan.MasukPelangganActivity;

public class MainActivity extends AppCompatActivity {

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
    private final String GET_ALAMAT = "get_alamat";


    private Button btn1, btn2;

    private UserPreference userPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPreference = new UserPreference(this);
        if (userPreference.isLogin()){
            UserPekerjaLogin userPekerjaLogin = userPreference.getUser();
            Bundle bundle = new Bundle();
            bundle.putString(GET_ID, userPekerjaLogin.getId());
            bundle.putString(GET_NAMA, userPekerjaLogin.getNama());
            bundle.putString(GET_USIA, userPekerjaLogin.getUsia());
            bundle.putString(GET_PEKERJAAN, userPekerjaLogin.getPekerjaan());
            bundle.putString(GET_GENDER, userPekerjaLogin.getGender());
            bundle.putString(GET_EMAIL, userPekerjaLogin.getEmail());
            bundle.putString(GET_PASS, userPekerjaLogin.getPassword());
            bundle.putString(GET_TELPON, userPekerjaLogin.getTelpon());
            bundle.putString(GET_PATH, userPekerjaLogin.getPath());
            bundle.putString(GET_STATUS, userPekerjaLogin.getStatus());

            Intent intent = new Intent(this, DashboardPekerjaActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
        } else if (userPreference.isPelangganLogin()){
            UserPelangganLogin userPelangganLogin = userPreference.getUserPelanggan();
            Bundle bundle = new Bundle();
            bundle.putString(GET_ID, userPelangganLogin.getId());
            bundle.putString(GET_NAMA, userPelangganLogin.getNama());
            bundle.putString(GET_EMAIL, userPelangganLogin.getEmail());
            bundle.putString(GET_PASS, userPelangganLogin.getPass());
            bundle.putString(GET_TELPON, userPelangganLogin.getTelpon());
            bundle.putString(GET_GENDER, userPelangganLogin.getGender());
            bundle.putString(GET_ALAMAT, userPelangganLogin.getAlamat());
            bundle.putString(GET_PATH, userPelangganLogin.getPath());
            bundle.putString(GET_STATUS, userPelangganLogin.getStatus());

            Intent intent = new Intent(this, DashboardPelangganActivity.class);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity(intent);
        }

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        FragmentManager fm = this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MasukPekerjaActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MasukPelangganActivity.class);
                startActivity(intent);
            }
        });
    }
}
