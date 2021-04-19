package com.example.usf_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mainMenu extends AppCompatActivity {
    String un = "";
    String pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        Button TakeQuizButton = findViewById(R.id.button2);
        Button displayAllButton = findViewById(R.id.button3);
        Button displayRecommendedButton = findViewById(R.id.button4);
        Button logOutButton = findViewById(R.id.button5);
        ImageButton takeQuizInfo = findViewById(R.id.imageButton);
        ImageButton viewAllSocInfo = findViewById(R.id.imageButton2);
        ImageButton viewAllRecInfo = findViewById(R.id.imageButton3);
        final TextView tqInfo = findViewById(R.id.textView);
        final TextView socInfo = findViewById(R.id.textView2);
        final TextView recInfo = findViewById(R.id.textView3);
        Intent passedIn = getIntent();
        un = passedIn.getStringExtra("user_name");
        pw = passedIn.getStringExtra("user_pw");

        LoginFunction sql = new LoginFunction(un, pw, this);
        String[] userInputtedUN = {un};

        displayAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllSocieties();
            }
        });


        displayRecommendedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sql.getUserSocietyCount(userInputtedUN) != 0) {
                    viewRecommendedSocieties();
                }
                else {
                    System.out.println("no societies here");
                }
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutComplete();
            }
        });

        TakeQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OpenQuiz(); }
        });



        takeQuizInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tqInfo.setText("This option allows you to take the quiz to determine which societies will best fit you.");
                new CountDownTimer(6000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        tqInfo.setText("");
                    }

                }.start();



            }

        });


        viewAllSocInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                socInfo.setText("This option allows you to see all societies currently available at MMU.");
                new CountDownTimer(6000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        socInfo.setText("");
                    }

                }.start();

            }

        });

        viewAllRecInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                recInfo.setText("This option allows you to see all recommended societies based on your quiz results.");
                new CountDownTimer(6000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        recInfo.setText("");
                    }

                }.start();

            }

        });
    }

    public void viewRecommendedSocieties(){
        Intent intent = new Intent(this, SocietiesView.class);
        intent.putExtra("user_name", un);
        intent.putExtra("user_pw", pw);
        intent.putExtra( "type", 1);
        startActivity(intent);
    }

    public void viewAllSocieties(){
        Intent intent = new Intent(this, SocietiesView.class);
        intent.putExtra("user_name", un);
        intent.putExtra("user_pw", pw);
        intent.putExtra( "type", 2);
        startActivity(intent);
    }

    public void OpenQuiz(){
        Intent intent = new Intent(this, TakeQuiz.class);
        intent.putExtra("user_name", un);
        intent.putExtra("user_pw", pw);
        startActivity(intent);
    }

    public void logOutComplete() {
        String exitMessage = "You have successfully logged out";
        Toast.makeText(getApplicationContext(), exitMessage, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}