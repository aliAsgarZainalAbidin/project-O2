package com.oa.needyou.pelanggan.aktivitas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.needyou.needyou.R;
import com.oa.needyou.pekerja.model.Result;
import com.oa.needyou.pelanggan.aktivitas.AktifitasPelangganActivity;

import java.util.List;

public class AktifitasPelangganAdapter extends RecyclerView.Adapter<AktifitasPelangganAdapter.MyholderView> {

    private Context mContext;
    List<Result> arrayList;

    public AktifitasPelangganAdapter(Context mContext, List<Result> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AktifitasPelangganAdapter.MyholderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_aktifitas, parent, false);
        MyholderView viewHolder = new MyholderView(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AktifitasPelangganAdapter.MyholderView holder, int position) {

        holder.tv_jam.setText(arrayList.get(position).getmJamOrder());
        holder.tv_tgl.setText(arrayList.get(position).getmTanggalOrder());
        holder.tv_title.setText(arrayList.get(position).getmKeteranganOrder());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AktifitasPelangganActivity.class);
                intent.putExtra(AktifitasPelangganActivity.EXTRA_DATA,arrayList.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyholderView extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_tgl;
        private TextView tv_jam;

        public MyholderView(@NonNull View itemView) {
            super(itemView);

            tv_jam = itemView.findViewById(R.id.tv_jam);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_tgl = itemView.findViewById(R.id.tv_tgl);
        }
    }
}
