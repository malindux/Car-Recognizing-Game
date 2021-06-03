package com.example.assignment01application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintsActivity extends AppCompatActivity {

    private List<Integer> listOfCarImages;

    public static final String EXTRA_MESSAGE = "com.example.assignment01application.extra.MESSAGE";

    private int incorrectCount = 0; // This will increase as the user gives wrong answer. maximum count is 3.
    private int guessCount = 3;
    private boolean guessed = false;
    private String correctAns = null; // Correct answer will be saved into this variable.
    private String guessedAns = "Not Guessed Yet";

    private int index = 39; // Maximum bound to generate random index numbers (40 photos).
    private CountDownTimer countDownTimer;
    private long timeRemaining;
    private Intent intent;
    private View commonView;
    private String timerSwitchedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        intent = new Intent(this, HintsAnswer.class);

        Intent intent = getIntent();
        /*
         * getting the timerCheck (true or false) message.
         * getting the list of images and initialize it to the list declared in this class.
         */
        timerSwitchedMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        listOfCarImages = (List<Integer>) intent.getSerializableExtra("listOfCarImages");

        if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")) {
            startCountdownTimer(commonView);
        }

        ImageView carImg = findViewById(R.id.carImg);
        TextView modelGuess = findViewById(R.id.model);

        Random random = new Random();

        int randInt = random.nextInt(30);

        Integer image = listOfCarImages.get(randInt);

        carImg.setImageResource(image);
        /*
         * setting a tag to the image view with the same drawable name of the image.
         * by doing this it will be easy to retrieve the model name of the car which is displayed.
         */
        carImg.setTag(image);
        System.out.println(image);
        listOfCarImages.remove(randInt);

        // getting the model name from the image.
        String modelName = getResources().getResourceName((Integer) carImg.getTag());

        List<String> modelList = new ArrayList<>();
        modelList.add("Mercedes");
        modelList.add("BMW");
        modelList.add("Audi");
        modelList.add("Lamborghini");
        modelList.add("Toyota");

        for (String model : modelList) {
            if (modelName.contains(model.toLowerCase())) {
                //building a string with dashes (same amount as the letter count of the model name.)
                modelGuess.setText(new String(new char[model.length()]).replace("\0", "_ "));
            }
        }

        EditText guessedLetter = findViewById(R.id.guessedWord);

        /*
         * setting action for the keyboard enter key.
         * user can submit the guessed letter from keyboard enter key as well the submit button.
         */
        guessedLetter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (!guessed) {
                        submitGuess(commonView);
                    }
                }
                return false;
            }
        });

        /*
         * setting up the action bar to add a custom back button.
         */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * overriding the onOptionSelected to give a action to the custom back button.
     * main purpose of this custom back button is to stop the count down timer if user goes back.
     * otherwise it will run in the background and will trigger actions that were supposed to happen when the time is over.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //method to display a simple toast message.
    public void displayToast(View view, String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * actions for the submit button.
     */
    public void submitGuess(View view) {

        EditText guessedLetter = findViewById(R.id.guessedWord);
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);

        //validating user input.
        if (guessedLetter.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("   Enter a letter here");
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        TextView timer = findViewById(R.id.timer);

        ImageView carImg = findViewById(R.id.carImg);
        TextView modelGuess = findViewById(R.id.model);

        String modelName = getResources().getResourceName((Integer) carImg.getTag());
        String letter = guessedLetter.getText().toString();

        List<Integer> indexPositions = new ArrayList<>(); // to store index positions of the letter if it occurs more than once in the model name.

        List<String> modelList = new ArrayList<>();
        modelList.add("Mercedes");
        modelList.add("BMW");
        modelList.add("Audi");
        modelList.add("Lamborghini");
        modelList.add("Toyota");

        for (String model : modelList) {
            //checking if user reached maximum incorrect attempts.
            if (incorrectCount == 3) {
                break;
            } else if (modelName.contains(model.toLowerCase())) {
                correctAns = model.toLowerCase();

                //checking if the given letter is contained in the model name.
                if (model.toLowerCase().contains(letter.toLowerCase())) {

                    guessedLetter.setText("");

                    //getting indexes of all the occurrences of the guessed letter.
                    for (int i = 0; i < model.length(); i++) {
                        if (String.valueOf(model.toUpperCase().charAt(i)).equals(letter.toUpperCase())) {
                            indexPositions.add(i);
                        }
                    }

                    StringBuilder modelNameSb = new StringBuilder(modelGuess.getText());

                    //replacing dashes with the correct letter from the user
                    for (Integer index : indexPositions) {
                        if (String.valueOf(modelNameSb.charAt(index * 2)).equals("_")) {
                            modelNameSb.setCharAt(index * 2, letter.toUpperCase().charAt(0));
                        }
                    }

                    modelGuess.setText(modelNameSb.toString());
                    guessedAns = modelGuess.getText().toString().replace(" ", "");

                } else {
                    incorrectCount++;
                    guessCount--;
                    displayToast(view, "Guess is Wrong !  0" + guessCount + " Attempts Remaining");
                }

                if (correctAns.equals(guessedAns.toLowerCase())) {
                    guessed = true;
                    break;
                }
            }
        }

        if (guessed) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }

            //results will be shown in a different window.
            modelGuess.setTextColor(Color.GREEN);
            Button identifyBtn = findViewById(R.id.submit);
            Button nextBtn = findViewById(R.id.nextBtn);
            Intent intent = new Intent(this, HintsAnswer.class);
            String message = "Correct" + correctAns;
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            nextBtn.setVisibility(View.VISIBLE);
            identifyBtn.setVisibility(View.INVISIBLE);
        }

        if (incorrectCount == 3) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }

            //results will be shown in a different window.
            modelGuess.setText(correctAns.toUpperCase());
            modelGuess.setTextColor(Color.RED);
            Button identifyBtn = findViewById(R.id.submit);
            Button nextBtn = findViewById(R.id.nextBtn);
            Intent intent = new Intent(this, HintsAnswer.class);
            String message = "Incorrect" + correctAns;
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            nextBtn.setVisibility(View.VISIBLE);
            identifyBtn.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * actions for the next button.
     * same process will be executed as on the onCreate method.
     */
    public void next(View view) {

        //setting all values to their default value.
        guessCount = 3;
        incorrectCount = 0;
        guessed = false;
        correctAns = null;
        guessedAns = "Not Guessed Yet";

        EditText guessedLetter = findViewById(R.id.guessedWord);

        guessedLetter.setText("");

        if (listOfCarImages.size() == 0) {
            displayToast(view, "You have recognized all the cars");
        } else {
            if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")) {
                startCountdownTimer(view);
            }

            ImageView carImg = findViewById(R.id.carImg);
            TextView modelGuess = findViewById(R.id.model);

            Button identifyBtn = findViewById(R.id.submit);
            Button nextBtn = findViewById(R.id.nextBtn);

            Random random = new Random();

            int randInt = random.nextInt(index);
            Integer image = listOfCarImages.get(randInt);
            carImg.setImageResource(image);
            carImg.setTag(image);
            listOfCarImages.remove(image);
            index--;

            String modelName = getResources().getResourceName((Integer) carImg.getTag());

            System.out.println(modelName);

            List<String> modelList = new ArrayList<>();
            modelList.add("Mercedes");
            modelList.add("BMW");
            modelList.add("Audi");
            modelList.add("Lamborghini");
            modelList.add("Toyota");

            for (String model : modelList) {
                if (modelName.contains(model.toLowerCase())) {
                    modelGuess.setText(new String(new char[model.length()]).replace("\0", "_ "));
                    modelGuess.setTextColor(Color.rgb(255, 235, 59));
                }
            }

            nextBtn.setVisibility(View.INVISIBLE);
            identifyBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * this will trigger the timer.
     * inside this method there will also be the action for if the timer stops (given time is over).
     */
    private void startCountdownTimer(final View view) {

        final TextView timer = findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(21000, 1000) {

            //action when timer is running
            public void onTick(long millisUntilFinished) {
                timer.setText("You have " + (millisUntilFinished / 1000) + " seconds remaining");
                timeRemaining = millisUntilFinished;
            }

            //action if the time is over.
            public void onFinish() {

                timer.setText("Time's Up !");

                ImageView carImg = findViewById(R.id.carImg);
                TextView modelGuess = findViewById(R.id.model);
                EditText guessedLetter = findViewById(R.id.guessedWord);

                String modelName = getResources().getResourceName((Integer) carImg.getTag());
                String letter = guessedLetter.getText().toString();

                    List<Integer> indexPositions = new ArrayList<>();

                    List<String> modelList = new ArrayList<>();
                    modelList.add("Mercedes");
                    modelList.add("BMW");
                    modelList.add("Audi");
                    modelList.add("Lamborghini");
                    modelList.add("Toyota");

                    for (String model : modelList) {
                        if (incorrectCount == 3) {
                            break;
                        } else if (modelName.contains(model.toLowerCase())){
                            correctAns = model.toLowerCase();

                            if (model.toLowerCase().contains(letter.toLowerCase())) {

                                for (int i = 0; i < model.length(); i++) {
                                    if (String.valueOf(model.toUpperCase().charAt(i)).equals(letter.toUpperCase())) {
                                        indexPositions.add(i);
                                    }
                                }

                                StringBuilder modelNameSb = new StringBuilder(modelGuess.getText());

                                for (Integer index : indexPositions) {
                                    if (String.valueOf(modelNameSb.charAt(index * 2)).equals("_")) {
                                        modelNameSb.setCharAt(index * 2, letter.toUpperCase().charAt(0));
                                    }
                                }

                                modelGuess.setText(modelNameSb.toString());
                                guessedAns = modelGuess.getText().toString().replace(" ", "");

                            } else {
                                incorrectCount++;
                                guessCount--;
                                displayToast(view, "Your guess is wrong. " + guessCount + " more guesses are remaining...");
                            }

                            if (correctAns.equals(guessedAns.toLowerCase())) {
                                guessed = true;
                                break;
                            }
                        }
                    }

                    if (guessed) {
                        modelGuess.setTextColor(Color.GREEN);
                        Button identifyBtn = findViewById(R.id.submit);
                        Button nextBtn = findViewById(R.id.nextBtn);
                        String message = "Correct" + correctAns;
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                        nextBtn.setVisibility(View.VISIBLE);
                        identifyBtn.setVisibility(View.INVISIBLE);
                    }else {
                        modelGuess.setText(correctAns.toUpperCase());
                        modelGuess.setTextColor(Color.RED);
                        Button identifyBtn = findViewById(R.id.submit);
                        Button nextBtn = findViewById(R.id.nextBtn);
                        String message = "Incorrect" + correctAns;
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                        nextBtn.setVisibility(View.VISIBLE);
                        identifyBtn.setVisibility(View.INVISIBLE);
                    }

                    if (incorrectCount == 3) {
                        modelGuess.setText(correctAns.toUpperCase());
                        modelGuess.setTextColor(Color.RED);
                        Button identifyBtn = findViewById(R.id.submit);
                        Button nextBtn = findViewById(R.id.nextBtn);
                        String message = "Incorrect" + correctAns;
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                        nextBtn.setVisibility(View.VISIBLE);
                        identifyBtn.setVisibility(View.INVISIBLE);
                    }
            }

        }.start();
    }

}