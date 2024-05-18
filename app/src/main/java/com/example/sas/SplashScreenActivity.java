package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView logoImageView;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progressBar);
        logoImageView = findViewById(R.id.logo_image_view);
        titleTextView = findViewById(R.id.title_text_view);

        progressBar.setScaleY(3f);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.secondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        logoImageView.startAnimation(bounce);
        titleTextView.startAnimation(fadeIn);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    try {
                        Thread.sleep(50); // Speed up the progress bar for better UX
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i);
                }
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
