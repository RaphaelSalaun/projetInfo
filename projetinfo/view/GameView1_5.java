package com.example.xx_laphoune_xx.projetinfo.view;

/**
 * Created by Xx_LaPhoune_xX on 22/05/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.controller.SerieTest1.Test1_6Activity;
import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;

import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Math.pow;

public class GameView1_5 extends View {
    private View myView;

    private Bitmap mCible;
    private Bitmap mEcureil;

    private Bitmap mEcureilretourne;

    private ArrayList<Bitmap> mBitmapList;

    private int mHeight;
    private int mWidth;
    private int x;
    private int y;
    private String correctness;

    private int cibleIndex;
    private float Xcible;
    private float Ycible;

    private long touchTime;
    private long startTime;
    private float eventX;
    private float eventY;
    private float mPrecision;
    private DatabaseManager db;

    private Intent mIntent = new Intent(this.getContext(), Test1_6Activity.class);

    public GameView1_5(Context context) {
        super(context);
        init(null);
    }
    public GameView1_5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }
    public GameView1_5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    public GameView1_5(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        // différentes images utilisées
        mEcureil = (BitmapFactory.decodeResource(getResources(), R.drawable.ecureil));
        mCible = BitmapFactory.decodeResource(getResources(), R.drawable.lapinretourne);
        mEcureilretourne = (BitmapFactory.decodeResource(getResources(), R.drawable.ecureilretourne));

        myView = findViewById(R.id.GameView1_5);
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
            correctness = "bon";
        } else {
            correctness = "faux";
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                mPrecision = (float) pow(pow((Xcible - eventX), 2) + pow((Ycible - eventY), 2), 0.5);

                touchTime = System.currentTimeMillis();

                // on insere les infos recoltées
                db.insertUserScore(mPrecision,touchTime-startTime, event.getPressure(),1);
                db.close();
                for(int e=0; e<50; e++) {
                    if (mBitmapList.get(e) != null) {
                        mBitmapList.get(e).recycle();
                    }
                }
                // lancement de l'intent pour retourner au debut de l'application
                getContext().startActivity(mIntent);
                break;
        }
        return true;
    }
}
