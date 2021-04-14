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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button displayAllButton = findViewById(R.id.button3);
        Button displayRecommendedButton = findViewById(R.id.button4);
        Button logOutButton = findViewById(R.id.button5);
        ImageButton takeQuizInfo = findViewById(R.id.imageButton);
        ImageButton viewAllSocInfo = findViewById(R.id.imageButton2);
        ImageButton viewAllRecInfo = findViewById(R.id.imageButton3);
        final TextView tqInfo = findViewById(R.id.textView);
        final TextView socInfo = findViewById(R.id.textView2);
        final TextView recInfo = findViewById(R.id.textView3);
        Intent intent = getIntent();





        //-------------------TO IMPLEMENT IN QUIZ PAGE-------------------//
        LoginFunction sql = new LoginFunction(intent.getStringExtra("user_name"), intent.getStringExtra("user_pw"), this);
        //change this to a hashmap, for use on display page
        String[] userInputtedUN = {intent.getStringExtra("user_name")};
        sql.runAlgorithm(userInputtedUN);
        //---------------------------------------------------------------//



        displayAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------------------------------------------//
                //------------------------------------------------------------------//
                //-------------------TO IMPLEMENT IN DISPLAY PAGE-------------------//
                HashMap<String, String> allSocieties = new HashMap<>();
                sql.getAllSocieties(allSocieties);

                //this part only prints all of the values in the hashmap
                for(String s : allSocieties.keySet()) {
                    System.out.println(s + " " + allSocieties.get(s));
                }
                //------------------------------------------------------------------//
                //------------------------------------------------------------------//
                //------------------------------------------------------------------//
            }
        });


        displayRecommendedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------------------------------------------//
                //------------------------------------------------------------------//
                //-------------------TO IMPLEMENT IN DISPLAY PAGE-------------------//
                HashMap<String, String> userData = new HashMap<>();
                sql.getUserData(userData, userInputtedUN);

                //this part only prints all of the values in the hashmap
                for(String s : userData.keySet()) {
                    System.out.println(s + " " + userData.get(s));
                }
                //------------------------------------------------------------------//
                //------------------------------------------------------------------//
                //------------------------------------------------------------------//
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutComplete();
            }
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
    public void logOutComplete() {
        String exitMessage = "You have successfully logged out";
        Toast.makeText(getApplicationContext(), exitMessage, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}