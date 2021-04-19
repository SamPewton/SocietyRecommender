package com.example.usf_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class TakeQuiz extends AppCompatActivity {
    String un = "";
    String pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        Button BackButton = findViewById(R.id.ButtonBack);
        Button ContinueButton= findViewById(R.id.ButtonContinue);
        Intent passedIn = getIntent();
        un = passedIn.getStringExtra("user_name");
        pw = passedIn.getStringExtra("user_pw");

        RadioButton r11 = findViewById(R.id.Q1Y);
        RadioButton r12 = findViewById(R.id.Q1N);
        RadioButton r21 = findViewById(R.id.Q2Y);
        RadioButton r22 = findViewById(R.id.Q2N);
        RadioButton r31 = findViewById(R.id.Q3Y);
        RadioButton r32 = findViewById(R.id.Q3N);
        RadioButton r41 = findViewById(R.id.Q4Y);
        RadioButton r42 = findViewById(R.id.Q4N);
        RadioButton r51 = findViewById(R.id.Q5Y);
        RadioButton r52 = findViewById(R.id.Q5N);
        RadioButton[] buttons = {r11, r12, r21, r22, r31, r32, r41, r42, r51, r52};

        CheckBox[] multiSelectAnswers = {findViewById(R.id.CheckBoxCR), findViewById(R.id.CheckBoxSport), findViewById(R.id.CheckBoxArt),
                findViewById(R.id.CheckBoxMusic), findViewById(R.id.CheckBoxTechnology)};


        LoginFunction sql = new LoginFunction(un, pw, this);
        String[] userInputtedUN = {un};


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackButtonComplete();
            }
        });

        ContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateAnswers(buttons)) {
                    sql.runAlgorithm(userInputtedUN, completeAnswers(buttons, multiSelectAnswers));
                    ContinueButtonComplete();
                }
            }
        });
    }


    public boolean validateAnswers(RadioButton[] buttons) {
        for(int i = 0 ; i < buttons.length ; i = i + 2)
        {
            if(buttons[i].isChecked() && buttons[i+1].isChecked() || !(buttons[i].isChecked()) && !(buttons[i+1].isChecked())) {
                String wrongPass = "You have filled out the quiz incorrectly!";
                Toast.makeText(getApplicationContext(), wrongPass, Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public String[] completeAnswers(RadioButton[] buttons, CheckBox[] multiSelectAnswers) {
        String[] answers = new String[5];
        int cursor = 0;

        for (int i = 0; i < buttons.length; i = i + 2) {
            if (buttons[i].isChecked()) {
                answers[cursor] = "Y";
            } else {
                answers[cursor] = "N";
            }
            cursor++;
        }

        for(int j = 0 ; j < multiSelectAnswers.length ; j++)
        {
            if(answers[j].equals("N") && multiSelectAnswers[j].isChecked())
            {
                answers[j] = "Y";
            }
        }

        return answers;
    }

    public void BackButtonComplete() {
        Intent intent = new Intent(this, mainMenu.class);
        intent.putExtra("user_name", un);
        intent.putExtra("user_pw", pw);
        startActivity(intent);
    }

    public void ContinueButtonComplete() {
        Intent intent = new Intent(this, SocietiesView.class);
        intent.putExtra("user_name", un);
        intent.putExtra("user_pw", pw);
        intent.putExtra( "type", 1);
        startActivity(intent);
    }
}