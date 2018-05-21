package com.example.xx_laphoune_xx.projetinfo.model;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "donnees.db";
    public static final int DATABASE_VERSION = 8;

    public DatabaseManager(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    // Methode pour créer la base de donnée sqlite
    @Override
    public void onCreate(SQLiteDatabase db) {
        String creationBDD = "create table userInfo ("
                + "idUser integer primary key autoincrement,"
                + " name text,"
                + " surname text,"
                + " sex text,"
                + " age text "
                + ")";
        String sql2 = "create table userScores ("
                + "userID integer, "
                + "idScore integer primary key  autoincrement, "
                + "time real, "
                + "pressure real, "
                + "precision real, "
                + "tryNumber integer default 0, "
                + "FOREIGN KEY (userID) REFERENCES userInfo(idUser)"

                + ");";
        db.execSQL(creationBDD);
        db.execSQL(sql2);
        Log.i("DATABASE", "on create invoked");
    }

    // Méthode pour mettre a jour la base de donnée si le numéro de version est modifié (si le schéma est modifié)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String strSql = "drop table if exists userInfo  ";
        db.execSQL(strSql);
        String sql2 = "drop table if exists userScores";
        db.execSQL(sql2);
        this.onCreate(db);
        Log.i("DATABASE", "on upgrade invoked");

    }
// Méthode utilisée pour inserer les infos de l'utilisateur recupérées lors de la première activité
    public void insertUserInfo(String nom, String prenom, String sexe, String age) {
        nom = nom.replace(" ' "," ''");
        String sqlInsertion = "insert into userInfo (name, surname, sex, age) values ('"
                + nom + "', '" + prenom + "','" + sexe + "','" + age + "')";
        this.getWritableDatabase().execSQL(sqlInsertion);
    }

// Méthode utilisée pour inserer les infos recupérées lors des test
    public void insertUserScore(float precision, float time, float pressure, int tryNumber) {
        String sqlcode = "insert into userScores (userID, precision,  time, pressure, tryNumber) values ('"
                + getLastId() + "','" + precision + "','"  +  time  + "','" + pressure + "','" + tryNumber + "')";
        this.getWritableDatabase().execSQL(sqlcode);
    }


    public int getLastId() {
        String sql = " select\n" +
                "    \"idUser\"\n" +
                "\n" +
                "from\n" +
                "    \"userInfo\"\n" +
                "order by \"idUser\"\n" +
                "desc limit 1";
        Cursor curseur = this.getWritableDatabase().rawQuery(sql, null);
        curseur.moveToFirst();
        int lastId = curseur.getInt(0);
        curseur.close();
        return lastId;
    }


    // Methode pour recuperer la liste de tous les users et leurs résultats
    public List<User> getUserScores() {
        List<User> scores = new ArrayList<>();

        String sql = "select distinct i.name, i.surname,  i.age, i.sex, s.precision, s.time, s.pressure, s.tryNumber " +
                     "from userInfo i, userScores s "  +
                     "where i.idUser = s.userID " +
                     "order by i.idUser desc  ";
        Cursor cursor  = this.getWritableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();

        // On recupere les infos
        while (! cursor.isAfterLast()) {
            User newUser = new User(cursor.getString(0),cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getInt(4),
                    cursor.getFloat(5), cursor.getFloat(6), cursor.getInt(7));
            scores.add(newUser);
            cursor.moveToNext();
        }
        cursor.close();
        return scores;
    }

}
