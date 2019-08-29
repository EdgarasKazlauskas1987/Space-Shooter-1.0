package com.edgarasvilija.spaceshooter.Model.Abstract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

public abstract class AbstractButton
{
    protected int xCoordinate;
    protected int yCoordinate;

    protected Bitmap button;

    protected GameActivity gameActivity = new GameActivity();

    public AbstractButton(Context context, int xCoordinate, int yCoordinate, int drawableId)
    {
        Bitmap rawButton = BitmapFactory.decodeResource(context.getResources(), drawableId);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //counting 3% of all screen area
        int areaSize = (int)(gameActivity.getArea()*(3.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        button = Bitmap.createScaledBitmap(rawButton, root, root, true);
    }

    public Bitmap getButton()
    {
        return button;
    }
}
