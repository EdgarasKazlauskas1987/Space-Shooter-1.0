package com.edgarasvilija.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Edgaras on 30/09/2016.
 */
public class LaserBlast
{
    Bitmap laserblastBitmap;
    Bitmap resizedLaserblast;

    private int x;
    private int y = 60;

    private Rect hitBox;


    GameActivity gameActivity = new GameActivity();



    public LaserBlast (int x, int y)
    {
        laserblastBitmap = BitmapFactory.decodeResource(gameActivity.getContext().getResources(), R.drawable.laserblast);
       // x = gameActivity.getXpart();
        //y = gameActivity.getYpart();
        this.x = x;
        this.y = y;

        resizedLaserblast = Bitmap.createScaledBitmap(laserblastBitmap, 30, 30, true);

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

    public void update(float deltaTime)
    {
        y -= 35 ;

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
