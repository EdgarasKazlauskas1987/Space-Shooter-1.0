package com.edgarasvilija.spaceshooter.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 01/12/2016.
 */
public class StopButton
{
    Bitmap stopButton;
    Bitmap resizedStopButton;

    private int x;
    private int y;

    GameActivity gameActivity = new GameActivity();

    public StopButton(Context context, int x, int y)
    {
        stopButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_stop_button);
        this.x = x;
        this.y = y;

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(1.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedStopButton = Bitmap.createScaledBitmap(stopButton, root, root, true);
    }

    public Bitmap getBitmap()
    {
        return resizedStopButton;
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
