package com.edgarasvilija.spaceshooter.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 27/09/2016.
 */
public class TargetButton
{
    private Bitmap bitmapTarget;
    public Bitmap resizedTarget;

    private int x;
    private int y;

    GameActivity gameActivity = new GameActivity();

    public TargetButton(Context context, int x, int y)
    {

        bitmapTarget = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_target);
        this.x = x;
        this.y = y;

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(3.0f/100.0f));

        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedTarget = Bitmap.createScaledBitmap(bitmapTarget, root, root, true);

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
