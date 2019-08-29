package com.edgarasvilija.spaceshooter.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 30/09/2016.
 */
public class RedLaser
{
    GameActivity gameActivity = new GameActivity();

    private Bitmap rawRedLaser;
    private Bitmap redLaser;

    private int xCoordinate;
    private int yCoordinate = 60;

    private Rect hitBox;

    public RedLaser(int xCoordinate, int yCoordinate)
    {
        rawRedLaser = BitmapFactory.decodeResource(gameActivity.getContext().getResources(), R.drawable.img_red_laser);

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //counting 3% of all screen area
        int areaSize = (int)(gameActivity.getArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        float root = (float) Math.sqrt(areaSize);

        redLaser = Bitmap.createScaledBitmap(rawRedLaser,(int) root, (int) root, true);

        hitBox = new Rect(xCoordinate, yCoordinate, redLaser.getWidth(), redLaser.getHeight());
    }

    public Bitmap getRedLaser()
    {
        return redLaser;
    }

    public int getXCoordinate()
    {
        return xCoordinate;
    }

    public int getYCoordinate()
    {
        return yCoordinate;
    }

    public void setYCoordinate()
    {
        yCoordinate = -10;
    }

    public void update(float deltaTime)
    {
        yCoordinate -= 800 * deltaTime;

        // Refresh hit box location
        hitBox.left = xCoordinate;
        hitBox.top = yCoordinate;
        hitBox.right = xCoordinate + redLaser.getWidth();
        hitBox.bottom = yCoordinate + redLaser.getHeight();
    }

    public Rect getHitbox(){
        return hitBox;
    }
}
