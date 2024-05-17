package com.example.sas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        Button playMathButton = findViewById(R.id.play_math_button);
        Button playSpeedButton = findViewById(R.id.play_speed_button);
        Button exitButton = findViewById(R.id.exit_button);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        playMathButton.startAnimation(animation);

        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });



        playSpeedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
