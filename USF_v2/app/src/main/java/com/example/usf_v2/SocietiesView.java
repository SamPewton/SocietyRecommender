package com.example.usf_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocietiesView extends AppCompatActivity {
    String un = "";
    String pw = "";
    int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societies_view);
        Button MenuButton = findViewById(R.id.ButtonMenuBack);
        TextView soc1 = findViewById(R.id.textView11);
        TextView soc2 = findViewById(R.id.textView12);
        TextView soc3 = findViewById(R.id.textView13);
        TextView soc4 = findViewById(R.id.textView14);
        TextView soc5 = findViewById(R.id.textView15);
        Intent passedIn = getIntent();
        un = passedIn.getStringExtra("user_name");
        pw = passedIn.getStringExtra("user_pw");
        type = passedIn.getIntExtra("type", -1);


        LoginFunction sql = new LoginFunction(un, pw, this);
        HashMap<String, String> allSocieties = new HashMap<>();

        if(type == 1) {
            String[] userInputtedUN = {un};
            sql.getUserData(allSocieties, userInputtedUN);

        }
        else if(type == 2) {

            sql.getAllSocieties(allSocieties);
        }


        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<TextView> socDisplay = new ArrayList<>();
        socDisplay.add(soc1);
        socDisplay.add(soc2);
        socDisplay.add(soc3);
        socDisplay.add(soc4);
        socDisplay.add(soc5);

        //example of how to access hashmap for the data
        //this needs changing from a console print to viewing on the display
        for(String s : allSocieties.keySet()) {
            System.out.println(s + " " + allSocieties.get(s));
            keys.add(s);
            values.add(allSocieties.get(s));
        }

        for(int i = 0; i<socDisplay.size(); i++)
        {
            socDisplay.get(i).setText(keys.get(i) + "\n" + "\n" + values.get(i));
        }



        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuButtonComplete();
            }
        });
    }

    public void MenuButtonComplete() {
        Intent intent = new Intent(this, mainMenu.class);
        intent.putExtra("user_name", un);
        intent.putExtra("user_pw", pw);
        startActivity(intent);
    }



}