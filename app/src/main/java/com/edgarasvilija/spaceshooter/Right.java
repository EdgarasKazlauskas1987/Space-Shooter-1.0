package com.edgarasvilija.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Edgaras on 27/09/2016.
 */
public class Right
{

    Bitmap bitmapRight;
    Bitmap resizedRight;

    private int x;
    private int y;

    GameActivity gameActivity = new GameActivity();

    public Right(Context context, int x, int y)
    {

        bitmapRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.right);
        this.x = x;
        this.y = y;

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(3.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedRight = Bitmap.createScaledBitmap(bitmapRight, root, root, true);
    }

    public Bitmap getBitmap()
    {
        return resizedRight;
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
