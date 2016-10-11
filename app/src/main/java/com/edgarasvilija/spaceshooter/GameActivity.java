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

//this is the class where all the game will be played
//we will not use layout for this activitysince
//it will be dinamically generated
public class GameActivity extends Activity {

    private GameView gameView;

    //these will be used to see what size is the screen
    public static int screenXsize;
    public static int screenYsize;

    public static final  int hugeScreenX = 2300;
    public static final int hugeScreenY = 1400;

    public static final int bigScreenX = 1250;
    public static final int bigScreenY = 2200;

    public static final int mediumScreenX = 900;
    public static final int mediumScreenY = 1600;

    public static final int smallScreenX = 600;
    public static final int smallScreenY = 900;

    public static final int tinnyScreenX = 500;
    public static final int tinnyScreenY = 890;

    public static final int microScreenX = 350;
    public static final int microScreenY = 550;

    private static Context mContext;


    //this is where playButton sends us from MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
       // screenSizeX();
       // screenSizeY();
        //get display object in order to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        //load resolution into a Point object
        Point screenSize = new Point();
        display.getSize(screenSize);

        int screenX = screenSize.x;
        String screen1= screenX + "";
        int screenY = screenSize.y;
        String screen2 = screenY + "";
        Log.i("Sizes of the screen : " + screen1, screen2);

        //setting screen size to variables
        screenXsize = screenSize.x;
        screenYsize = screenSize.y;

        gameView = new GameView(this, screenSize.x, screenSize.y, screenSize.x);


      //  gameView = new GameView(this, screenSizeX(), screenSizeY());

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

    public String getScreenXsize()
    {
        if ((screenXsize <= microScreenX  && screenYsize <= microScreenY)
        || (screenXsize <= microScreenX  || screenYsize <= microScreenY))
        {
            return "tinny";

        }

        else if ((((screenXsize > microScreenX )&& (screenYsize > microScreenY ))
        && ((screenXsize <= mediumScreenX )&& (screenYsize <= mediumScreenY )))
        ||   (((screenXsize > microScreenX )|| (screenYsize > microScreenY ))
            &&   (screenXsize <= mediumScreenX )&& (screenYsize <= mediumScreenY )))

        {
            return "medium";
        }

        else if ((((screenXsize > mediumScreenX )&& (screenYsize > mediumScreenY ))
                && ((screenXsize <= bigScreenX )&& (screenYsize <= bigScreenY )))
                ||   (((screenXsize > mediumScreenY )|| (screenYsize > mediumScreenY ))
                &&   (screenXsize <= bigScreenX )&& (screenYsize <= bigScreenY )))
        {
            return "big";
        }

        else if ((screenXsize > bigScreenX && screenYsize > bigScreenY)
        || (screenXsize > bigScreenX || screenYsize >bigScreenY ))
        {
            return "huge";
        }

        else {
            return "medium";
        }

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


