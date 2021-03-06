package com.example.xx_laphoune_xx.projetinfo.view;
/**
 * Created by Xx_LaPhoune_xX on 07/04/2018.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.controller.SerieTest1.Test1_1Activity;
import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.pow;

public class GameView extends View {

    SharedPreferences sharedpreferences;

    private View myView;

    private Bitmap mCible;
    private Bitmap mEcureil;

    private Bitmap mEcureilretourne;

    private ArrayList<Bitmap> mBitmapList;

    private int mHeight;
    private int mWidth;
    private int x;
    private int y;
    private int correctness;
    private String userinfos;
    private String nom_fichier;
    private String donnees;

    private int cibleIndex;
    private float Xcible;
    private float Ycible;

    private long touchTime;
    private long startTime;
    private float eventX;
    private float eventY;
    private float mPrecision;
    private DatabaseManager db;

    private Intent mIntent = new Intent(this.getContext(), Test1_1Activity.class);

    public GameView(Context context) {
        super(context);
        init(null);
    }
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        // différentes images utilisées
        mEcureil = (BitmapFactory.decodeResource(getResources(), R.drawable.ecureil));
        mCible = BitmapFactory.decodeResource(getResources(), R.drawable.lapinretourne);
        mEcureilretourne = (BitmapFactory.decodeResource(getResources(), R.drawable.ecureilretourne));
        sharedpreferences = getContext().getSharedPreferences("preferences",MODE_PRIVATE);



        myView = findViewById(R.id.GameView);
// ouverture de notre base de données
        db = new DatabaseManager(getContext());
// liste de nos images qu'on va peupler
        mBitmapList = new ArrayList<>();

        // début  phase de peuplage
        mBitmapList.add(mCible);

        for (int k = 0; k < 16; k++) {
            mBitmapList.add(mEcureil);
        }
        for (int k = 0; k < 16; k++) {
            mBitmapList.add(mEcureilretourne);
        }
        for (int l = 0; l < 17; l++) {
            mBitmapList.add(null);
        }
        // fin  phase de peuplage

        // mélange des images
        Collections.shuffle(mBitmapList);
        cibleIndex = mBitmapList.indexOf(mCible);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //on recupère la taille de notre écran
        myView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mHeight = myView.getHeight();
        mWidth = myView.getWidth();

        x = mWidth/5;
        y = mHeight/10;


        // on dessine nos images mises a l'échelle
        for(int f=0; f<50; f++) {
            if (mBitmapList.get(f) != null) {
                canvas.drawBitmap(Bitmap.createScaledBitmap(mBitmapList.get(f), x, y, true), x*(f%5) , y * (f/5), null);
            }
        }
        // on prend le temps du début du test
        startTime = System.currentTimeMillis();
    }
    @Override
    public String toString() {
        return super.toString();
    }

    // méthode pour détecter le toucher de l'utilisateur
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // prise des coordonnées du toucher
        eventX = event.getX();
        eventY = event.getY();
        Xcible = x*(cibleIndex%5) + mCible.getWidth()/2 ;
        Ycible = y*(cibleIndex/5) + mCible.getHeight()/2;

        if(Math.abs((eventX-Xcible)) <= 50 && Math.abs((eventY-Ycible)) <= 50 ) {
            correctness = 1;
        } else {
            correctness = 0;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:


                // Toast.makeText(getContext(), userinfos, Toast.LENGTH_SHORT).show();


                mPrecision = (float) pow(pow((Xcible - eventX), 2) + pow((Ycible - eventY), 2), 0.5);

                touchTime = System.currentTimeMillis();
                long delai =  touchTime-startTime;
                // on insere les infos recoltées
                db.insertUserScore(mPrecision,touchTime-startTime, event.getPressure(),1);
                db.close();
                mEcureil.recycle();
                for(int e=0; e<50; e++) {
                    if (mBitmapList.get(e) != null) {
                        mBitmapList.get(e).recycle();
                    }
                }

                userinfos = sharedpreferences.getString("Nom",null) + " " +
                    sharedpreferences.getString("Prenom",null) + " " +
                    sharedpreferences.getString("Age",null) + " " +
                    sharedpreferences.getString("Sexe",null);

                donnees = "(" + Xcible + "     ," + Ycible + "     ," + Xcible + " + 50,     " + Ycible + " 50)     "
                        + correctness + "     ,"
                         + mPrecision + "     ," + delai + "     ,1";


                // Toast.makeText(getContext(), donnees, Toast.LENGTH_SHORT).show();

                String arr[] = userinfos.split(" ", 4);
                nom_fichier = arr[0].toUpperCase()+"_"+arr[1].toLowerCase()+"_"+arr[2]+"_"+arr[3]+".txt";
                BufferedWriter output = null;
                Log.i("GNEUNEU", " juste avant try");
                try {
                    String path = getContext().getFilesDir().getAbsolutePath();
                            //getContext().getExternalFilesDir(nom_fichier).getAbsolutePath(); ? might work
                    output = new BufferedWriter(new FileWriter(new File(path,nom_fichier),true));
                    output.append(donnees);
                    output.newLine();
                    output.close();
                    Toast.makeText(getContext(), "fichier créé" + path, Toast.LENGTH_LONG).show();
                    Log.i("GNEUNEU", " fichier créé");


                } catch (IOException e) {
                    e.printStackTrace();
                }

                // lancement de l'intent pour retourner au debut de l'application
                getContext().startActivity(mIntent);
                break;
        }
        return true;
    }
}