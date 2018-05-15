package com.example.xx_laphoune_xx.projetinfo.controller;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import com.example.xx_laphoune_xx.projetinfo.R;

public class Main3Activity extends AppCompatActivity {

    private TextView mTextField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mTextField = findViewById(R.id.textfield);

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("Prochain test dans : " + millisUntilFinished / 1000 +". Préparez vous a trouver l'image suivante :");
            }

            public void onFinish() {
                Intent intent = new Intent(Main3Activity.this,Test2Activity.class);
                startActivity(intent);
            }
        }.start();

    }
}
