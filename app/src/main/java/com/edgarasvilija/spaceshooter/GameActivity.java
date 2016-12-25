package com.edgarasvilija.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;


public class GameActivity extends Activity {

    private GameView gameView;

    //these will be used to see what size is the screen
    public static int screenXsize;
    public static int screenYsize;

    private static Context mContext;


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
        screenXsize = screenSize.x;
        screenYsize = screenSize.y;


        gameView = new GameView(this, screenSize.x, screenSize.y, screenSize.x);

        //making our game view the view for the activity
        setContentView(gameView);
            }

    //uses method from GameView class
    @Override
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }

    //uses method from GameView  class
    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }


    //these methods used to return x and y of the screen
    public int getXpart()
    {
        return screenXsize;
    }

    public int getYpart()
    {
        return screenYsize;
    }

    //calculates are of the screen
    public int getArea()
    {
        int x = screenXsize;
        int y = screenYsize;
        int area = x * y;
        return area;
    }

    public  Context getContext(){
        return mContext;
    }

    }


