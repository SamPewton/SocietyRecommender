package com.example.usf_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userName = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextTextPassword);
        Button loginButton = findViewById(R.id.button);

        LoginFunction sql = new LoginFunction(userName.getText().toString(), password.getText().toString(), this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] userInputtedUN = {userName.getText().toString()};
                //if the user gets both username and password correct
                if (sql.checkLogin(userInputtedUN, password.getText().toString())) {
                    //if logs in ok, move onto the next page
                    System.out.println("Logged in ok");
                }
                //else if the username is not in the database, create account
                //write other else?
                //otherwise the login has failed
                else {
                    //show the login failed and reprompt
                    System.out.println("Failed login attempt");
                }

                }
            });


    }
}