package com.example.sas;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainGameActivity extends AppCompatActivity {

    private int realNumber;
    private int score;
    private Dialog congratsDialog;
    private Dialog wrongDialog;
    private GridLayout catContainer;
    private TextView scoreTextView;
    private Button button1;
    private Button button2;
    private Button button3;

    private Animation buttonAnimation;
    private Animation scoreBoxAnimation;
    private Animation scoreTextAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_game);
        getSupportActionBar().hide();

        catContainer = findViewById(R.id.cat_container);
        scoreTextView = findViewById(R.id.score_text_view);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);

        // Load button animation from XML
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        // Set button animation listener
        buttonAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation start event
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation end event
                // You can add any additional logic here after the animation ends
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat event
            }
        });

        // Load score box animation from XML
        scoreBoxAnimation = AnimationUtils.loadAnimation(this, R.anim.score_box_animation);

        // Load score text animation from XML
        scoreTextAnimation = AnimationUtils.loadAnimation(this, R.anim.score_text_animation);

        generateRandomNumberOfCats();

        // Initialize the congratulations dialog
        congratsDialog = new Dialog(this);
        congratsDialog.setContentView(R.layout.congrats_dialog);
        congratsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        wrongDialog = new Dialog(this);
        wrongDialog.setContentView(R.layout.wrong_ans);
        wrongDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply animation to buttons
        button1.startAnimation(buttonAnimation);
        button2.startAnimation(buttonAnimation);
        button3.startAnimation(buttonAnimation);
    }

    private void generateRandomNumberOfCats() {
        catContainer.removeAllViews(); // Clear any existing cat images
        Random random = new Random();
        int numberOfCats = random.nextInt(10) + 1; // Generate a random number between 1 and 10

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int columnCount = screenWidth / 100; // Calculate the number of columns based on image width
        int rowCount = (int) Math.ceil((double) numberOfCats / columnCount); // Calculate the number of rows needed

        catContainer.setColumnCount(columnCount);
        catContainer.setRowCount(rowCount);

        for (int i = 0; i < numberOfCats; i++) {
            ImageView catImageView = new ImageView(this);
            catImageView.setImageResource(R.drawable.cat);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = screenWidth / columnCount; // Set width of the cat image based on column count
            params.height = 100; // Set height of the cat image
            params.setMargins(8, 8, 8, 8); // Set margins between cat images
            catImageView.setLayoutParams(params);

            // Apply animation to the cat image
            Animation catAnimation = AnimationUtils.loadAnimation(this, R.anim.cat_animation);
            catImageView.startAnimation(catAnimation);

            catContainer.addView(catImageView);
        }

        realNumber = numberOfCats; // Set the real number to the number of cats

        updateButtonNames(realNumber);
        updateScore();
        startButtonAnimation();
    }

    private void updateButtonNames(int realNumber) {
        int[] buttonValues = new int[3];
        buttonValues[0] = realNumber;
        buttonValues[1] = realNumber;
        buttonValues[2] = realNumber;

        int cnt = 0;
        while (cnt < 2) {
            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
            if (!containsValue(buttonValues, randomNumber) && randomNumber != realNumber) {
                buttonValues[cnt] = randomNumber;
                cnt++;
            }
        }

        // Shuffle the button values
        shuffleArray(buttonValues);

        // Set the button texts
        button1.setText(String.valueOf(buttonValues[0]));
        button2.setText(String.valueOf(buttonValues[1]));
        button3.setText(String.valueOf(buttonValues[2]));
    }

    private boolean containsValue(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        boolean isCorrect = false;
        for(int i = 0; i < 3; i++){
            if(array[i] == realNumber){
                isCorrect = true;
            }
        }
        if(!isCorrect){
            // set a random index to the real number
            Random rand = new Random();
            int index = rand.nextInt(3);
            array[index] = realNumber;
        }
    }

    public void onClickButton1(View view) {
        checkAnswer(Integer.parseInt(button1.getText().toString()));
    }

    public void onClickButton2(View view) {
        checkAnswer(Integer.parseInt(button2.getText().toString()));
    }

    public void onClickButton3(View view) {
        checkAnswer(Integer.parseInt(button3.getText().toString()));
    }

    private void checkAnswer(int chosenNumber) {
        if (chosenNumber == realNumber) {
            // Increase the score if the chosen number is correct
            score++;

            // Show the congratulations dialog
            showCongratsDialog();
        } else {
            score--;
            // Show the wrong answer dialog
            updateScore();
            showWrongDialog();
        }
    }

    private void showCongratsDialog() {
        congratsDialog.show();

        // Apply animation to the congratulations dialog layout
        Animation dialogAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        LinearLayout congratsView = congratsDialog.findViewById(R.id.congrats_view); // Update the type to LinearLayout
        congratsView.startAnimation(dialogAnimation);

        // Dismiss the congratulations dialog after a delay
        congratsDialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congratsDialog.dismiss();
                generateRandomNumberOfCats();

            }
        });
    }

    private void showWrongDialog() {
        wrongDialog.show();

        // Apply animation to the wrong answer dialog layout
        Animation dialogAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        LinearLayout wrongAnsView = wrongDialog.findViewById(R.id.wrong_ans_view); // Update the type to LinearLayout
        wrongAnsView.startAnimation(dialogAnimation);

        // Dismiss the wrong answer dialog after a delay
        wrongDialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongDialog.dismiss();
            }
        });
    }

    private void updateScore() {
        // Get the score TextView
        final TextView scoreTextView = findViewById(R.id.score_text_view);

//        // Animate the score box
//        Animation scoreBoxAnimation = AnimationUtils.loadAnimation(this, R.anim.score_box_animation);
//        scoreTextView.startAnimation(scoreBoxAnimation);
////
//        // Animate the score text
//        Animation scoreTextAnimation = AnimationUtils.loadAnimation(this, R.anim.score_text_animation);
//        scoreTextView.startAnimation(scoreTextAnimation);

        // Update the score value
        scoreTextView.setText("Score: " + score);
    }

    private void startButtonAnimation() {
        button1.startAnimation(buttonAnimation);
        button2.startAnimation(buttonAnimation);
        button3.startAnimation(buttonAnimation);
    }
}
