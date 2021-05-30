package com.oa.needyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.needyou.needyou.R;

public class SplashScreanActivity extends AppCompatActivity {

    private ImageView img_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screan);
        img_logo = findViewById(R.id.img_logo);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition_splash);
        img_logo.startAnimation(animation);
        final Intent intent = new Intent(this,MainActivity.class);
        Thread time = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        time.start();

    }
}
