package com.oa.needyou.pekerja.akun;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.needyou.needyou.R;
import com.oa.needyou.URL_IP;
import com.oa.needyou.pekerja.api.ApiRequestPekerja;
import com.oa.needyou.pekerja.api.RetroServerPekerja;
import com.oa.needyou.pekerja.model.OrderResponModel;
import com.oa.needyou.pelanggan.api.ApiRequsetPelanggan;
import com.oa.needyou.pelanggan.api.RetroServerPelanggan;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePekerjaActivity extends AppCompatActivity {

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

    private String id, nama, umur, pekerjaan, email, pass, telpon, gender, alamat, path, status;

    private EditText et_nama;
    private EditText et_umur;
    private EditText et_pekerjaan;
    private EditText et_email;
    private EditText et_telpon;
    private EditText et_gender;

    private View dialogView;

    private Button btn_update;
    private Button btn_batal;

    private ImageView foto_profil;
    private ProgressDialog pd;

    Bitmap bitmap = null;
    String path_pekerja = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pekerja);

        foto_profil = findViewById(R.id.foto_profil);
        et_nama = findViewById(R.id.et_nama_update);
        et_umur = findViewById(R.id.et_umur_update);
        et_pekerjaan = findViewById(R.id.et_pekerjaan_update);
        et_email = findViewById(R.id.et_email_update);
        et_telpon = findViewById(R.id.et_telpon_update);
        et_gender = findViewById(R.id.et_gender_update);
        btn_batal = findViewById(R.id.btn_batal);
        btn_update = findViewById(R.id.btn_update);
        pd = new ProgressDialog(this);

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Bundle bundle = new Bundle();
//                bundle.putString(GET_ID, id);
//                bundle.putString(GET_NAMA, nama);
//                bundle.putString(GET_EMAIL, email);
//                bundle.putString(GET_PASS, pass);
//                bundle.putString(GET_TELPON, telpon);
//                bundle.putString(GET_GENDER, gender);
//                bundle.putString(GET_ALAMAT, alamat);
//                bundle.putString(GET_PATH, path_pekerja);
//                bundle.putString(GET_STATUS, status);
//
//                Intent intent = new Intent(UpdatePelangganActivity.this, DashboardPekerjaActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
                finish();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataUpdate();
            }
        });

        foto_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage();

                //CAMERA
//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)

                //GALERY
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

            }
        });

        et_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(UpdatePekerjaActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.dialog_gender, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setTitle("Jenis Kelamin");
                final AlertDialog show = dialog.show();


                Button btn1 = dialogView.findViewById(R.id.btn1);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_gender.setText("Laki-laki");
                        show.dismiss();
                    }
                });
                Button btn2 = dialogView.findViewById(R.id.btn2);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_gender.setText("Perempuan");
                        show.dismiss();
                    }
                });
            }
        });

        getBundle();

    }

    private void sendDataUpdate() {

        pd.setMessage("Proses ... ");
        pd.setCancelable(false);
        pd.show();
        if (bitmap == null) {
            path_pekerja = path;
        } else {
            path_pekerja = BitMapToString();
        }

        String id_pekerja = id;
        String nama_pekerja = et_nama.getText().toString();
        String umur_pekerja = et_umur.getText().toString();
        String pekerjaan_pekerja = et_pekerjaan.getText().toString();
        String email_pekerja = et_email.getText().toString();
        String telpon_pekerja = et_telpon.getText().toString();
        String gender_pekerja = et_gender.getText().toString();


        ApiRequestPekerja apiRequestPekerja = RetroServerPekerja.getClientPekerja().create(ApiRequestPekerja.class);
        Call<OrderResponModel> orderResponModelCall = apiRequestPekerja.updateDataPekerja(id_pekerja,
                nama_pekerja,
                umur_pekerja,
                pekerjaan_pekerja,
                gender_pekerja,
                email_pekerja,
                telpon_pekerja,
                path_pekerja);
        orderResponModelCall.enqueue(new Callback<OrderResponModel>() {
            @Override
            public void onResponse(Call<OrderResponModel> call, Response<OrderResponModel> response) {
                pd.hide();
                if (response.isSuccessful()) {

                    Log.e("Retro", "Kode : " + response.body().getKode());
                    Log.e("Retro", "Message : " + response.body().getPesan());

                    Toast.makeText(UpdatePekerjaActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponModel> call, Throwable t) {
                Log.e("Retro", "Failed : " + t);
                pd.hide();
            }
        });

    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePekerjaActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    foto_profil.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                        foto_profil.setImageURI(selectedImage);
            }
        }

    }

    public String BitMapToString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        if (!bundle.isEmpty()) {

            id = bundle.getString(GET_ID);
            nama = bundle.getString(GET_NAMA);
            umur = bundle.getString(GET_USIA);
            pekerjaan = bundle.getString(GET_PEKERJAAN);
            gender = bundle.getString(GET_GENDER);
            email = bundle.getString(GET_EMAIL);
            pass = bundle.getString(GET_PASS);
            telpon = bundle.getString(GET_TELPON);
            path = bundle.getString(GET_PATH);
            status = bundle.getString(GET_STATUS);

            path_pekerja = path;
            et_nama.setText(nama);
            et_umur.setText(umur);
            et_pekerjaan.setText(pekerjaan);
            et_email.setText(email);
            String foto = URL_IP.ALAMAT_IP + path;
            Log.d("UpdatePekerjaActivity", "getBundle: UPDATE"+foto);

//            Glide.with(this)
//                    .load(foto)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(foto_profil);
            Picasso.with(UpdatePekerjaActivity.this)
                    .load(foto)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(foto_profil);
            if (!gender.isEmpty() || !gender.equals("")) {
                et_gender.setText(gender);
            } else {
                et_gender.setText("-");
            }

            if (!telpon.isEmpty() || !telpon.equals("")) {
                et_telpon.setText(telpon);
            } else {
                et_telpon.setText("-");
            }

        }

    }
}
