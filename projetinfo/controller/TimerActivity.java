package com.example.xx_laphoune_xx.projetinfo.controller;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.controller.SerieTest2.Test2Activity;

public class TimerActivity extends AppCompatActivity {

    private TextView mTextField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mTextField = findViewById(R.id.textfield);

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("Prochain test dans : " + (millisUntilFinished ) / 1000 +". Préparez vous a trouver l'image suivante :");
            }

            public void onFinish() {
                Intent intento = new Intent(getApplicationContext() , Test2Activity.class);
                startActivity(intento);
                Log.i("TIMER","Onfinish atteint, intent lancé");
            }
        }.start();


    }
}
