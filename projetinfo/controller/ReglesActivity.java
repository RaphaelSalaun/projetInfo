package com.example.xx_laphoune_xx.projetinfo.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.controller.SerieTest1.Test1Activity;

public class ReglesActivity extends AppCompatActivity {

    private Button mLaunchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regles);


        mLaunchButton = findViewById(R.id.appuyez_pour_jouer);

        mLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReglesActivity.this,Test1Activity.class);
                startActivity(i);
            }
        });
    }
}
