package com.edgarasvilija.spaceshooter.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 27/09/2016.
 */
public class RightButton
{
    private Bitmap rawRightButton;
    private Bitmap rightButton;

    private int xCoordinate;
    private int yCoordinate;

    GameActivity gameActivity = new GameActivity();

    public RightButton(Context context, int xCoordinate, int yCoordinate)
    {
        rawRightButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_right_button);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //counting 3% of all screen area
        int areaSize = (int)(gameActivity.getArea()*(3.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        rightButton = Bitmap.createScaledBitmap(rawRightButton, root, root, true);
    }

    public Bitmap getRightButton()
    {
        return rightButton;
    }
}
