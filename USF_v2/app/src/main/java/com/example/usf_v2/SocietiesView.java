package com.example.usf_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SocietiesView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_societies_view);
        Button MenuButton = findViewById(R.id.ButtonMenuBack);


        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuButtonComplete();
            }
        });
    }

    public void MenuButtonComplete() {
        Intent intent = new Intent(this, mainMenu.class);
        startActivity(intent);
    }
}