package com.edgarasvilija.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * Created by Edgaras on 27/09/2016.
 */

/*
Things that player ship has to know about itself:
- where is it on the screen
- how it looks like
- how fast is it flying
 */
public class PlayerShip
{

    public Bitmap bitmap;
    public Bitmap resizedBitmap;

    //x and y are screen coordinates for player ship
    private int x;
    private int y;
    private int speed = 0;

    //stop playerShip from leaving the screen
    private int maxX;
    private int minX =0;

    private boolean goingRight;
    private boolean goingLeft;

    private Rect hitBox;

    GameActivity gameActivity = new GameActivity();



    //constructor
    public PlayerShip(Context context, int screenX, int screenY)
    {

      //  x = screenX;
      //  y = screenY;

        x = gameActivity.getXpart();
       y = gameActivity.getYpart();



        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playership);

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(2.5f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedBitmap = Bitmap.createScaledBitmap(bitmap, root, root, true);

        // Initialize the hit box
       // hitBox = new Rect(getX(), getY(), getX() , resizedBitmap.getHeight());

    }



    //incrementing ships y by 1 every time the method is called
    public void update()
    {
        //y--;
        if (goingRight)
        {
            //playerShip goes right
            x+=10 ;
        }

        else if (goingLeft){
            //playerShip goes back to right
            x-=10;
        }

        else {
            //do nothing
        }

        if  (x >getMaxX() * 2)
        {
            x = getMaxX() * 2;
        }

        if (x < minX)
        {
            x = minX;
        }

        // Refresh hit box location
     /*   hitBox.left = getX();
        hitBox.top = getY() - resizedBitmap.getHeight() - 50;
        hitBox.right = getX() + resizedBitmap.getWidth();
        hitBox.bottom = getY() + resizedBitmap.getHeight() ;  */

       // canvas.drawBitmap(playerShip.getBitmap(), (playerShip.getX() /2) ,
          //      left - targetButton.getBitmap().getHeight() - playerShip.getBitmap().getHeight(), paint);


    }

    //Getters
    //sharing image, speed, x and y coordinates with the view
    public Bitmap getBitmap()
    {
        return resizedBitmap;
    }

    public int getBitmapWidth()
    {
        return resizedBitmap.getWidth();
    }

    public int getMaxX()
    {
        maxX = gameActivity.getXpart() - getBitmapWidth();

        return maxX;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    //move ship to right
    public void goRight()
    {
        goingRight = true;
        goingLeft = false;
    }

    //move ship to left
    public void goLeft()
    {
        goingLeft = true;
        goingRight = false;

    }

    public void standStill()
    {
        goingRight = false;
        goingLeft = false;
    }

    public Rect getHitBox()
    {
        return hitBox;
    }


    }

