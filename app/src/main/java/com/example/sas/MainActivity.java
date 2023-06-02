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
        Button playButton = findViewById(R.id.play_button);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        playButton.startAnimation(animation);
        Button exitButton = findViewById(R.id.exit_button);
exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(MainActivity.this, MainMathGame.class);
                startActivity(intent);

            }
        });



    }
}