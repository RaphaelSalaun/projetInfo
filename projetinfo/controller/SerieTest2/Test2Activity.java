package com.example.xx_laphoune_xx.projetinfo.controller.SerieTest2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xx_laphoune_xx.projetinfo.R;

public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
    }
}
