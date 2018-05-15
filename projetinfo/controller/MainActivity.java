package com.example.xx_laphoune_xx.projetinfo.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.CheckBox;
import android.widget.Spinner;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.xx_laphoune_xx.projetinfo.R;

import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;
import com.example.xx_laphoune_xx.projetinfo.model.User;

public class MainActivity extends AppCompatActivity  {

    private Button mPlayButton;
    private EditText mNameInput;
    private EditText mSurnameInput;
    private Spinner mAge;
    private CheckBox mCheckboxMale;
    private CheckBox mCheckboxFemale;
    private Button dataButton;
    private String sexe;
    private DatabaseManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remplissage du spinner des âges
        ArrayList<String> years = new ArrayList<String>();
        int ageMax = 99;

        for (int i = 18; i <= ageMax; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        db = new DatabaseManager(this);


        mSurnameInput = findViewById(R.id.input_surname);
        mNameInput = findViewById(R.id.input_name);

        dataButton = findViewById(R.id.dataButton);
        mPlayButton =  findViewById(R.id.play_button);
        mPlayButton.setEnabled(false);

        mAge = findViewById(R.id.spinner);
        mAge.setAdapter(adapter);

        mCheckboxMale = findViewById(R.id.maleCheckbox);

        mCheckboxFemale = findViewById(R.id.femaleCheckBox);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.insertUserInfo(mNameInput.getText().toString(),mSurnameInput.getText().toString(),sexe,
                         mAge.getSelectedItem().toString());
                db.close();

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        mCheckboxMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckboxMale.setChecked(true);
                mCheckboxFemale.setChecked(false);
                sexe = "Masculin";
            }
        });

        mCheckboxFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckboxMale.setChecked(false);
                mCheckboxFemale.setChecked(true);
                sexe ="Femininn";
            }
        });

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This is where we'll check the user input
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, dataActivity.class);
                startActivity(intent2);
            }
        });


    }
}

