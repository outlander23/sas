package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progressBar);

        progressBar.setScaleY(3f);


        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.secondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    try {
                        Thread.sleep(100);
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
