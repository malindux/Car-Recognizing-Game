package com.example.assignment01application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdentifyTheCarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int index = 39; //Maximum bound to generate random index numbers (40 photos).
    private String spinnerLabel; //This will have the selected model name from the spinner.
    private List<Integer> listOfCarImages; //This list will be initialized with the list passed from main activity.
    private CountDownTimer countDownTimer;
    private long timeRemaining;
    private String timerSwitchedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_car_make);

        Intent intent = getIntent();
        /*
         *getting the timerCheck (true or false) message.
         *getting the list of images and initialize it to the list declared in this class.
         */
        timerSwitchedMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        listOfCarImages = (List<Integer>) intent.getSerializableExtra("listOfCarImages");

        if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")){
            startCountdownTimer();
        }

        ImageView carImg = findViewById(R.id.carImg);

        Random random = new Random();

        int randInt = random.nextInt(40);

        Integer image = listOfCarImages.get(randInt);

        carImg.setImageResource(image);
        /*
         * setting a tag to the image view with the same drawable name of the image.
         * by doing this it will be easy to retrieve the model name of the car which is displayed.
         */
        carImg.setTag(image);
        listOfCarImages.remove(randInt);

        /*
         * setting up the spinner with the five model names given.
         * model name list is created inside the string.xml
         */
        Spinner spinner = findViewById(R.id.modelList);
        spinner.getBackground().setColorFilter(Color.rgb(255, 235, 59), PorterDuff.Mode.SRC_ATOP);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.models_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

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
     * overriding the onItemSelected to give a action to the spinner.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(255, 235, 59));
        ImageView v1 = findViewById(R.id.modelLogo);
        spinnerLabel = parent.getItemAtPosition(position).toString();
        int resource_id = getResources().getIdentifier(spinnerLabel.toLowerCase(), "drawable", "com.example.assignment01application");
        v1.setImageResource(resource_id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /**
     * actions for the Identify button.
     */
    public void identify(View view) {

        TextView timer = findViewById(R.id.timer);

        //checking if the countdown timer is not null and if so timer will stop when identify is clicked.
        if (countDownTimer != null) {
            countDownTimer.cancel();
            timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
        }

        ImageView carImg = findViewById(R.id.carImg);

        TextView checkAnswer = findViewById(R.id.answerCheck);
        TextView answer = findViewById(R.id.answer);

        Button identifyBtn = findViewById(R.id.submit);
        Button nextBtn = findViewById(R.id.nextBtn);

        checkAnswer.setVisibility(View.VISIBLE);
        answer.setVisibility(View.VISIBLE);

        //getting model name from image tag.
        String modelName = getResources().getResourceName((Integer) carImg.getTag());

        List<String> modelList = new ArrayList<>();
        modelList.add("Mercedes");
        modelList.add("BMW");
        modelList.add("Audi");
        modelList.add("Lamborghini");
        modelList.add("Toyota");

        //checking if the selected item from the spinner is correct or wrong.
        if(modelName.contains(spinnerLabel.toLowerCase())){
            checkAnswer.setBackgroundColor(Color.GREEN);
            checkAnswer.setText("C O R R E C T");
            checkAnswer.setTextColor(Color.BLACK);
            answer.setText("Correct Answer : " + spinnerLabel);
            answer.setTextColor(Color.BLACK);
        }else {
            checkAnswer.setText("W R O N G");
            checkAnswer.setTextColor(Color.BLACK);
            checkAnswer.setBackgroundColor(Color.RED);
            for (String model : modelList) {
                if (modelName.contains(model.toLowerCase())) {
                    answer.setText("Correct Answer : " + model);
                    answer.setTextColor(Color.BLACK);
                }
            }
        }
        nextBtn.setVisibility(View.VISIBLE);
        identifyBtn.setVisibility(View.INVISIBLE);
    }

    /**
     * actions for the next button.
     * same process will be executed as on the onCreate method.
     */
    public void next(View view) {
        /*
          index equals to the zero means that the next button is clicked 39 times which means all the photos have been answered by the user.
          when a image is randomly selected on the action of the next button, the value of index will be reduced by 1 every time until it reach 0.
         */
        if(index == 0){
            displayToast(view,"You have recognized all the cars");
        } else {

            // timer will start according to the timerSwitchedMessage.
            if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")){
                startCountdownTimer();
            }

            ImageView carImg = findViewById(R.id.carImg);

            Button identifyBtn = findViewById(R.id.submit);
            Button nextBtn = findViewById(R.id.nextBtn);

            TextView checkAnswer = findViewById(R.id.answerCheck);
            TextView answer = findViewById(R.id.answer);

            checkAnswer.setVisibility(View.INVISIBLE);
            answer.setVisibility(View.INVISIBLE);

            System.out.println(index);
            Random random = new Random();

            int randInt = random.nextInt(index);
            Integer image = listOfCarImages.get(randInt);
            carImg.setImageResource(image);
            carImg.setTag(image);
            listOfCarImages.remove(image);
            index--;

            nextBtn.setVisibility(View.INVISIBLE);
            identifyBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * this will trigger the timer.
     * inside this method there will also be the action for if the timer stops (given time is over).
     */
    private void startCountdownTimer() {

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

                TextView checkAnswer = findViewById(R.id.answerCheck);
                TextView answer = findViewById(R.id.answer);

                Button identifyBtn = findViewById(R.id.submit);
                Button nextBtn = findViewById(R.id.nextBtn);

                checkAnswer.setVisibility(View.VISIBLE);
                answer.setVisibility(View.VISIBLE);

                String modelName = getResources().getResourceName((Integer) carImg.getTag());

                System.out.println(modelName);

                List<String> modelList = new ArrayList<>();
                modelList.add("Mercedes");
                modelList.add("BMW");
                modelList.add("Audi");
                modelList.add("Lamborghini");
                modelList.add("Toyota");

                if(modelName.contains(spinnerLabel.toLowerCase())){
                    System.out.println("true");
                    checkAnswer.setBackgroundColor(Color.GREEN);
                    checkAnswer.setText("C O R R E C T");
                    checkAnswer.setTextColor(Color.BLACK);
                    answer.setText("Correct Answer : " + spinnerLabel);
                    answer.setTextColor(Color.BLACK);
                }else {
                    System.out.println("false");
                    checkAnswer.setText("W R O N G");
                    checkAnswer.setTextColor(Color.BLACK);
                    checkAnswer.setBackgroundColor(Color.RED);

                    for (String model : modelList) {
                        if (modelName.contains(model.toLowerCase())) {
                            answer.setText("Correct Answer : " + model);
                            answer.setTextColor(Color.BLACK);
                        }
                    }
                }
                nextBtn.setVisibility(View.VISIBLE);
                identifyBtn.setVisibility(View.INVISIBLE);
            }

        }.start();
    }

}