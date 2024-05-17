package com.example.sas;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainMathGame extends AppCompatActivity {
    private TextView number1TextView;
    private TextView signTextView;
    private TextView number2TextView;
    private EditText resultEditText;
    private Button checkButton;
    private TextView scoreTextView;
    private Random random;
    private int level;
    private int number1;
    private int number2;
    private int result;


    private int score;
    private Dialog congratsDialog;
    private Dialog wrongDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.math_main_game);

        // remove all the title bar
        getSupportActionBar().hide();

        score = 0;
        level = 1;
        number1TextView = findViewById(R.id.number1TextView);
        signTextView = findViewById(R.id.operatorTextView);
        number2TextView = findViewById(R.id.number2TextView);
        resultEditText = findViewById(R.id.resultEditText);
        checkButton = findViewById(R.id.checkButton);
        scoreTextView = findViewById(R.id.score_text_view);


        updateScore(); // Update the scoreTextView with the initial score

        random = new Random();

        generateQuestion();
        resultEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkAnswer();
                    return true;
                }
                return false;
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        // Initialize the congratulations dialog
        congratsDialog = new Dialog(this);
        congratsDialog.setContentView(R.layout.congrats_dialog);
        congratsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        wrongDialog = new Dialog(this);
        wrongDialog.setContentView(R.layout.wrong_ans);
        wrongDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void generateQuestion() {
        number1 = random.nextInt(10);
        number2 = random.nextInt(10);
        int signIndex = random.nextInt(3);
        if(score<=15)signIndex = 2;
        if(score<=10)signIndex = 1;
        if(score<=5)signIndex = 0;




        if(number1 < number2){
            int temp = number1;
            number1 = number2;
            number2 = temp;
        }

        String sign;
        int operationResult;

        switch (signIndex) {
            case 0:
                sign = "+";
                operationResult = number1 + number2;
                break;
            case 1:
                sign = "-";
                operationResult = number1 - number2;
                break;
            case 2:
                sign = "×";
                operationResult = number1 * number2;
                break;
            default:
                sign = "";
                operationResult = 0;
        }

        result = operationResult;

        number1TextView.setText(String.valueOf(number2));
        signTextView.setText(sign);
        number2TextView.setText(String.valueOf(number1));
        resultEditText.setText("");
    }

    private void checkAnswer() {
        String userInput = resultEditText.getText().toString().trim();

        if (!userInput.isEmpty()) {
            int userResult = Integer.parseInt(userInput);

            if (userResult == result) {
                // Correct answer
                showCongratsDialog();
                score++;
                updateScore();
            } else {
                // Incorrect answer
                showWrongDialog();
                score--;
                updateScore();
            }
            TextView levelTextView = findViewById(R.id.level1);
            levelTextView.setText("Level " + ((level/5)+1));
            generateQuestion();
        } else {
            Toast.makeText(this, "Please enter your answer", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCongratsDialog() {
        congratsDialog.show();

        // Apply animation to the congratulations dialog layout
        Animation dialogAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        LinearLayout congratsView = congratsDialog.findViewById(R.id.congrats_view);
        congratsView.startAnimation(dialogAnimation);

        // Dismiss the congratulations dialog after a delay
        congratsDialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                congratsDialog.dismiss();
            }
        });
    }

    private void showWrongDialog() {
        wrongDialog.show();

        // Apply animation to the wrong answer dialog layout
        Animation dialogAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        LinearLayout wrongAnsView = wrongDialog.findViewById(R.id.wrong_ans_view);
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

        scoreTextView.setText("Score: " + score);
    }
}
