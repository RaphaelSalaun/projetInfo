package com.example.xx_laphoune_xx.projetinfo.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;
import com.example.xx_laphoune_xx.projetinfo.model.User;

import java.util.List;

// Activité où on accède aux scores renseignés dans la base de donnée

public class dataActivity extends AppCompatActivity {

    private TextView scoreView;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        scoreView = findViewById(R.id.scoreView);

        // On ouvre notre BDD specifique a l'application
        db = new DatabaseManager(this);

        // On recupère la liste de tous les scores
        List<User> userScores = db.getUserScores();

       // On affiche tous les scores
        for (User user : userScores) {
            scoreView.append(user.toString() + "\n\n");
        }


        // On ferme la BDD pour eviter une fuite de mémoire
        db.close();
    }
}
