package com.edgarasvilija.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.edgarasvilija.spaceshooter.View.Gameplay;


public class GameActivity extends Activity
{
    private static int screenSizeX;
    private static int screenSizeY;

    private static Context mContext;
    private Gameplay gameplay;

    //this is where playButton sends us from MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;

        //get display object in order to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        //load resolution into a Point object
        Point screenSize = new Point();
        display.getSize(screenSize);
        //setting screen size to variables
        screenSizeX = screenSize.x;
        screenSizeY = screenSize.y;

        gameplay = new Gameplay(this, screenSize.x, screenSize.y, screenSize.x);
        //making our game view the view for the activity
        setContentView(gameplay);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gameplay.pause();
    }

    //uses method from GameView  class
    @Override
    protected void onResume()
    {
        super.onResume();
        gameplay.resume();
    }

    public int getScreenSizeX()
    {
        return screenSizeX;
    }

    public int getScreenSizeY()
    {
        return screenSizeY;
    }

    public int getScreenArea()
    {
        return screenSizeX * screenSizeY;
    }

    public  Context getContext(){
        return mContext;
    }
}


