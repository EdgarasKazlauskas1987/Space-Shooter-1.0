package com.edgarasvilija.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

import java.util.Random;

/**
 * Created by Edgaras on 06/10/2016.
 */
public class EnemyShip
{
    GameActivity gameActivity = new GameActivity();

    Random randomX = new Random();

    private Bitmap bitmap;
    private Bitmap resizedEnemyShip;

    private int x;

    //private int y;
    private float y;

    //for detecting enemies leaving the screen
    private int maxY;
    private int minX;

    //spawn enemies within screen bounds
    private int maxX;
    private int minY;

    private Rect hitBox;



    //constructor
    public EnemyShip(Context context, int screenX, int screenY)
    {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy1);

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(2.5f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedEnemyShip = Bitmap.createScaledBitmap(bitmap, root, root, true);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        x = randomX.nextInt(gameActivity.getXpart() - getBitmap().getWidth());

        // Initialize the hit box
        hitBox = new Rect(getX(), (int) getY(), resizedEnemyShip.getWidth(), resizedEnemyShip.getHeight());

    }

    //enemy ship is going down
    //when enemy ship reaches the maximum Y then it
    //respawns at 0 position
    public void update(float speed)
    {
        if (y > gameActivity.getYpart())
        {
            Random placeGenerator = new Random();
            //putting respawned enemyship in a random X spot
            x = placeGenerator.nextInt(gameActivity.getXpart()) - getBitmap().getWidth();
            y = 0;
        }

        else {
           // y++;
            y = y + 50 * speed;
            Log.i("Speed of ship" +  speed, "-----------------");

        }

        // Refresh hit box location
        hitBox.left = getX();
         hitBox.top = (int) getY();
        hitBox.right = getX() + resizedEnemyShip.getWidth();
        hitBox.bottom = (int) (getY() + resizedEnemyShip.getHeight());

    }

    //getters and setters
    public Bitmap getBitmap()
    {
        return resizedEnemyShip;
    }

    public int getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public void setX (int x)
    {
        this.x = x;
    }

    public Rect getHitBox()
    {
        return hitBox;
    }

    public void setY(int y)
    {
        this.y = y;
        Random placeGenerator = new Random();
        x = placeGenerator.nextInt(gameActivity.getXpart()) - getBitmap().getWidth();
       // x = 20;
    }

}
