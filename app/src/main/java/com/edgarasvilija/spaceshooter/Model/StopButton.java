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
    private Bitmap rawStopButton;
    private Bitmap stopButton;

    private int xCoordinate;
    private int yCoordinate;

    GameActivity gameActivity = new GameActivity();

    public StopButton(Context context, int xCoordinate, int yCoordinate)
    {
        rawStopButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_stop_button);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //counting 3% of all screen area
        int areaSize = (int)(gameActivity.getArea()*(1.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        stopButton = Bitmap.createScaledBitmap(rawStopButton, root, root, true);
    }

    public Bitmap getStopButton()
    {
        return stopButton;
    }
}
