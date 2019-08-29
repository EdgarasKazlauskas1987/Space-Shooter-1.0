package com.edgarasvilija.spaceshooter.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 13/11/2016.
 */
public class BlueLaser
{
    private Bitmap rawBlueLaser;
    private Bitmap blueLaser;

    private int xCoordinate;
    private int yCoordinate;

    private Rect enemyShipLaserBlastRect;

    GameActivity gameActivity;

    public BlueLaser(GameActivity ga, int xCoordinate, int yCoordinate)
    {
        gameActivity = ga;
        rawBlueLaser = BitmapFactory.decodeResource(gameActivity.getContext().getResources(), R.drawable.img_blue_laser);

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //counting 3% of all screen area
        int areaSize = (int)(gameActivity.getArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        float root = (float) Math.sqrt(areaSize);

        blueLaser = Bitmap.createScaledBitmap(rawBlueLaser,(int) root, (int) root, true);
        enemyShipLaserBlastRect = new Rect(xCoordinate, yCoordinate, blueLaser.getWidth(), blueLaser.getHeight());
    }

    public Bitmap getBlueLaser()
    {
        return blueLaser;
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
        yCoordinate = 5000;
    }

    public void update(float deltaTime)
    {
        yCoordinate += 600 * deltaTime;

        // Refresh hit box location
        enemyShipLaserBlastRect.left = xCoordinate;
        enemyShipLaserBlastRect.top = yCoordinate;
        enemyShipLaserBlastRect.right = xCoordinate + blueLaser.getWidth();
        enemyShipLaserBlastRect.bottom = yCoordinate + blueLaser.getHeight();
    }

    public Rect getEnemyShipLaserBlastRect()
    {
        return enemyShipLaserBlastRect;
    }
}
