package com.edgarasvilija.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.edgarasvilija.spaceshooter.View.Gameplay;


public class GameActivity extends Activity
{
    private static int screenWidth;
    private static int screenHeight;

    private static Context mContext;
    private Gameplay gameplay;

    //This is where Play button sends player from MainActivity class
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Get display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        //Load resolution into Point object
        Point screenSize = new Point();
        display.getSize(screenSize);
        //Set screen size
        screenWidth = screenSize.x;
        screenHeight = screenSize.y;

        gameplay = new Gameplay(this, screenWidth, screenHeight);
        //Make game view the view for the activity
        setContentView(gameplay);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gameplay.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        gameplay.resume();
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public int getScreenArea()
    {
        return screenWidth * screenHeight;
    }

    public float getTextSize(float percentage)
    {
        int widthPixels = getScreenWidth();
        return widthPixels*(percentage/100.0f);
    }

    public  Context getContext(){
        return mContext;
    }
}


