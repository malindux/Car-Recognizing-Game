package com.example.assignment01application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdentifyTheCarImageActivity extends AppCompatActivity {

    private List<Integer> listOfCarImages;

    private List<String> modelList = new ArrayList<>();

    //setting up the maximum bound for each model as it placed on the list.
    private int mercedes = 8;
    private int audi = 16;
    private int toyota = 24;
    private int bmw = 32;
    private int lamborghini = 40;

    private CountDownTimer countDownTimer;
    private long timeRemaining;
    private View commonView;
    private String timerSwitchedMessage;
    private boolean imageClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_car_image);

        Intent intent = getIntent();
        /*
         * getting the timerCheck (true or false) message.
         * getting the list of images and initialize it to the list declared in this class.
         */
        timerSwitchedMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        listOfCarImages = (List<Integer>) intent.getSerializableExtra("listOfCarImages");

        if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")){
            startCountdownTimer(commonView);
        }

        List<Integer> selectedImages;

        TextView modelText = findViewById(R.id.model);

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        ImageView image1Wrong = findViewById(R.id.wrong1);
        ImageView image2Wrong = findViewById(R.id.wrong2);
        ImageView image3Wrong = findViewById(R.id.wrong3);

        image1Wrong.setVisibility(View.INVISIBLE);
        image2Wrong.setVisibility(View.INVISIBLE);
        image3Wrong.setVisibility(View.INVISIBLE);

        selectedImages = pickThreeRandomPhotos(); //setting up three random images.

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

        modelList.add("Mercedes");
        modelList.add("BMW");
        modelList.add("Audi");
        modelList.add("Lamborghini");
        modelList.add("Toyota");

        Random random = new Random();

        String modelName = getResources().getResourceName(selectedImages.get(random.nextInt(3))); //getting one model name from selected three images randomly.

        for (String model : modelList) {
            if (modelName.contains(model.toLowerCase())) {
                modelText.setText(model.toUpperCase()); //displaying selected model name.
            }
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
            randomInts.add(randInt1);;
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (audi - mercedes != 0) {
            int randInt2 = random.nextInt(audi - mercedes) + mercedes;
            randomInts.add(randInt2);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (toyota - audi != 0) {
            int randInt3 = random.nextInt(toyota - audi) + audi;
            randomInts.add(randInt3);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (bmw - toyota != 0) {
            int randInt4 = random.nextInt(bmw - toyota) + toyota;
            randomInts.add(randInt4);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

        if (lamborghini - bmw != 0) {
            int randInt5 = random.nextInt(lamborghini - bmw) + bmw;
            randomInts.add(randInt5);
        }else {
            randomInts.add(-1);
            minus1Count++;
        }

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

            selectedImages.add(listOfCarImages.get(randomInts.get(index1)));
            selectedImages.add(listOfCarImages.get(randomInts.get(index2)));
            selectedImages.add(listOfCarImages.get(randomInts.get(index3)));

            return selectedImages;
        }
    }

    //action for image 1
    public void selectedImage1(View view) {

        imageClicked = true;

        TextView timer = findViewById(R.id.timer);

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        ImageView image1Wrong = findViewById(R.id.wrong1);
        ImageView image2Wrong = findViewById(R.id.wrong2);
        ImageView image3Wrong = findViewById(R.id.wrong3);

        TextView checkAns = findViewById(R.id.answerCheck);
        TextView modelText = findViewById(R.id.model);

        checkAns.setVisibility(View.VISIBLE);

        //getting model name from image tag.
        String modelName = getResources().getResourceName((Integer) image1.getTag());

        //checking model name with the correct answer.
        if(modelName.contains(modelText.getText().toString().toLowerCase())) {
            checkAns.setText("C O R R E C T");
            checkAns.setTextColor(Color.BLACK);
            checkAns.setBackgroundColor(Color.GREEN);
            image1.setClickable(false);
            image2.setClickable(false);
            image3.setClickable(false);
            image2Wrong.setVisibility(View.VISIBLE);
            image3Wrong.setVisibility(View.VISIBLE);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }
        } else {
            image1.setClickable(false);
            image2.setClickable(false);
            image3.setClickable(false);
            checkImages();
            checkAns.setText("W R O N G");
            checkAns.setTextColor(Color.BLACK);
            checkAns.setBackgroundColor(Color.RED);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }
        }
    }

    //action for image 2
    public void selectedImage2(View view) {

        imageClicked = true;

        TextView timer = findViewById(R.id.timer);

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        ImageView image1Wrong = findViewById(R.id.wrong1);
        ImageView image2Wrong = findViewById(R.id.wrong2);
        ImageView image3Wrong = findViewById(R.id.wrong3);

        TextView checkAns = findViewById(R.id.answerCheck);
        TextView modelText = findViewById(R.id.model);

        checkAns.setVisibility(View.VISIBLE);

        //getting model name from image tag.
        String modelName = getResources().getResourceName((Integer) image2.getTag());

        //checking model name with the correct answer.
        if(modelName.contains(modelText.getText().toString().toLowerCase())) {
            checkAns.setText("C O R R E C T");
            checkAns.setTextColor(Color.BLACK);
            checkAns.setBackgroundColor(Color.GREEN);
            image1.setClickable(false);
            image2.setClickable(false);
            image3.setClickable(false);
            image1Wrong.setVisibility(View.VISIBLE);
            image3Wrong.setVisibility(View.VISIBLE);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }
        } else {
            image1.setClickable(false);
            image2.setClickable(false);
            image3.setClickable(false);
            checkImages();
            checkAns.setText("W R O N G");
            checkAns.setTextColor(Color.BLACK);
            checkAns.setBackgroundColor(Color.RED);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }
        }
    }

    //action for image3
    public void selectedImage3(View view) {

        imageClicked = true;

        TextView timer = findViewById(R.id.timer);

        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);

        ImageView image1Wrong = findViewById(R.id.wrong1);
        ImageView image2Wrong = findViewById(R.id.wrong2);
        ImageView image3Wrong = findViewById(R.id.wrong3);

        TextView checkAns = findViewById(R.id.answerCheck);
        TextView modelText = findViewById(R.id.model);

        checkAns.setVisibility(View.VISIBLE);

        //getting model name from image tag.
        String modelName = getResources().getResourceName((Integer) image3.getTag());

        //checking model name with the correct answer.
        if(modelName.contains(modelText.getText().toString().toLowerCase())) {
            checkAns.setText("C O R R E C T");
            checkAns.setTextColor(Color.BLACK);
            checkAns.setBackgroundColor(Color.GREEN);
            image1.setClickable(false);
            image2.setClickable(false);
            image3.setClickable(false);
            image1Wrong.setVisibility(View.VISIBLE);
            image2Wrong.setVisibility(View.VISIBLE);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }
        } else {
            image1.setClickable(false);
            image2.setClickable(false);
            image3.setClickable(false);
            checkImages();
            checkAns.setText("W R O N G");
            checkAns.setTextColor(Color.BLACK);
            checkAns.setBackgroundColor(Color.RED);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer.setText("Time Taken : " + (20 - (timeRemaining / 1000)) + " seconds");
            }
        }
    }

    /**
     * actions for the next button.
     * same process will be executed as on the onCreate method.
     */
    public void next(View view) {

        System.out.println(imageClicked);

        if(!imageClicked) {
            displayToast(view, "Guess the car first");
        }else {

            List<Integer> selectedImages = pickThreeRandomPhotos();

            if (selectedImages.size() >= 3) {

                ImageView image1 = findViewById(R.id.image1);
                ImageView image2 = findViewById(R.id.image2);
                ImageView image3 = findViewById(R.id.image3);

                ImageView image1Wrong = findViewById(R.id.wrong1);
                ImageView image2Wrong = findViewById(R.id.wrong2);
                ImageView image3Wrong = findViewById(R.id.wrong3);

                image1Wrong.setVisibility(View.INVISIBLE);
                image2Wrong.setVisibility(View.INVISIBLE);
                image3Wrong.setVisibility(View.INVISIBLE);

                image1.setClickable(true);
                image2.setClickable(true);
                image3.setClickable(true);

                TextView checkAns = findViewById(R.id.answerCheck);

                checkAns.setVisibility(View.INVISIBLE);

                TextView modelText = findViewById(R.id.model);

                image1.clearColorFilter();
                image2.clearColorFilter();
                image3.clearColorFilter();

                if (timerSwitchedMessage != null && timerSwitchedMessage.contains("true")) {
                    startCountdownTimer(view);
                }

                image1.setImageResource(selectedImages.get(0));
                image1.setTag(selectedImages.get(0));
                listOfCarImages.remove(selectedImages.get(0));

                image2.setImageResource(selectedImages.get(1));
                image2.setTag(selectedImages.get(1));
                listOfCarImages.remove(selectedImages.get(1));

                image3.setImageResource(selectedImages.get(2));
                image3.setTag(selectedImages.get(2));
                listOfCarImages.remove(selectedImages.get(2));

                modelList.add("Mercedes");
                modelList.add("BMW");
                modelList.add("Audi");
                modelList.add("Lamborghini");
                modelList.add("Toyota");

                Random random = new Random();

                String modelName = getResources().getResourceName(selectedImages.get(random.nextInt(3)));

                for (String model : modelList) {
                    if (modelName.contains(model.toLowerCase())) {
                        modelText.setText(model.toUpperCase());
                    }
                }

                imageClicked = false;

            } else {
                displayToast(view, "You have recognized all photos");
            }
        }
    }

    /**
     * this will trigger the timer.
     * inside this method there will also be the action for if the timer stops (given time is over).
     */
    private void startCountdownTimer(final View view) {

        ImageView image1Wrong = findViewById(R.id.wrong1);
        ImageView image2Wrong = findViewById(R.id.wrong2);
        ImageView image3Wrong = findViewById(R.id.wrong3);

        image1Wrong.setVisibility(View.INVISIBLE);
        image2Wrong.setVisibility(View.INVISIBLE);
        image3Wrong.setVisibility(View.INVISIBLE);

        final TextView timer = findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(21000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("You have " + (millisUntilFinished / 1000) + " seconds remaining");
                timeRemaining = millisUntilFinished;
            }

            public void onFinish() {

                imageClicked = true;

                ImageView image1 = findViewById(R.id.image1);
                ImageView image2 = findViewById(R.id.image2);
                ImageView image3 = findViewById(R.id.image3);

                image1.setClickable(false);
                image2.setClickable(false);
                image3.setClickable(false);

                checkImages();
                timer.setText("Time's Up!");
            }

        }.start();
    }

    //checking all three images to find the correct image
    private void checkImages() {

        final ImageView image1 = findViewById(R.id.image1);
        final ImageView image2 = findViewById(R.id.image2);
        final ImageView image3 = findViewById(R.id.image3);

        final ImageView image1Wrong = findViewById(R.id.wrong1);
        final ImageView image2Wrong = findViewById(R.id.wrong2);
        final ImageView image3Wrong = findViewById(R.id.wrong3);

        final TextView modelText = findViewById(R.id.model);

        if(getResources().getResourceName((Integer) image1.getTag()).contains(modelText.getText().toString().toLowerCase())){
            Animation expandIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_expand_in);
            Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_fade_in);
            image1.startAnimation(expandIn);
            image2Wrong.setVisibility(View.VISIBLE);
            image3Wrong.setVisibility(View.VISIBLE);
            image2Wrong.startAnimation(fadeIn);
            image3Wrong.startAnimation(fadeIn);
        }
        if(getResources().getResourceName((Integer) image2.getTag()).contains(modelText.getText().toString().toLowerCase())){
            Animation expandIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_expand_in);
            Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_fade_in);
            image2.startAnimation(expandIn);
            image1Wrong.setVisibility(View.VISIBLE);
            image3Wrong.setVisibility(View.VISIBLE);
            image1Wrong.startAnimation(fadeIn);
            image3Wrong.startAnimation(fadeIn);
        }
        if(getResources().getResourceName((Integer) image3.getTag()).contains(modelText.getText().toString().toLowerCase())){
            Animation expandIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_expand_in);
            Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_fade_in);
            image3.startAnimation(expandIn);
            image1Wrong.setVisibility(View.VISIBLE);
            image2Wrong.setVisibility(View.VISIBLE);
            image2Wrong.startAnimation(fadeIn);
            image1Wrong.startAnimation(fadeIn);
        }
    }

}