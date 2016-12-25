package com.edgarasvilija.spaceshooter.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 13/11/2016.
 */
public class EnemyShipLaserBlast
{
    private int x;
    private int y;

    Bitmap enemyShipLaserblastBitmap;
    Bitmap resizedEnemyShipLaserblastBitmap;//after size adjusted according to screen area

    private Rect enemyShipLaserBlastRect;

    // GameActivity gameActivity = new GameActivity();
    GameActivity gameActivity;

    public EnemyShipLaserBlast (GameActivity ga, int x, int y)
    {
        //can also do like it is in LaserBlast class
        gameActivity = ga;
        enemyShipLaserblastBitmap = BitmapFactory.decodeResource(gameActivity.getContext().getResources(), R.drawable.enemylaser);

        this.x = x;
        this.y = y;


        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        float root = (float) Math.sqrt(areaSize);


        resizedEnemyShipLaserblastBitmap = Bitmap.createScaledBitmap(enemyShipLaserblastBitmap,(int) root, (int) root, true);

        enemyShipLaserBlastRect = new Rect(x, y, resizedEnemyShipLaserblastBitmap.getWidth(), resizedEnemyShipLaserblastBitmap.getHeight());

    }

    public Bitmap getBitmap()
    {
        return resizedEnemyShipLaserblastBitmap;
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
        y = 5000;
    }

    public void update(float deltaTime)
    {
        y += 600 * deltaTime;

        // Refresh hit box location
        enemyShipLaserBlastRect.left = x;
        enemyShipLaserBlastRect.top = y;
        enemyShipLaserBlastRect.right = x + resizedEnemyShipLaserblastBitmap.getWidth();
        enemyShipLaserBlastRect.bottom = y + resizedEnemyShipLaserblastBitmap.getHeight();

    }

    public Rect getEnemyShipLaserBlastRect(){
        return enemyShipLaserBlastRect;
    }
}
