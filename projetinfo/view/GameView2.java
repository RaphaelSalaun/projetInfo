package com.example.xx_laphoune_xx.projetinfo.view;

/**
 * Created by Xx_LaPhoune_xX on 27/04/2018.
 *  Custom view qui est l'interface graphique des test
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;

import com.example.xx_laphoune_xx.projetinfo.R;
import com.example.xx_laphoune_xx.projetinfo.controller.PremiereActivity;
import com.example.xx_laphoune_xx.projetinfo.model.DatabaseManager;

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

    private int maxsize;

    private Intent mIntent; // = new Intent(this.getContext(), PremiereActivity.class);

    // 4 méthodes constructeur selon les différents arguments renseignés (selon les versions d'android)
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


// Méthode ou on initialise toutes les valeurs a utiliser
    private void init(@Nullable AttributeSet set) {

        mEcureil = (BitmapFactory.decodeResource(getResources(), R.drawable.ecureil));
        mCible = BitmapFactory.decodeResource(getResources(), R.drawable.cible2);
        myView = findViewById(R.id.GameView2);
        mIntent = new Intent(this.getContext(), PremiereActivity.class);
        db = new DatabaseManager(getContext());

        mBitmapList = new ArrayList<>();
// Remplisage de la liste avec les images fournies

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
            mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.retonneau));
        }
        for (int l = 0; l < 6; l++) {
            mBitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.couronnex7));
        }
        // Shuffle = on réordonne aléatoirement la liste pour que l'image cible soit a une place différente a chaque fois
        Collections.shuffle(mBitmapList);
        cibleIndex = mBitmapList.indexOf(mCible);

    }
    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        myView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mHeight = myView.getHeight();
        mWidth = myView.getWidth();

        x = mWidth / 5;
        y = mHeight / 10;

        if(x>y) {
            maxsize = y;

        } else {
            maxsize = x;
        }

// Mise a l'échelle voulue des images
       /* mCible = Bitmap.createScaledBitmap(mCible, x, y, true);
        mEcureil = Bitmap.createScaledBitmap(mEcureil, x, y, true);*/

        for (int f = 0; f < 50; f++) {
            if (mBitmapList.get(f) != null) {
                // On dessine les images sur le canvas
                canvas.drawBitmap(getResizedBitmap(mBitmapList.get(f), maxsize), x * (f % 5), y * (f / 5), null);
           // canvas.drawBitmap(scaleBitmap(mBitmapList.get(f),x,y), x * (f % 5), y * (f / 5), null);
            }
        }
        // On recupere le temps de début
        startTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return super.toString();
    }


//Méthode appelée a chaque toucher
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // On prend les positions du toucher apposé + position de la cible
        eventX = event.getX();
        eventY = event.getY();
        Xcible = x * (cibleIndex % 5) + mCible.getWidth() / 2;
        Ycible = y * (cibleIndex / 5) + mCible.getHeight() / 2;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // on calcule la distance entre le toucher de l'utilisateur et le centre de la cible
                mPrecision = (float) pow(pow((Xcible - eventX), 2) + pow((Ycible - eventY), 2), 0.5);
                // On prend la date du toucher
                touchTime = System.currentTimeMillis();

                // On insere les valeurs dans notre base de données
                db.insertUserScore(mPrecision, touchTime - startTime, event.getPressure(), 2);
                db.close();

                // On lance l'intent pour retourner a la première vue
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
