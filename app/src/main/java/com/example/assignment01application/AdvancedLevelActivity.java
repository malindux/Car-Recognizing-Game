package com.example.assignment01application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdvancedLevelActivity extends AppCompatActivity {

    private List<Integer> listOfCarImages;

    //setting up the maximum bound for each model as it placed on the list.
    private int mercedes = 8;
    private int audi = 16;
    private int toyota = 24;
    private int bmw = 32;
    private int lamborghini = 40;

    //scores for answer 1,2 and 3.
    private int score1 = 0;
    private int score2 = 0;
    private int score3 = 0;

    //total score will be the sum of score1, score2 and score3.
    private int totalScore;

    // This will increase as the user gives wrong answer. maximum count is 3.
    private int incorrectCount = 0;

    private boolean ans1Correct = false; //checks answer 1
    private boolean ans2Correct = false; //checks answer 2
    private boolean ans3Correct = false; //checks answer 3

    private CountDownTimer countDownTimer;

    private long timeRemaining;

    private String timerSwitchedMessage;

    private String modelNameImg1 = null;
    private String modelNameImg2 = null;
    private String modelNameImg3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        Intent intent = getIntent();
        /*
         * getting the timerCheck (true or false) message.
         * getting the list of images and initialize it to the list declared in this class.
         */
        timerSwitchedMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        listOfCarImages = (List<Integer>) intent.getSerializableExtra("listOfCarImages");

        EditText ans1 = findViewById(R.id.ans1);
        EditText ans2 = findViewById(R.id.ans2);
        EditText ans3 = findViewById(R.id.ans3);

        ans1.setBackgroundColor(Color.TRANSPARENT);
        ans2.setBackgroundColor(Color.TRANSPARENT);
        ans3.setBackgroundColor(Color.TRANSPARENT);

        List<Integer> selectedImages;

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        selectedImages = pickThreeRandomPhotos();

        image1.setImageResource(selectedImages.get(0));
        /*
         * setting a tag to the image view with the same drawable name of the image.
         * by doing this it will be easy to retrieve the model name of the car which is displayed.
         */
        image1.setTag(selectedImages.get(0));
        listOfCarImages.remove(selectedImages.get(0));

        image2.setImageResource(selectedImages.get(1));
        /*
         * setting a tag to the image view with the same drawable name of the image.
         * by doing this it will be easy to retrieve the model name of the car which is displayed.
         */
        image2.setTag(selectedImages.get(1));
        listOfCarImages.remove(selectedImages.get(1));

        image3.setImageResource(selectedImages.get(2));
        /*
         * setting a tag to the image view with the same drawable name of the image.
         * by doing this it will be easy to retrieve the model name of the car which is displayed.
         */
        image3.setTag(selectedImages.get(2));
        listOfCarImages.remove(selectedImages.get(2));

        if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")){
            startCountdownTimer();
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
     * this method will pick three random images.
     * and it will be from three different manufacturers.
     */
    private List<Integer> pickThreeRandomPhotos() {

        int minus1Count = 0;

        int randomBound = 5;

        List<Integer> selectedImages = new ArrayList<>();

        List<Integer> randomInts = new ArrayList<>();

        Random random = new Random();

        if (mercedes != 0) {
            int randInt1 = random.nextInt(mercedes);
            randomInts.add(randInt1);
            System.out.println(randInt1);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (audi - mercedes != 0) {
            int randInt2 = random.nextInt(audi - mercedes) + mercedes;
            randomInts.add(randInt2);
            System.out.println(randInt2);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (toyota - audi != 0) {
            int randInt3 = random.nextInt(toyota - audi) + audi;
            randomInts.add(randInt3);
            System.out.println(randInt3);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (bmw - toyota != 0) {
            int randInt4 = random.nextInt(bmw - toyota) + toyota;
            randomInts.add(randInt4);
            System.out.println(randInt4);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (lamborghini - bmw != 0) {
            int randInt5 = random.nextInt(lamborghini - bmw) + bmw;
            randomInts.add(randInt5);
            System.out.println(randInt5);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        System.out.println("-----------Bound Values------------");
        System.out.println(mercedes);
        System.out.println(audi);
        System.out.println(toyota);
        System.out.println(bmw);
        System.out.println(lamborghini);
        System.out.println("-----------Bound Values------------");

        System.out.println();

        System.out.println("-----------------bound-----------------");
        System.out.println(randomBound);
        System.out.println("-----------------bound-----------------");

        System.out.println();

        if (minus1Count >= 3){
            return selectedImages;
        } else {
            //indexes fro three random images.
            int index1;
            int index2;
            int index3;
            while (true) {
                index1 = random.nextInt(randomBound);
                if (randomInts.get(index1) == -1) continue;
                while (true) {
                    index2 = random.nextInt(randomBound);
                    if (randomInts.get(index2) == -1) continue;
                    if (index1 == index2) continue;
                    while (true) {
                        index3 = random.nextInt(randomBound);
                        if (randomInts.get(index3) == -1) continue;
                        if (index1 == index3 || index2 == index3) continue;
                        break;
                    }
                    break;
                }
                break;
            }

            if (index1 == 0) {
                mercedes--;
                audi--;
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index1 == 1) {
                audi--;
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index1 == 2) {
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index1 == 3) {
                bmw--;
                lamborghini--;
            }
            if (index1 == 4) {
                lamborghini--;
            }

            if (index2 == 0) {
                mercedes--;
                audi--;
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index2 == 1) {
                audi--;
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index2 == 2) {
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index2 == 3) {
                bmw--;
                lamborghini--;
            }
            if (index2 == 4) {
                lamborghini--;
            }

            if (index3 == 0) {
                mercedes--;
                audi--;
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index3 == 1) {
                audi--;
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index3 == 2) {
                toyota--;
                bmw--;
                lamborghini--;
            }
            if (index3 == 3) {
                bmw--;
                lamborghini--;
            }
            if (index3 == 4) {
                lamborghini--;
            }

            System.out.println("-----------------indexes-------------------");
            System.out.println(index1);
            System.out.println(index2);
            System.out.println(index3);
            System.out.println("-----------------indexes-------------------");

            selectedImages.add(listOfCarImages.get(randomInts.get(index1)));
            selectedImages.add(listOfCarImages.get(randomInts.get(index2)));
            selectedImages.add(listOfCarImages.get(randomInts.get(index3)));

            System.out.println(selectedImages.toString());

            return selectedImages;
        }
    }

    /**
     * actions for the submit button.
     */
    public void submit(View view) {

        List<String> modelList = new ArrayList<>();

        modelList.add("Mercedes");
        modelList.add("BMW");
        modelList.add("Audi");
        modelList.add("Lamborghini");
        modelList.add("Toyota");

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        EditText ans1 = findViewById(R.id.ans1);
        EditText ans2 = findViewById(R.id.ans2);
        EditText ans3 = findViewById(R.id.ans3);

        TextInputLayout ans1Layout = findViewById(R.id.ansLayout1);
        TextInputLayout ans2Layout = findViewById(R.id.ansLayout2);
        TextInputLayout ans3Layout = findViewById(R.id.ansLayout3);

        TextView ans1Check = findViewById(R.id.ans1Check);
        TextView ans2Check = findViewById(R.id.ans2Check);
        TextView ans3Check = findViewById(R.id.ans3Check);

        TextView ans1CheckWrong = findViewById(R.id.ans1CheckWrong);
        TextView ans2CheckWrong = findViewById(R.id.ans2CheckWrong);
        TextView ans3CheckWrong = findViewById(R.id.ans3CheckWrong);

        TextView timer = findViewById(R.id.timer);

        TextView score = findViewById(R.id.textView2);

        Button submit = findViewById(R.id.submit);
        Button next = findViewById(R.id.next);

        /*
         * checking if all three editTexts are filled.
         * user have to guess all three models before submit.
         */
        if(ans1.getText().toString().equals("") || ans2.getText().toString().equals("") || ans3.getText().toString().equals("")){
            displayToast(view, "Guess all three models before submit");
        } else {
            //getting model names of each image from image tag.
            modelNameImg1 = getResources().getResourceName((Integer) image1.getTag());
            modelNameImg2 = getResources().getResourceName((Integer) image2.getTag());
            modelNameImg3 = getResources().getResourceName((Integer) image3.getTag());

            if (score1 == 0) {
                //checking given answer with correct answer.
                if (modelNameImg1.contains(ans1.getText().toString().toLowerCase())) {
                    ans1Correct = true;
                    ans1.setEnabled(false);
                    ans1Check.setVisibility(View.VISIBLE);
                    ans1.setVisibility(View.INVISIBLE);
                    ans1Layout.setVisibility(View.INVISIBLE);
                    ans1Check.setText(ans1.getText().toString().toUpperCase());
                    ans1Check.setBackgroundColor(Color.rgb(152,251,152));
                    //updating score
                    score1++;
                    totalScore = totalScore + score1;
                }else{
                    ans1Layout.setError("             Wrong Answer");
                    ans1.setTextColor(Color.rgb(253,89,48));
                }
            }

            if (score2 == 0) {
                //checking given answer with correct answer.
                if (modelNameImg2.contains(ans2.getText().toString().toLowerCase())) {
                    ans2Correct = true;
                    ans2.setEnabled(false);
                    ans2Check.setVisibility(View.VISIBLE);
                    ans2.setVisibility(View.INVISIBLE);
                    ans2Layout.setVisibility(View.INVISIBLE);
                    ans2Check.setText(ans2.getText().toString().toUpperCase());
                    ans2Check.setBackgroundColor(Color.rgb(152,251,152));
                    //updating score
                    score2++;
                    totalScore = totalScore + score2;
                }else {
                    ans2Layout.setError("             Wrong Answer");
                    ans2.setTextColor(Color.rgb(253,89,48));
                }
            }

            if (score3 == 0) {
                //checking given answer with correct answer.
                if (modelNameImg3.contains(ans3.getText().toString().toLowerCase())) {
                    ans3Correct = true;
                    ans3.setEnabled(false);
                    ans3Check.setVisibility(View.VISIBLE);
                    ans3.setVisibility(View.INVISIBLE);
                    ans3Layout.setVisibility(View.INVISIBLE);
                    ans3Check.setText(ans3.getText().toString().toUpperCase());
                    ans3Check.setBackgroundColor(Color.rgb(152,251,152));
                    //updating score
                    score3++;
                    totalScore = totalScore + score3;
                }else {
                    ans3Layout.setError("             Wrong Answer");
                    ans3.setTextColor(Color.rgb(253,89,48));
                }
            }

            score.setText(String.valueOf(totalScore));

            /*
            * if all three guesses are correct following actions will happen
            * timer stops if its activated already.
            * correct message will be shown for all three images.
            * next button will appear.
            */
            if (ans1Correct && ans2Correct && ans3Correct) {
                ans1Check.setText("Correct");
                ans2Check.setText("Correct");
                ans3Check.setText("Correct");
                submit.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
                }
            } else {
                //incrementing incorrect count
                incorrectCount = incorrectCount + 1;
                if(incorrectCount < 3) {
                    displayToast(view, "Guess is Wrong !  0" + (3 - incorrectCount) + " Attempts Remaining");
                }else {
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout_root));

                    TextView text = (TextView) toastLayout.findViewById(R.id.text);
                    text.setText("     W R O N G !     ");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 847);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(toastLayout);
                    toast.show();
                }
            }
        }

        /*
         * checking if user reached maximum num of attempts.
         * all the on processes will be stopped.
         */
        if (incorrectCount == 3){
            submit.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }

            //displaying correct answer.
            if (!ans1Correct){
                for (String model : modelList) {
                    if (modelNameImg1.contains(model.toLowerCase())) {
                        ans1CheckWrong.setVisibility(View.VISIBLE);
                        ans1CheckWrong.setText(model);
                        ans1CheckWrong.setBackgroundColor(Color.rgb(255,235,59));
                    }
                }
            }

            //displaying correct answer.
            if (!ans2Correct){
                for (String model : modelList) {
                    if (modelNameImg2.contains(model.toLowerCase())) {
                        ans2CheckWrong.setVisibility(View.VISIBLE);
                        ans2CheckWrong.setText(model);
                        ans2CheckWrong.setBackgroundColor(Color.rgb(255,235,59));
                    }
                }
            }

            //displaying correct answer.
            if (!ans3Correct){
                for (String model : modelList) {
                    if (modelNameImg3.contains(model.toLowerCase())) {
                        ans3CheckWrong.setVisibility(View.VISIBLE);
                        ans3CheckWrong.setText(model);
                        ans3CheckWrong.setBackgroundColor(Color.rgb(255,235,59));
                    }
                }
            }
        }
    }

    /**
     * actions for the next button.
     * same process will be executed as on the onCreate method.
     */
    public void next(View view) {

        score1 = 0;
        score2 = 0;
        score3 = 0;

        incorrectCount = 0;

        ans1Correct = false;
        ans2Correct = false;
        ans3Correct = false;

        EditText ans1 = findViewById(R.id.ans1);
        EditText ans2 = findViewById(R.id.ans2);
        EditText ans3 = findViewById(R.id.ans3);

        ans1.setTextColor(Color.rgb(255,235,59));
        ans2.setTextColor(Color.rgb(255,235,59));
        ans3.setTextColor(Color.rgb(255,235,59));

        TextInputLayout ans1Layout = findViewById(R.id.ansLayout1);
        TextInputLayout ans2Layout = findViewById(R.id.ansLayout2);
        TextInputLayout ans3Layout = findViewById(R.id.ansLayout3);

        ans1Layout.setError(null);
        ans2Layout.setError(null);
        ans3Layout.setError(null);

        TextView ans1Check = findViewById(R.id.ans1Check);
        TextView ans2Check = findViewById(R.id.ans2Check);
        TextView ans3Check = findViewById(R.id.ans3Check);

        TextView ans1CheckWrong = findViewById(R.id.ans1CheckWrong);
        TextView ans2CheckWrong = findViewById(R.id.ans2CheckWrong);
        TextView ans3CheckWrong = findViewById(R.id.ans3CheckWrong);

        ans1.setEnabled(true);
        ans2.setEnabled(true);
        ans3.setEnabled(true);

        ans1.setText(null);
        ans2.setText(null);
        ans3.setText(null);

        List<Integer> selectedImages;

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        selectedImages = pickThreeRandomPhotos();

        if (selectedImages.size() >= 3) {

            if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")){
                startCountdownTimer();
            }

            ans1Check.setVisibility(View.INVISIBLE);
            ans1CheckWrong.setVisibility(View.INVISIBLE);
            ans1.setVisibility(View.VISIBLE);
            ans1Layout.setVisibility(View.VISIBLE);

            ans2Check.setVisibility(View.INVISIBLE);
            ans2CheckWrong.setVisibility(View.INVISIBLE);
            ans2.setVisibility(View.VISIBLE);
            ans2Layout.setVisibility(View.VISIBLE);

            ans3Check.setVisibility(View.INVISIBLE);
            ans3CheckWrong.setVisibility(View.INVISIBLE);
            ans3.setVisibility(View.VISIBLE);
            ans3Layout.setVisibility(View.VISIBLE);

            Button submit = findViewById(R.id.submit);
            Button next = findViewById(R.id.next);

            submit.setVisibility(View.VISIBLE);
            next.setVisibility(View.INVISIBLE);

            image1.setImageResource(selectedImages.get(0));
            image1.setTag(selectedImages.get(0));
            listOfCarImages.remove(selectedImages.get(0));

            image2.setImageResource(selectedImages.get(1));
            image2.setTag(selectedImages.get(1));
            listOfCarImages.remove(selectedImages.get(1));

            image3.setImageResource(selectedImages.get(2));
            image3.setTag(selectedImages.get(2));
            listOfCarImages.remove(selectedImages.get(2));

        }else {

            TextView score = findViewById(R.id.textView2);
            TextView scoreLabel = findViewById(R.id.textView);

            Animation expandIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_expand_in);
            Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_fade_in);

            score.setAnimation(fadeIn);
            scoreLabel.setAnimation(fadeIn);

            displayToast(view, "You have recognized all photos");

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
                timer.setText((millisUntilFinished / 1000) + " seconds remaining");
                timeRemaining = millisUntilFinished;
            }

            //action if the time is over.
            public void onFinish() {

                timer.setText("Time's Up!");

                List<String> modelList = new ArrayList<>();

                modelList.add("Mercedes");
                modelList.add("BMW");
                modelList.add("Audi");
                modelList.add("Lamborghini");
                modelList.add("Toyota");

                ImageView image1 = findViewById(R.id.image1);
                ImageView image2 = findViewById(R.id.image2);
                ImageView image3 = findViewById(R.id.image3);

                EditText ans1 = findViewById(R.id.ans1);
                EditText ans2 = findViewById(R.id.ans2);
                EditText ans3 = findViewById(R.id.ans3);

                TextInputLayout ans1Layout = findViewById(R.id.ansLayout1);
                TextInputLayout ans2Layout = findViewById(R.id.ansLayout2);
                TextInputLayout ans3Layout = findViewById(R.id.ansLayout3);

                TextView ans1Check = findViewById(R.id.ans1Check);
                TextView ans2Check = findViewById(R.id.ans2Check);
                TextView ans3Check = findViewById(R.id.ans3Check);

                TextView ans1CheckWrong = findViewById(R.id.ans1CheckWrong);
                TextView ans2CheckWrong = findViewById(R.id.ans2CheckWrong);
                TextView ans3CheckWrong = findViewById(R.id.ans3CheckWrong);

                TextView score = findViewById(R.id.textView2);

                Button submit = findViewById(R.id.submit);
                Button next = findViewById(R.id.next);

                //getting model names of each image from image tag.
                modelNameImg1 = getResources().getResourceName((Integer) image1.getTag());
                modelNameImg2 = getResources().getResourceName((Integer) image2.getTag());
                modelNameImg3 = getResources().getResourceName((Integer) image3.getTag());

                if (score1 == 0) {
                    //checking given answer with correct answer.
                    if (!ans1.getText().toString().toLowerCase().equals("") && modelNameImg1.contains(ans1.getText().toString().toLowerCase())) {
                        ans1Correct = true;
                        ans1.setEnabled(false);
                        ans1Check.setVisibility(View.VISIBLE);
                        ans1.setVisibility(View.INVISIBLE);
                        ans1Layout.setVisibility(View.INVISIBLE);
                        ans1Check.setText(ans1.getText().toString().toUpperCase());
                        ans1Check.setBackgroundColor(Color.rgb(152,251,152));
                        //updating score
                        score1++;
                        totalScore = totalScore + score1;
                    }
                }

                if (score2 == 0) {
                    //checking given answer with correct answer.
                    if (!ans2.getText().toString().toLowerCase().equals("") && modelNameImg2.contains(ans2.getText().toString().toLowerCase())) {
                        ans2Correct = true;
                        ans2.setEnabled(false);
                        ans2Check.setVisibility(View.VISIBLE);
                        ans2.setVisibility(View.INVISIBLE);
                        ans2Layout.setVisibility(View.INVISIBLE);
                        ans2Check.setText(ans2.getText().toString().toUpperCase());
                        ans2Check.setBackgroundColor(Color.rgb(152,251,152));
                        //updating score
                        score2++;
                        totalScore = totalScore + score2;
                    }
                }

                if (score3 == 0) {
                    //checking given answer with correct answer.
                    if (!ans3.getText().toString().toLowerCase().equals("") && modelNameImg3.contains(ans3.getText().toString().toLowerCase())) {
                        ans3Correct = true;
                        ans3.setEnabled(false);
                        ans3Check.setVisibility(View.VISIBLE);
                        ans3.setVisibility(View.INVISIBLE);
                        ans3Layout.setVisibility(View.INVISIBLE);
                        ans3Check.setText(ans3.getText().toString().toUpperCase());
                        ans3Check.setBackgroundColor(Color.rgb(152,251,152));
                        //updating score
                        score3++;
                        totalScore = totalScore + score3;
                    }
                }

                score.setText(String.valueOf(totalScore));

                System.out.println(ans1Correct);
                System.out.println(ans2Correct);
                System.out.println(ans3Correct);

                /*
                 * if all three guesses are correct following actions will happen
                 * timer stops if its activated already.
                 * correct message will be shown for all three images.
                 * next button will appear.
                 */
                if (ans1Correct && ans2Correct && ans3Correct) {
                    ans1Check.setText("Correct");
                    ans2Check.setText("Correct");
                    ans3Check.setText("Correct");
                    submit.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);
                }else{
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout_root));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("     W R O N G !     ");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 847);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

                submit.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);

                //displaying correct answer.
                if (!ans1Correct){
                    for (String model : modelList) {
                        if (modelNameImg1.contains(model.toLowerCase())) {
                            ans1.setTextColor(Color.rgb(253,89,48));
                            ans1CheckWrong.setVisibility(View.VISIBLE);
                            ans1CheckWrong.setText(model);
                            ans1CheckWrong.setBackgroundColor(Color.rgb(255,235,59));
                        }
                    }
                }

                //displaying correct answer.
                if (!ans2Correct){
                    for (String model : modelList) {
                        if (modelNameImg2.contains(model.toLowerCase())) {
                            ans2.setTextColor(Color.rgb(253,89,48));
                            ans2CheckWrong.setVisibility(View.VISIBLE);
                            ans2CheckWrong.setText(model);
                            ans2CheckWrong.setBackgroundColor(Color.rgb(255,235,59));
                        }
                    }
                }

                //displaying correct answer.
                if (!ans3Correct){
                    for (String model : modelList) {
                        if (modelNameImg3.contains(model.toLowerCase())) {
                            ans3.setTextColor(Color.rgb(253,89,48));
                            ans3CheckWrong.setVisibility(View.VISIBLE);
                            ans3CheckWrong.setText(model);
                            ans3CheckWrong.setBackgroundColor(Color.rgb(255,235,59));
                        }
                    }
                }
            }
        }.start();
    }

}