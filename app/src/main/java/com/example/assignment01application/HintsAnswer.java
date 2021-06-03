package com.example.assignment01application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HintsAnswer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints_answer);

        TextView checkAnswer = findViewById(R.id.answerCheck);
        TextView answer = findViewById(R.id.answer);

        Intent intent = getIntent();
        String message = intent.getStringExtra(HintsActivity.EXTRA_MESSAGE);

        List<String> modelList = new ArrayList<>();
        modelList.add("Mercedes");
        modelList.add("BMW");
        modelList.add("Audi");
        modelList.add("Lamborghini");
        modelList.add("Toyota");

        for(String model : modelList) {
            if (message.contains(model.toLowerCase())) {
                ImageView v1 = findViewById(R.id.modelLogo);
                int resource_id = getResources().getIdentifier(model.toLowerCase(), "drawable", "com.example.assignment01application");
                v1.setImageResource(resource_id);
            }
        }

        if(message.contains("Correct")){
            checkAnswer.setBackgroundColor(Color.GREEN);
            checkAnswer.setText("C O R R E C T");
            checkAnswer.setTextColor(Color.BLACK);

            for(String model : modelList) {
                if (message.contains(model.toLowerCase())) {
                    answer.setText("Correct Answer : " + model);
                    answer.setTextColor(Color.BLACK);
                }
            }
        }

        if (message.contains("Incorrect")){
            checkAnswer.setText("W R O N G");
            checkAnswer.setTextColor(Color.BLACK);
            checkAnswer.setBackgroundColor(Color.RED);

            for(String model : modelList) {
                if (message.contains(model.toLowerCase())) {
                    answer.setText("Correct Answer : " + model);
                    answer.setTextColor(Color.BLACK);
                }
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}