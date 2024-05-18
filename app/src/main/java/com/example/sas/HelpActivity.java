package com.example.sas;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private String[] animalNames = {
            "Cat", "Dog", "Elephant", "Giraffe", "Lion", "Monkey", "Panda", "Pig", "Rabbit", "Sheep",
            "Fox", "Otter", "Wolf", "American"
    };

    private int[] animalImages = {
            R.drawable.cat, R.drawable.dog, R.drawable.elephant, R.drawable.giraffe, R.drawable.lion,
            R.drawable.monkey, R.drawable.panda, R.drawable.pig, R.drawable.rabbit, R.drawable.sheep,
            R.drawable.fox, R.drawable.otter, R.drawable.wolf, R.drawable.american
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().hide();

        LinearLayout animalListLayout = findViewById(R.id.animal_list_layout);

        for (int i = 0; i < animalNames.length; i++) {
            LinearLayout animalLayout = new LinearLayout(this);
            animalLayout.setOrientation(LinearLayout.HORIZONTAL);
            animalLayout.setPadding(0, 0, 0, 16);

            ImageView animalImage = new ImageView(this);
            animalImage.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            animalImage.setImageResource(animalImages[i]);
            animalLayout.addView(animalImage);

            TextView animalName = new TextView(this);
            animalName.setText(animalNames[i]);
            animalName.setTextColor(getResources().getColor(android.R.color.white));
            animalName.setTextSize(18);
            animalName.setPadding(16, 0, 0, 0);
            animalLayout.addView(animalName);

            animalListLayout.addView(animalLayout);
        }
    }
}
