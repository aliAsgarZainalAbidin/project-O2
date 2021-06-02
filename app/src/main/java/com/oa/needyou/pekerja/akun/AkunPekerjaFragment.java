package com.oa.needyou.pekerja.akun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.oa.needyou.MainActivity;
import com.needyou.needyou.R;
import com.oa.needyou.URL_IP;
import com.oa.needyou.pelanggan.akun.UpdatePelangganActivity;

import static org.greenrobot.eventbus.EventBus.TAG;

public class AkunPekerjaFragment extends Fragment {


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

    private String id,nama,email,pass,telpon,gender,alamat,path,status;

    private Button btn_signout;
    private Button btn_update;

    private EditText et_nama;
    private EditText et_usia;
    private EditText et_pekerjaan;
    private EditText et_gender;
    private EditText et_email;
    private EditText et_telpon;

    private ImageView foto_profil;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_akun_pekerja, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_signout = view.findViewById(R.id.btn_signout);
        btn_update = view.findViewById(R.id.btn_update_pekerja);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = et_nama.getText().toString();
                String umur = et_usia.getText().toString();
                String pekerjaan = et_pekerjaan.getText().toString();
                String gender = et_gender.getText().toString();
                String email = et_email.getText().toString();
                String telpon = et_telpon.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString(GET_ID, id);
                bundle.putString(GET_NAMA, nama);
                bundle.putString(GET_USIA, umur);
                bundle.putString(GET_PEKERJAAN, pekerjaan);
                bundle.putString(GET_GENDER, gender);
                bundle.putString(GET_EMAIL, email);
                bundle.putString(GET_TELPON, telpon);
                bundle.putString(GET_PATH, path);
                bundle.putString(GET_STATUS, status);

                Intent intent = new Intent(getActivity(), UpdatePekerjaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        foto_profil = view.findViewById(R.id.foto_profil);
        et_nama = view.findViewById(R.id.et_nama_update);
        et_usia = view.findViewById(R.id.et_usia);
        et_pekerjaan = view.findViewById(R.id.et_pekerjaan);
        et_gender = view.findViewById(R.id.et_gender_update);
        et_email = view.findViewById(R.id.et_email_update);
        et_telpon = view.findViewById(R.id.et_telpon_update);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (!bundle.isEmpty()) {
            String foto = URL_IP.ALAMAT_IP+bundle.getString(GET_PATH);

            Log.e("GAMBAR", "VALUE : "+foto);

            Glide.with(getActivity()).load(foto).error(R.drawable.img_circle).placeholder(R.drawable.img_circle).into(foto_profil);

//            Picasso.with(getActivity()).load(foto).error(R.drawable.img_circle).placeholder(R.drawable.img_circle).into(foto_profil);
            id = bundle.getString(GET_ID);
            et_nama.setText(bundle.getString(GET_NAMA));
            et_usia.setText(bundle.getString(GET_USIA));
            Log.d(TAG, "onViewCreated: HALO "+bundle.getString(GET_USIA));
            et_pekerjaan.setText(bundle.getString(GET_PEKERJAAN));
            et_gender.setText(bundle.getString(GET_GENDER));
            et_email.setText(bundle.getString(GET_EMAIL));
            et_telpon.setText(bundle.getString(GET_TELPON));
        }

    }
}
