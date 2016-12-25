package com.edgarasvilija.spaceshooter.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 27/09/2016.
 */
public class LeftButton
{
    private Bitmap bitmapLeft;
    private Bitmap resizedLeft;

    private int x;
    private int y;

    GameActivity gameActivity = new GameActivity();

    public LeftButton(Context context, int x, int y)
    {

        bitmapLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.left);
        this.x = x;
        this.y = y;

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(3.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedLeft = Bitmap.createScaledBitmap(bitmapLeft, root, root, true);

    }

    public Bitmap getBitmap()
    {
        return resizedLeft;
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
