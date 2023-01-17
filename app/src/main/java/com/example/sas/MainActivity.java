package com.example.sas;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        Button playButton = findViewById(R.id.play_button);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        playButton.startAnimation(animation);





    }
}