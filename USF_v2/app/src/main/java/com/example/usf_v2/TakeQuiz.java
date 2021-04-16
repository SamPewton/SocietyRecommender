package com.example.usf_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TakeQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);

        Button BackButton = findViewById(R.id.ButtonBack);
        Button ContinueButton= findViewById(R.id.ButtonContinue);


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackButtonComplete();
            }
        });

        ContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContinueButtonComplete();
            }
        });



    }

    public void BackButtonComplete(){
        Intent intent = new Intent(this, mainMenu.class);
        startActivity(intent);
    }

    public void ContinueButtonComplete(){

    }
}