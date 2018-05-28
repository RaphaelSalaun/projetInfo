package com.example.xx_laphoune_xx.projetinfo.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.File;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;

public class PremiereActivity extends AppCompatActivity  {

    SharedPreferences sharedpreferences;
    File filedir;
    File filename;
    File internalDirectory;
    public String userInfos;
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

    // Méthode permettant de dissimuler le clavier si on touche ailleur que dans une zone a remplir
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // on detecte le toucher
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // on prend la vue selectionnée
            View v = getCurrentFocus();

            // si cette dernière n'est pas a remplir on hide le clavier
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

        sharedpreferences = this.getSharedPreferences("preferences",MODE_PRIVATE);

        internalDirectory = getDir("UserData",0);

        //Remplissage du spinner des âges
        ArrayList<String> years = new ArrayList<String>();
        int ageMax = 99;

        for (int i = 7; i <= ageMax; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        // On reference les différents widgets utilisés
        mSurnameInput = findViewById(R.id.input_surname);
        mNameInput = findViewById(R.id.input_name);

        dataButton = findViewById(R.id.dataButton);
        mPlayButton =  findViewById(R.id.play_button);
        mPlayButton.setEnabled(false);

        connexxionbutton = findViewById(R.id.button2);
        mAge = findViewById(R.id.spinner);

        mCheckboxMale = findViewById(R.id.maleCheckbox);
        mCheckboxFemale = findViewById(R.id.femaleCheckBox);

        // Ouverture de notre BDD
        db = new DatabaseManager(this);
        mAge.setAdapter(adapter);

        // Lancé de l'activité pour connexion socket si on click sur le bouton
        connexxionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(PremiereActivity.this, SendDataActivity.class);
                startActivity(intent3);
            }
        });

        // Si on clique pour jouer, on insère les données utilisateur, ferme la BDD puis change d'activité
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// On met les données de l'utilisateur en mémoire pour qu'il n'ait pas a les remplir a nouveau s'il rejoue
                SharedPreferences.Editor editeur = sharedpreferences.edit();

                editeur.putString("Nom",mNameInput.getText().toString());
                editeur.putString("Prenom",mSurnameInput.getText().toString());
                editeur.putString("Age",mAge.getSelectedItem().toString());
                editeur.putString("Sexe",sexe);
                editeur.apply();

                filename = new File(internalDirectory, sharedpreferences.getString("Nom", null) + "_" +
                        sharedpreferences.getString("Prenom", null));


                // On insere dans la bdd les données
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
                sexe = "M";
            }
        });

        mCheckboxFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckboxMale.setChecked(false);
                mCheckboxFemale.setChecked(true);
                sexe ="F";
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

        if(sharedpreferences.getString("Nom",null) != null) {
            mNameInput.setText(sharedpreferences.getString("Nom", null));
        }
        if(sharedpreferences.getString("Prenom",null) != null) {
            mSurnameInput.setText(sharedpreferences.getString("Prenom", null));
        }
        if(sharedpreferences.getString("Age", null) != null) {
            mAge.setSelection(Integer.parseInt(sharedpreferences.getString("Age", null))- 7);
         //   mAge.setSelection(getIndex(mAge,(sharedpreferences.getInt("Age", 0) )));
        }
        if(sharedpreferences.getString("Sexe",null) != null) {
            if(sharedpreferences.getString("Sexe",null) == "Masculin") {
                mCheckboxMale.setChecked(true);
            } else {
                mCheckboxFemale.setChecked(true);
            }
        }
    }

    //pour avoir l'index de la valeur dans les sharedpreferences
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

}

