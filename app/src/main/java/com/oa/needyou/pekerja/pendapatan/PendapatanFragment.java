package com.oa.needyou.pekerja.pendapatan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.OrderanPekerjaActivity;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.model.OrderModel;
import com.oa.needyou.pekerja.model.Result;
import com.oa.needyou.pekerja.pendapatan.adapter.RiwayatPekerjaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendapatanFragment extends Fragment {


    private RelativeLayout relativLayout_sibuk;

    private final String GET_ID = "get_id";
    private String id;

    private RecyclerView rv_riwayat;

    private List<Result> results = new ArrayList<>();
    private ArrayList<Result> resultArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pendapatan, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_riwayat = view.findViewById(R.id.rv_riwayat);
        relativLayout_sibuk = view.findViewById(R.id.relativLayout_sibuk);
        relativLayout_sibuk.setVisibility(View.GONE);


        relativLayout_sibuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(GET_ID, id);

                Intent intent = new Intent(getActivity(),DetailSibukActivity.class);
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

            getDataRiwayat(id);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Pekerja").child(id).child("status_pekerja");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String status = (String) dataSnapshot.getValue();

                    assert status != null;
                    if (status.equals("Sibuk")){
                        relativLayout_sibuk.setVisibility(View.VISIBLE);
                    } else {
                        relativLayout_sibuk.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void getDataRiwayat(String id) {

        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderModel> orderModelCall = apiRequestPekerja.getDataOrderIdPekerja(id);
        orderModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.isSuccessful()){

                    assert response.body() != null;
                    results = response.body().getResult();
//                    resultArrayList = (ArrayList<ResultSaldo>) results;
                    Log.e("Retro", "Kode : " + response.body().getKode());
                    Log.e("Retro", "RESULT : " + results);
//                    Log.e("Retro", "RESULT : " + results.get(0).getIdPekerja());

                    RiwayatPekerjaAdapter adapter = new RiwayatPekerjaAdapter(getContext(),results);

                    rv_riwayat.setLayoutManager(new LinearLayoutManager(getActivity()));

                    rv_riwayat.setAdapter(adapter);

                } else {
                    Log.e("Retro", "Gagal");

                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {

                Log.e("Retro", "Respon : "+t);

            }
        });

    }

}
