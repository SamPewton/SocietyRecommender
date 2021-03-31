package com.example.usf_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    List<String> userData = new ArrayList<>();
    String loggedInUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userName = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextTextPassword);
        Button loginButton = findViewById(R.id.button);

        LoginFunction sql = new LoginFunction(userName.getText().toString(), password.getText().toString(), this);

        //list for storing all of the users recommended societies


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] userInputtedUN = {userName.getText().toString()};
                String displayName = userName.getText().toString();
                //if the user gets both username and password correct
                if (sql.checkLogin(userInputtedUN, password.getText().toString())) {
                    //sets the global loggedInUser variable to the name of the user
                    loggedInUser = userName.getText().toString();
                    //gets all of the user associated data and stores it in global userData variable
                    sql.getUserData(userData, userInputtedUN);


                    //!!if logs in ok, move onto the next page!!//
                    System.out.println("Logged in ok");
                    String welcome = "Welcome " + displayName;
                    Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                    loginComplete();
                }
                //else if the username is not in the database, create account
                //write other else?
                //otherwise the login has failed
                else {
                    //show the login failed and reprompt
                    System.out.println("Failed login attempt");
                    String wrongPass = "You have entered the wrong user or password";
                    Toast.makeText(getApplicationContext(), wrongPass, Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public void loginComplete()
    {
        Intent intent = new Intent(this, mainMenu.class);
        startActivity(intent);
    }
}