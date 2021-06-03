package com.example.assignment01application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.assignment01application.extra.MESSAGE";
    private String timerChecked;
    private List<Integer> listOfCarImages =  new ArrayList<>(); //this list will hold all the images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildImageList();

        Switch timer = findViewById(R.id.switchTimer);

        /*
          checking whether the timer switch is on or off.
          based on that the variable timerChecked will be initialized asa true or false.
          and it will be passed on to the relevant intents.
         */
        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timerChecked = "true";
                } else {
                    timerChecked = "false";
                }
            }
        });
    }

    /**
     * action for the identify car make button.
     */
    public void identifyCarMake(View view) {
        Intent intent = new Intent(MainActivity.this, IdentifyTheCarMakeActivity.class);
        intent.putExtra(EXTRA_MESSAGE, timerChecked);
        intent.putExtra("listOfCarImages", (Serializable) listOfCarImages);
        startActivity(intent);
    }

    /**
     * action for the hints button.
     */
    public void hints(View view) {
        Intent intent = new Intent(MainActivity.this, HintsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, timerChecked);
        intent.putExtra("listOfCarImages", (Serializable) listOfCarImages);
        startActivity(intent);
    }

    /**
     * action for the identify car image button.
     */
    public void identifyCarImage(View view) {
        Intent intent = new Intent(MainActivity.this, IdentifyTheCarImageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, timerChecked);
        intent.putExtra("listOfCarImages", (Serializable) listOfCarImages);
        startActivity(intent);
    }

    /**
     * action for the advanced level button.
     */
    public void advancedLvl(View view) {
        Intent intent = new Intent(MainActivity.this, AdvancedLevelActivity.class);
        intent.putExtra(EXTRA_MESSAGE, timerChecked);
        intent.putExtra("listOfCarImages", (Serializable) listOfCarImages);
        startActivity(intent);
    }

    /**
     * in here listOfCarImages list will be filled with all the photos.
     * it will be passed on to the relevant intents.
     */
    public void buildImageList() {
        /*
          all photos from five different manufactures will be collected by iterating through drawables.
         */
        for (int i = 1; i <=8; i++) {
            String imageNameMercedes = "mercedes" + i;
            int imageIDMercedes = getResources().getIdentifier(imageNameMercedes, "drawable", getPackageName());
            listOfCarImages.add(imageIDMercedes);
        }

        for (int i = 1; i <=8; i++) {
            String imageNameAudi = "audi" + i;
            int imageIDAudi = getResources().getIdentifier(imageNameAudi, "drawable", getPackageName());
            listOfCarImages.add(imageIDAudi);
        }

        for (int i = 1; i <=8; i++) {
            String imageNameToyota = "toyota" + i;
            int imageIDToyota = getResources().getIdentifier(imageNameToyota, "drawable", getPackageName());
            listOfCarImages.add(imageIDToyota);
        }

        for (int i = 1; i <=8; i++) {
            String imageNameBMW = "bmw" + i;
            int imageIDBMW = getResources().getIdentifier(imageNameBMW, "drawable", getPackageName());
            listOfCarImages.add(imageIDBMW);
        }

        for (int i = 1; i <=8; i++) {
            String imageNameLamborghini = "lamborghini" + i;
            int imageIDLamborghini = getResources().getIdentifier(imageNameLamborghini, "drawable", getPackageName());
            listOfCarImages.add(imageIDLamborghini);
        }
    }

}