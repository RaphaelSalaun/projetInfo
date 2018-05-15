package com.example.xx_laphoune_xx.projetinfo.view;

/**
 * Created by Xx_LaPhoune_xX on 27/04/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.controller.GameActivity;
import com.example.xx_laphoune_xx.projetinfo.controller.Main3Activity;
import com.example.xx_laphoune_xx.projetinfo.controller.MainActivity;
import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import static java.lang.Math.pow;

public class GameView2 extends View {
    private View myView;

    private Bitmap mCible;
    private Bitmap mEcureil;

    private ArrayList<Bitmap> mBitmapList;

    private int mHeight;
    private int mWidth;
    private int x;
    private int y;

    private int cibleIndex;
    private float Xcible;
    private float Ycible;

    private long touchTime;
    private long startTime;
    private float eventX;
    private float eventY;
    private float mPrecision;
    private DatabaseManager db;

    private Intent mIntent = new Intent(this.getContext(), MainActivity.class);

    public GameView2(Context context) {
        super(context);
        init(null);
    }

    public GameView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public GameView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GameView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }



    private void init(@Nullable AttributeSet set) {
        mEcureil = (BitmapFactory.decodeResource(getResources(), R.drawable.ecureil));
        mCible = BitmapFactory.decodeResource(getResources(), R.drawable.cible2);
        myView = findViewById(R.id.GameView2);

        db = new DatabaseManager(getContext());

        mBitmapList = new ArrayList<>();

        mBitmapList.add(mCible);
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.chemineex2));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.chemineex2));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.ballonx1));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.croissant1));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.igloo));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.clochex1));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.lapin));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.montgolfiere3));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.montgolfiere3));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.montgolfiere3));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.maison2));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.maison2));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.moto));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.moulin));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.puit));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.sel4));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.sel4));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.sel4));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.table));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.television));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.tortue));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.totem));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.valise2));
        mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.valise2));

        for (int aj = 0; aj < 4; aj++) {
            mBitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.gateau4));
        }
        for (int am = 0; am < 4; am++) {
            mBitmapList.add((BitmapFactory.decodeResource(getResources(),R.drawable.clown5)));

        }
        for (int az = 0; az < 5; az++) {
            mBitmapList.add((BitmapFactory.decodeResource(getResources(),R.drawable.fromage5)));

        }
        for (int r = 0; r < 6; r++) {
            mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.tonneau7));
        }
        for (int l = 0; l < 6; l++) {
            mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.couronnex7));
        }
        Collections.shuffle(mBitmapList);
        cibleIndex = mBitmapList.indexOf(mCible);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        myView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mHeight = myView.getHeight();
        mWidth = myView.getWidth();

        x = mWidth / 5;
        y = mHeight / 10;

        mCible = Bitmap.createScaledBitmap(mCible, x, y, false);
        mEcureil = Bitmap.createScaledBitmap(mEcureil, x, y, false);

        for (int f = 0; f < 50; f++) {
            if (mBitmapList.get(f) != null) {
                canvas.drawBitmap(Bitmap.createScaledBitmap(mBitmapList.get(f), x, y, false), x * (f % 5), y * (f / 5), null);
            }
        }
        startTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        eventX = event.getX();
        eventY = event.getY();
        Xcible = x * (cibleIndex % 5) + mCible.getWidth() / 2;
        Ycible = y * (cibleIndex / 5) + mCible.getHeight() / 2;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrecision = (float) pow(pow((Xcible - eventX), 2) + pow((Ycible - eventY), 2), 0.5);

                touchTime = System.currentTimeMillis();
        //        Toast.makeText(getContext(),cibleIndex + "   size + " + String.valueOf((touchTime - startTime)) + " est le temps mis, index est " + String.valueOf(cibleIndex) + " precision" + String.valueOf(mPrecision), Toast.LENGTH_LONG).show();
                db.insertUserScore(mPrecision, touchTime - startTime, event.getPressure(), 2);
                db.close();
                getContext().startActivity(mIntent);

                break;

            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }
}
