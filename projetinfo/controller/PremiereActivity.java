package com.example.xx_laphoune_xx.projetinfo.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaDrm;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;

public class PremiereActivity extends AppCompatActivity  {

    private Button mPlayButton;
    private EditText mNameInput;
    private EditText mSurnameInput;
    private Spinner mAge;
    private CheckBox mCheckboxMale;
    private CheckBox mCheckboxFemale;
    private Button dataButton;

    private Button connexxionbutton;
    private String sexe;
    private DatabaseManager db;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText || v instanceof Button || v instanceof CheckBox || v instanceof Spinner) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premiere);

        //Remplissage du spinner des âges
        ArrayList<String> years = new ArrayList<String>();
        int ageMax = 99;

        for (int i = 18; i <= ageMax; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);


        mSurnameInput = findViewById(R.id.input_surname);
        mNameInput = findViewById(R.id.input_name);

        dataButton = findViewById(R.id.dataButton);
        mPlayButton =  findViewById(R.id.play_button);
        mPlayButton.setEnabled(false);

        connexxionbutton = findViewById(R.id.button2);
        mAge = findViewById(R.id.spinner);

        mCheckboxMale = findViewById(R.id.maleCheckbox);
        mCheckboxFemale = findViewById(R.id.femaleCheckBox);

        db = new DatabaseManager(this);
        mAge.setAdapter(adapter);

        connexxionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(PremiereActivity.this, SendDataActivity.class);
                startActivity(intent3);
            }
        });
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.insertUserInfo(mNameInput.getText().toString(),mSurnameInput.getText().toString(),sexe,
                         mAge.getSelectedItem().toString());
                db.close();

                Intent intent = new Intent(PremiereActivity.this, ReglesActivity.class);
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
                Intent intent2 = new Intent(PremiereActivity.this, dataActivity.class);
                startActivity(intent2);
            }
        });


    }
}

