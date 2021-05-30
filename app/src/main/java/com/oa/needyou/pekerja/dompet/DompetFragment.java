package com.oa.needyou.pekerja.dompet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.beranda.model.ResultSaldo;
import com.oa.needyou.pekerja.beranda.model.SaldoResponModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DompetFragment extends Fragment {

    private ImageView img_add;
    private RelativeLayout rl2;
    private String tampil;

    private final String GET_ID = "get_id";
    private String id;

    private TextView tv_saldo;

    private List<ResultSaldo> results = new ArrayList<>();

    private CardView cv1,cv2,cv3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dompet, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_saldo = view.findViewById(R.id.tv_saldo);

        tampil = "hilang";
        cv1 = view.findViewById(R.id.cv1);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("BANK", "BNI");
                Intent intent = new Intent(getActivity(),BankDompetActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        cv2 = view.findViewById(R.id.cv2);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("BANK", "BRI");
                Intent intent = new Intent(getActivity(),BankDompetActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cv3 = view.findViewById(R.id.cv3);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("BANK", "MANDIRI");
                Intent intent = new Intent(getActivity(),BankDompetActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        img_add = view.findViewById(R.id.img_add);
        rl2 = view.findViewById(R.id.rl2);
        rl2.setVisibility(View.GONE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tampil.equals("hilang")){
                    rl2.setVisibility(View.VISIBLE);
                    tampil = "ada";
                } else {
                    rl2.setVisibility(View.GONE);
                    tampil = "hilang";
                }

            }
        });

        getBundle();

    }

    private void getBundle() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (!bundle.isEmpty()) {
            id = bundle.getString(GET_ID);

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


    }

    private void initSaldo(List<ResultSaldo> results) {
        for (int i = 0; i < results.size(); i++) {

            String saldo = "0";
            saldo = results.get(i).getJumlahSaldo();

            tv_saldo.setText("Rp. "+saldo);

        }
    }
}
