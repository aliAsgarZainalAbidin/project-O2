package com.oa.needyou.pelanggan.beranda;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.needyou.needyou.R;
import com.oa.needyou.URL_IP;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.oa.needyou.pelanggan.model.PelangganModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beranda, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btn_camera = view.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (telpon.equals("") || telpon.isEmpty()){
                    Toast.makeText(getActivity(), "Lengkapi Profile terlebih dahulu.", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(GET_ID, id);
                    Intent intent = new Intent(getContext(),OrderActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        getBundle();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                loadData();
            }
        }, 1000);

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

                    }
                }
            }

            @Override
            public void onFailure(Call<List<PelangganModel>> call, Throwable t) {

            }
        });
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

        }
    }
}
