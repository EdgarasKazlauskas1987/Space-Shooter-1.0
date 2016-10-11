package com.edgarasvilija.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by Edgaras on 27/09/2016.
 */
public class Target
{
    private Bitmap bitmapTarget;
    public Bitmap resizedTarget;

    private int x;
    private int y;

    GameActivity gameActivity = new GameActivity();

    public Target(Context context, int x, int y)
    {

        bitmapTarget = BitmapFactory.decodeResource(context.getResources(), R.drawable.target);
        this.x = x;
        this.y = y;

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(3.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedTarget = Bitmap.createScaledBitmap(bitmapTarget, root, root, true);

    }

    public Target()
    {

    }

    public Bitmap getBitmap()
    {
        return resizedTarget;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }


}
