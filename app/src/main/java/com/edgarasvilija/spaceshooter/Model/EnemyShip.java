package com.edgarasvilija.spaceshooter.Model;

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

    private Bitmap rawEnemyShip;
    private Bitmap enemyShip;

    private int xCoordinate;
    private int yCoordinate;

    //holds coordinates of 4 edges of object
    private Rect enemyShipRect;

    public EnemyShip(Context context, int screenX, int screenY) {
        Random generator = new Random();

        int whichBitmap = generator.nextInt(3);
        switch (whichBitmap) {
            case 0:
                rawEnemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_enemy_ship2);
                break;
            case 1:
                rawEnemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_enemy_ship2);
                break;
            case 2:
                rawEnemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_enemy_ship3);
                break;
        }

        //counting 2,5% of all screen area
        int areaSize = (int) (gameActivity.getArea() * (2.5f / 100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        //putting enemy ship in a random X spot
        enemyShip = Bitmap.createScaledBitmap(rawEnemyShip, root, root, true);

        xCoordinate = generator.nextInt(gameActivity.getXpart() - getRawEnemyShip().getWidth());
        Log.i("constructor " + xCoordinate, "******");
        // Initialize the hit box
        enemyShipRect = new Rect(getxCoordinate(), (int) getyCoordinate(), enemyShip.getWidth(), enemyShip.getHeight());
    }

    //enemy ship is going down
    //when enemy ship leaves screen it
    //respawns at 0 position
    public void update(float deltaTime) {
        if (yCoordinate > gameActivity.getYpart()) //if enemy ship img_left_button screen
        {
            Random placeGenerator = new Random();
            //putting respawned enemy ship in a random X spot
            xCoordinate = placeGenerator.nextInt(gameActivity.getXpart() - getRawEnemyShip().getWidth());
            Log.i("update " + xCoordinate, "******");
            yCoordinate = 0;

        } else {
            //here i use deltaTime
            //the longer it takes to do the updates, the more
            // i will multiple the distance the enemyShip has
            //to move
            //see the code in gameView class
            yCoordinate += 3 * (gameActivity.getYpart() / 14) * deltaTime;
        }

        // Refresh enemyShipRect location
        enemyShipRect.left = getxCoordinate();
        enemyShipRect.top = getyCoordinate();
        enemyShipRect.right = getxCoordinate() + enemyShip.getWidth();
        enemyShipRect.bottom = (getyCoordinate() + enemyShip.getHeight());
    }

    public Bitmap getRawEnemyShip() {
        return enemyShip;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Rect getEnemyShipRect() {
        return enemyShipRect;
    }

    //method is called when enemy ship is destroyed or leaves screen
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
        Random placeGenerator = new Random();

        xCoordinate = placeGenerator.nextInt(gameActivity.getXpart() - getRawEnemyShip().getWidth());
        Log.i("setyCoordinate " + xCoordinate, "******");
    }
}


