package com.edgarasvilija.spaceshooter.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 30/09/2016.
 */
public class LaserBlast
{
    GameActivity gameActivity = new GameActivity();

    Bitmap laserblastBitmap;
    Bitmap resizedLaserblast;

    private int x;
    private int y = 60;

    private Rect hitBox;


    public LaserBlast (int x, int y)
    {
        laserblastBitmap = BitmapFactory.decodeResource(gameActivity.getContext().getResources(), R.drawable.laserblast);

        this.x = x;
        this.y = y;

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        float root = (float) Math.sqrt(areaSize);

        resizedLaserblast = Bitmap.createScaledBitmap(laserblastBitmap,(int) root, (int) root, true);

        hitBox = new Rect(x, y, resizedLaserblast.getWidth(), resizedLaserblast.getHeight());

    }

    public Bitmap getBitmap()
    {
        return resizedLaserblast;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setY()
    {
        y = -10;
    }

    public void update(float deltaTime)
    {
        y -= 800 * deltaTime;

        // Refresh hit box location
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + resizedLaserblast.getWidth();
        hitBox.bottom = y + resizedLaserblast.getHeight();
    }

    public Rect getHitbox(){
        return hitBox;
    }

}
