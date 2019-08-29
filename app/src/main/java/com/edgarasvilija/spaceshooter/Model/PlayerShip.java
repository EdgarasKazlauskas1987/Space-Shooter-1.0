package com.edgarasvilija.spaceshooter.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.R;

/**
 * Created by Edgaras on 27/09/2016.
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
    public PlayerShip(Context context, int x, int y)
    {

        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_player_ship);

        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(2.5f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedBitmap = Bitmap.createScaledBitmap(bitmap, root, root, true);

        this.x = x;
        this.y = y;

        // Initialize the hit box
        hitBox = new Rect(getX(), getY(), resizedBitmap.getWidth() , resizedBitmap.getHeight());

    }



    //incrementing ships y by 1 every time the method is called
    public void update(float deltaTime, int newY)
    {
        //y--;
        if (goingRight)
        {
            //playerShip goes img_right_button
            x += 1 * (gameActivity.getXpart() / 2) *deltaTime;
        }

        else if (goingLeft){
            //playerShip goes back to img_right_button
            x -= 1 * (gameActivity.getXpart() / 2) *deltaTime;
        }

        else {
            //do nothing
        }

        // Refresh hit box location
        hitBox.left = getX();
        hitBox.top =  newY;
        hitBox.right = getX() + resizedBitmap.getWidth();
        hitBox.bottom = newY + resizedBitmap.getHeight();

        if  (x >getMaxX() )
        {
            x = getMaxX();
        }

        if (x < minX)
        {
            x = minX;
        }

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
        maxX = gameActivity.getXpart()  - resizedBitmap.getWidth();

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

    //move ship to img_right_button
    public void goRight()
    {
        goingRight = true;
        goingLeft = false;
    }

    //move ship to img_left_button
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

