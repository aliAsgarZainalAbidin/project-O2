package com.oa.needyou.pelanggan.aktivitas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.model.OrderModel;
import com.oa.needyou.pekerja.model.Result;
import com.oa.needyou.pelanggan.aktivitas.adapter.AktifitasPelangganAdapter;
import com.oa.needyou.pelanggan.aktivitas.model.AktifitasPelangganModel;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AktifitasFragment extends Fragment {

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

    private RecyclerView rv_aktifitas;
    private List<Result> results = new ArrayList<>();

    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aktifitas, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_aktifitas = view.findViewById(R.id.rv_aktifitas);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
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


            getDataOrder(id);

        }

    }

    private void getDataOrder(String id) {

        ApiRequsetPelanggan apiRequsetPelanggan = RetroServerPelanggan.getClientPelanggan().create(ApiRequsetPelanggan.class);
        Call<OrderModel> orderModelCall = apiRequsetPelanggan.getDataOrderIdPelanggan(id);
        orderModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                progressBar.setVisibility(View.GONE);
                assert response.body() != null;
                results = response.body().getResult();
                AktifitasPelangganAdapter adapter = new AktifitasPelangganAdapter(getContext(),results);

                rv_aktifitas.setLayoutManager(new LinearLayoutManager(getActivity()));

                rv_aktifitas.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
