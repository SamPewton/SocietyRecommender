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

public class mainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button logOutButton = findViewById(R.id.button5);
        ImageButton takeQuizInfo = findViewById(R.id.imageButton);
        ImageButton viewAllSocInfo = findViewById(R.id.imageButton2);
        ImageButton viewAllRecInfo = findViewById(R.id.imageButton3);
        final TextView tqInfo = findViewById(R.id.textView);
        final TextView socInfo = findViewById(R.id.textView2);
        final TextView recInfo = findViewById(R.id.textView3);

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