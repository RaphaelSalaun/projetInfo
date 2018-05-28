package com.example.xx_laphoune_xx.projetinfo.controller.SerieTest1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.xx_laphoune_xx.projetinfo.R;

public class Test1_1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_1);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

    }

    @Override
    public void onBackPressed() {

    }
}
