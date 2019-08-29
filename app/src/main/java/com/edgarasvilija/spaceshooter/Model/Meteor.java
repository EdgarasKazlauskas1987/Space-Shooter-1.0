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
    private Bitmap bitmap;
    private Bitmap resizedMeteor;

    private int x;
    private int y;

    private Rect hitBox;

    GameActivity gameActivity = new GameActivity();

    public Meteor(Context context, int x, int y)
    {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_meteor);
        //counting 3procent of all screen area
        int areaSize = (int)(gameActivity.getArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        resizedMeteor = Bitmap.createScaledBitmap(bitmap, root, root, true);

        this.x = x;
        this.y = y;

        // Initialize the hit box
        hitBox = new Rect(getX(), getY(), resizedMeteor.getWidth(), resizedMeteor.getHeight());
    }

    public void update(float deltaTime)
    {
        if (y > gameActivity.getYpart())
        {
            Random placeGenerator = new Random();

            //makes meteor appear only within screen
            x = placeGenerator.nextInt(gameActivity.getXpart() - getBitmap().getWidth());
            y = 0;

        }

        else {

            y += 3* (gameActivity.getYpart() / 15) * deltaTime;

        }

        // Refresh hit box location
        hitBox.left = getX();
        hitBox.top =  getY();
        hitBox.right = getX() + resizedMeteor.getWidth();
        hitBox.bottom = getY() + resizedMeteor.getHeight();

    }

    public Bitmap getBitmap()
    {
        return resizedMeteor;
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

    public Rect getHitBox()
    {
        return hitBox;
    }
}
