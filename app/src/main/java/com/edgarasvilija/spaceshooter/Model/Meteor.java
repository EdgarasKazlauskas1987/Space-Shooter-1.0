package com.edgarasvilija.spaceshooter.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

import java.util.Random;

/**
 * Created by Edgaras on 07/11/2016.
 */
public class Meteor
{
    private Bitmap rawMeteor;
    private Bitmap meteor;

    private int xCoordinate;
    private int yCoordinate;

    private Rect hitBox;

    GameActivity gameActivity = new GameActivity();

    Random generator = new Random();

    public Meteor(Context context, int xCoordinate, int yCoordinate)
    {
        rawMeteor = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_meteor);
        //counting 3% of all screen area
        int areaSize = (int)(gameActivity.getArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        meteor = Bitmap.createScaledBitmap(rawMeteor, root, root, true);

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        hitBox = new Rect(getXCoordinate(), getYCoordinate(), meteor.getWidth(), meteor.getHeight());
    }

    public void update(float deltaTime)
    {
        if (yCoordinate > gameActivity.getYpart())
        {
            //makes meteor appear only within screen
            xCoordinate = generator.nextInt(gameActivity.getXpart() - getRawMeteor().getWidth());
            yCoordinate = 0;
        } else {
            yCoordinate += 3 * (gameActivity.getYpart() / 15) * deltaTime;
        }

        // Refresh hit box location
        hitBox.left = getXCoordinate();
        hitBox.top =  getYCoordinate();
        hitBox.right = getXCoordinate() + meteor.getWidth();
        hitBox.bottom = getYCoordinate() + meteor.getHeight();
    }

    public Bitmap getRawMeteor()
    {
        return meteor;
    }

    public int getXCoordinate()
    {
        return xCoordinate;
    }

    public int getYCoordinate()
    {
        return yCoordinate;
    }

    public void setYCoorinate()
    {
        yCoordinate = -10;
    }

    public Rect getHitBox()
    {
        return hitBox;
    }
}
