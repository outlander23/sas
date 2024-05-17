package com.example.sas;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameActivity extends AppCompatActivity {

    private int level;
    private int realNumber;
    private int score;
    private Dialog congratsDialog;
    private Dialog wrongDialog;
    private RecyclerView entityRecyclerView;
    private TextView scoreTextView;
    private TextView levelTextView;
    private Button button1, button2, button3;
    private Animation buttonAnimation, scoreTextAnimation, levelTransitionAnimation;
    private MediaPlayer backgroundMusic;
    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;
    private ImageButton soundToggleButton;
    private boolean isSoundOn = true;

    private String[] animalNames = {
            "cat", "dog", "elephant", "giraffe", "lion", "monkey", "panda", "pig", "rabbit", "sheep",
            "american", "fox", "otter", "wolf"
    };

    private int[] animalImages = {
            R.drawable.cat, R.drawable.dog, R.drawable.elephant, R.drawable.giraffe, R.drawable.lion,
            R.drawable.monkey, R.drawable.panda, R.drawable.pig, R.drawable.rabbit, R.drawable.sheep,
            R.drawable.wolf, R.drawable.otter, R.drawable.fox, R.drawable.american
    };

    private int[] foodImages = {
            R.drawable.berry, R.drawable.orange, R.drawable.apple
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        getSupportActionBar().hide();

        level = 1;
        score = 0;

        entityRecyclerView = findViewById(R.id.entity_recycler_view);
        scoreTextView = findViewById(R.id.score_text_view);
        levelTextView = findViewById(R.id.level_text_view);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        soundToggleButton = findViewById(R.id.sound_toggle_button);

        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        scoreTextAnimation = AnimationUtils.loadAnimation(this, R.anim.score_text_animation);
        levelTransitionAnimation = AnimationUtils.loadAnimation(this, R.anim.level_transition);

        setupDialogs();
        setupSounds();
        startBackgroundMusic();
        generateRandomEntities();

        startButtonAnimation();

        soundToggleButton.setOnClickListener(v -> toggleSound());
    }

    private void setupDialogs() {
        congratsDialog = new Dialog(this);
        congratsDialog.setContentView(R.layout.congrats_dialog);
        congratsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        wrongDialog = new Dialog(this);
        wrongDialog.setContentView(R.layout.wrong_ans);
        wrongDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setupSounds() {
        correctSound = MediaPlayer.create(this, R.raw.correct_sound);
        wrongSound = MediaPlayer.create(this, R.raw.wrong_sound);
    }

    private void startBackgroundMusic() {
        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    private void generateRandomEntities() {
        levelTextView.setText("Level: " + level);

        Random random = new Random();
        int targetAnimalIndex = random.nextInt(animalNames.length);
        String targetAnimalName = animalNames[targetAnimalIndex];
        int targetAnimalImage = animalImages[targetAnimalIndex];

        int numberOfEntities = level * 5 + random.nextInt(6);  // Increase entities with each level
        int countOfTargetAnimal = 0;

        List<Integer> entityImages = new ArrayList<>();
        for (int i = 0; i < numberOfEntities; i++) {
            if (random.nextBoolean()) {
                entityImages.add(targetAnimalImage);
                countOfTargetAnimal++;
            } else {
                int randomFoodImage = foodImages[random.nextInt(foodImages.length)];
                entityImages.add(randomFoodImage);
            }
        }

        realNumber = countOfTargetAnimal;

        EntityAdapter adapter = new EntityAdapter(this, entityImages);
        entityRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        entityRecyclerView.setAdapter(adapter);

        setButtonValues(countOfTargetAnimal);
    }

    private void setButtonValues(int correctValue) {
        Random random = new Random();
        int[] buttonValues = new int[3];
        buttonValues[random.nextInt(3)] = correctValue;

        for (int i = 0; i < 3; i++) {
            if (buttonValues[i] == 0) {
                int randomValue;
                do {
                    randomValue = random.nextInt(correctValue * 2) + 1;
                } while (randomValue == correctValue);

                buttonValues[i] = randomValue;
            }
        }

        button1.setText(String.valueOf(buttonValues[0]));
        button2.setText(String.valueOf(buttonValues[1]));
        button3.setText(String.valueOf(buttonValues[2]));

        button1.setOnClickListener(this::onButtonClick);
        button2.setOnClickListener(this::onButtonClick);
        button3.setOnClickListener(this::onButtonClick);
    }

    private void onButtonClick(View view) {
        Button clickedButton = (Button) view;
        int guessedNumber = Integer.parseInt(clickedButton.getText().toString());

        if (guessedNumber == realNumber) {
            score += 10;
            scoreTextView.setText("Score: " + score);
            scoreTextView.startAnimation(scoreTextAnimation);
            correctSound.start();
            congratsDialog.show();
        } else {
            wrongSound.start();
            wrongDialog.show();
        }

        level++;
        entityRecyclerView.startAnimation(levelTransitionAnimation);
        generateRandomEntities();
    }

    private void startButtonAnimation() {
        button1.startAnimation(buttonAnimation);
        button2.startAnimation(buttonAnimation);
        button3.startAnimation(buttonAnimation);
    }

    private void toggleSound() {
        if (isSoundOn) {
            backgroundMusic.pause();
            correctSound.setVolume(0, 0);
            wrongSound.setVolume(0, 0);
            soundToggleButton.setImageResource(R.drawable.ic_volume_off);
        } else {
            backgroundMusic.start();
            correctSound.setVolume(1, 1);
            wrongSound.setVolume(1, 1);
            soundToggleButton.setImageResource(R.drawable.ic_volume_up);
        }
        isSoundOn = !isSoundOn;
    }

    @Override
    protected void onDestroy() {
        if (backgroundMusic != null) {
            backgroundMusic.release();
            backgroundMusic = null;
        }
        if (correctSound != null) {
            correctSound.release();
            correctSound = null;
        }
        if (wrongSound != null) {
            wrongSound.release();
            wrongSound = null;
        }
        super.onDestroy();
    }
}
