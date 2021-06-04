package com.oa.needyou.pelanggan.akun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oa.needyou.MainActivity;
import com.needyou.needyou.R;
import com.oa.needyou.URL_IP;
import com.oa.needyou.model.UserPreference;
import com.oa.needyou.pekerja.OrderanPekerjaActivity;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.PelangganModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AkunFragment extends Fragment {

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

    private Button btn_signout;
    private UserPreference sharedPreferences;

    private CircleImageView foto_profil;
    private EditText et_nama;
    private EditText et_email;
    private EditText et_telpon;
    private EditText et_alamat;

    private Button btn_update;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_akun, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_update = view.findViewById(R.id.btn_update);
        foto_profil = view.findViewById(R.id.foto_profil);
        et_nama = view.findViewById(R.id.et_nama_update);
        et_email = view.findViewById(R.id.et_email_update);
        et_telpon = view.findViewById(R.id.et_telpon_update);
        et_alamat = view.findViewById(R.id.et_alamat_update);
        btn_signout = view.findViewById(R.id.btn_signout);

        sharedPreferences = new UserPreference(getContext());
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.logoutPelanggan();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            public void run() {
                loadData();
            }
        }, 1000);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                Intent intent = new Intent(getActivity(), UpdatePelangganActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        getBundle();

    }

    private void getBundle() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (!bundle.isEmpty()) {

            id = bundle.getString(GET_ID);
            nama = bundle.getString(GET_NAMA);
            email = bundle.getString(GET_EMAIL);
            pass = bundle.getString(GET_PASS);
            telpon = bundle.getString(GET_TELPON);
            gender = bundle.getString(GET_GENDER);
            alamat = bundle.getString(GET_ALAMAT);
            path = bundle.getString(GET_PATH);
            status = bundle.getString(GET_STATUS);

            loadData();

        }
    }

    private void loadData() {

        ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<List<PelangganModel>> listCall = apiRequsetPelanggan.readIdPelanggan(id);
        listCall.enqueue(new Callback<List<PelangganModel>>() {
            @Override
            public void onResponse(Call<List<PelangganModel>> call, Response<List<PelangganModel>> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().size(); i++) {

                        nama = response.body().get(i).getNama_pelanggan();
                        email = response.body().get(i).getEmail_pelanggan();
                        pass = response.body().get(i).getPassword_pelanggan();
                        telpon = response.body().get(i).getTelpon_pelanggan();
                        gender = response.body().get(i).getGender_pelanggan();
                        alamat = response.body().get(i).getAlamat_pelanggan();
                        path = response.body().get(i).getPath_pelanggan();
                        status = response.body().get(i).getStatus_pelanggan();

                        et_nama.setText(nama);
                        et_email.setText(email);

                        String foto = URL_IP.ALAMAT_IP+path;

                        Picasso.with(getActivity()).load(foto).error(R.drawable.img_circle).placeholder(R.drawable.img_circle).into(foto_profil);


                        if (!telpon.isEmpty()||!telpon.equals("")){
                            et_telpon.setText(telpon);
                        } else {
                            et_telpon.setText("-");
                        }

                        if (!alamat.isEmpty()||!alamat.equals("")){
                            et_alamat.setText(alamat);
                        } else {
                            et_alamat.setText("-");
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<List<PelangganModel>> call, Throwable t) {

            }
        });


    }
}
