package com.oa.needyou.pekerja.dompet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.needyou.needyou.R;

public class BankDompetActivity extends AppCompatActivity {

    private ImageView img_back,img_bank;

    private String bank;

    private TextView tv1,tv_no_rek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_dompet);


        tv1 = findViewById(R.id.tv1);
        img_bank = findViewById(R.id.img_bank);
        tv_no_rek = findViewById(R.id.tv_no_rek);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        bank = bundle.getString("BANK");

        if (bank.equals("BNI")){
            tv1.setText("Transfer via Bank BNI");
            img_bank.setImageResource(R.drawable.img_bni);
            tv_no_rek.setText("0467 006 783");
        } else if (bank.equals("BRI")){
            tv1.setText("Transfer via Bank BRI");
            img_bank.setImageResource(R.drawable.img_bri);
            tv_no_rek.setText("9678 225 022");
        } else if (bank.equals("MANDIRI")){
            tv1.setText("Transfer via Bank MANDIRI");
            img_bank.setImageResource(R.drawable.img_mandiri);
            tv_no_rek.setText("8863 019 001");
        }

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
