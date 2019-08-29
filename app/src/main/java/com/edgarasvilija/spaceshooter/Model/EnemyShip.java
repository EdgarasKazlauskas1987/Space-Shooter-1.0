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
public class EnemyShip {
    GameActivity gameActivity = new GameActivity();

    private Bitmap bitmap;
    private Bitmap resizedEnemyShip;

    private int x; //x coordinate
    private int y; //y coordinate

    //holds coordinates of 4 edges of object
    private Rect enemyShipRect;


    public EnemyShip(Context context, int screenX, int screenY) {
        Random generator = new Random();

        //here we choose which picture will enemy ship have
        int whichBitmap = generator.nextInt(3);
        switch (whichBitmap) {
            case 0:
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_enemy_ship1);
                break;
            case 1:
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_enemy_ship2);
                break;
            case 2:
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_enemy_ship3);
                break;
        }

        //counting 2,5 procent of all screen area
        int areaSize = (int) (gameActivity.getArea() * (2.5f / 100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        //putting enemyship in a random X spot
        resizedEnemyShip = Bitmap.createScaledBitmap(bitmap, root, root, true);

        x = generator.nextInt(gameActivity.getXpart() - getBitmap().getWidth());
        Log.i("constructor " + x, "******");
        // Initialize the hit box
        enemyShipRect = new Rect(getX(), (int) getY(), resizedEnemyShip.getWidth(), resizedEnemyShip.getHeight());
    }

    //enemy ship is going down
    //when enemy ship leaves screen it
    //respawns at 0 position
    public void update(float deltaTime) {
        if (y > gameActivity.getYpart()) //if enemy ship img_left_button screen
        {
            Random placeGenerator = new Random();
            //putting respawned enemyship in a random X spot
            x = placeGenerator.nextInt(gameActivity.getXpart() - getBitmap().getWidth());
            Log.i("update " + x, "******");
            y = 0;

        } else {
            //here i use deltaTime
            //the longer it takes to do the updates, the more
            // i will multiple the distance the enemyShip has
            //to move
            //see the code in gameView class
            y += 3 * (gameActivity.getYpart() / 14) * deltaTime;
        }

        // Refresh enemyShipRect location
        enemyShipRect.left = getX();
        enemyShipRect.top = getY();
        enemyShipRect.right = getX() + resizedEnemyShip.getWidth();
        enemyShipRect.bottom = (getY() + resizedEnemyShip.getHeight());

    }

    public Bitmap getBitmap() {
        return resizedEnemyShip;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Rect getEnemyShipRect() {
        return enemyShipRect;
    }

    //method is called when enemy ship is destroyed or leaves screen
    public void setY(int y) {
        this.y = y;
        Random placeGenerator = new Random();

        x = placeGenerator.nextInt(gameActivity.getXpart() - getBitmap().getWidth());
        Log.i("setY " + x, "******");
    }
}


