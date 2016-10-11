package com.edgarasvilija.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Edgaras on 25/09/2016.
 */
//this class will provide the view for the game
public class GameView extends SurfaceView implements Runnable {

    GameActivity gameActivity = new GameActivity();

    Context context;

    //volatile variable can be used by multiple threads and also outside a thread
    volatile boolean playing;

    Thread gameThread = null;

    Random placeGenerator = new Random();

    //game object
    private PlayerShip playerShip;
    private Left leftButton;
    private Right rightButton;
    private Target targetButton;
    private LaserBlast laserBlast;
    private EnemyShip enemyShip1;
    private EnemyShip enemyShip2;
    private EnemyShip enemyShip3;



    //for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public int right;
    public int rightForPlayerShip;
    public int rightForRightButton;
    public int leftForRightButton;
    public int left;

    private int framesPerSecond = 0;

    public static ArrayList<LaserBlast> listOfLaserBlasts = new ArrayList<>();


    public GameView(Context context, int x, int y, int rightForPlayerShip) {
        super(context);
        right = 0;
        left = y;
        this.rightForPlayerShip = rightForPlayerShip;
        rightForRightButton = x;
        leftForRightButton = y;
        surfaceHolder = getHolder();

        paint = new Paint();
        //initializing player ship object
        playerShip = new PlayerShip(context, x, y); //x y
        leftButton = new Left(context, x, 0);
        rightButton = new Right(context, rightForRightButton, 0);
        targetButton = new Target(context, x, y);
        enemyShip1 = new EnemyShip(context, 300, 0);
        enemyShip2 = new EnemyShip(context, placeGenerator.nextInt(gameActivity.getXpart()), 0);
        enemyShip3 = new EnemyShip(context, placeGenerator.nextInt(gameActivity.getXpart()), 0);

        laserBlast = new LaserBlast( x ,y);
        listOfLaserBlasts = new ArrayList<>();
    }



    @Override
    public void run() {

        //try to measure everything in this method

        int frames = 0;
        long startTime = System.nanoTime();
        long currTime = 0;
        long lastTime = System.nanoTime();

        while (playing)
        {

            currTime = System.nanoTime();

            update((currTime - lastTime)/ 1000000000.0f); //updates the game data
            float whatever = (currTime - lastTime)/ 1000000000.0f;
            Log.i("Frames per second : " + whatever, "**********");
            draw(); //draws the screen based on the game data
            control(); //controls how long is it until the run() method is called again

            lastTime = currTime;

            frames = frames + 1;
            if (System.nanoTime() - startTime > 100000000)
            {
                framesPerSecond = frames;
                frames = 0;
                startTime = System.nanoTime();
            }

          //  Log.i("Frames per second : " + framesPerSecond, "**********");
        }

    }

    private void update(float speed)
    {


        playerShip.update();

        //updating enemyship
        enemyShip1.update(speed);
        enemyShip2.update(speed);
        enemyShip3.update(speed);





        //increments laser blast objects y by 1
        //cheking if there was a collision with enemyShip
        for (int i = 0; i < listOfLaserBlasts.size(); i ++)
        {

            if (Rect.intersects(enemyShip1.getHitBox(), listOfLaserBlasts.get(i).getHitbox()))

            {
                enemyShip1.setY(0);

            }

            if (Rect.intersects(enemyShip2.getHitBox(), listOfLaserBlasts.get(i).getHitbox()))

            {
                enemyShip2.setY(0);

            }

            if (Rect.intersects(enemyShip3.getHitBox(), listOfLaserBlasts.get(i).getHitbox()))

            {
                enemyShip3.setY(0);

            }

            if (listOfLaserBlasts.get(i).getY() < 0)
            {
                listOfLaserBlasts.remove(listOfLaserBlasts.get(i));

            }

            else {
                listOfLaserBlasts.get(i).update(getFramesPerSecond());
            }


        }




    }

    private void draw()
    {
        if (surfaceHolder.getSurface().isValid())
        {


            //locking are of memore where we will draw the player ship
            canvas = surfaceHolder.lockCanvas();

           // canvas.drawColor(Color.argb(255, 0, 0, 0));
            canvas.drawColor(Color.argb(255, 0, 0, 0));

           // currTime = System.nanoTime();

            canvas.drawBitmap(playerShip.getBitmap(), (playerShip.getX() /2) ,
                    left - targetButton.getBitmap().getHeight() - playerShip.getBitmap().getHeight(), paint);


            //drawing enemy ship
            canvas.drawBitmap(enemyShip1.getBitmap(), enemyShip1.getX(), enemyShip1.getY(), paint);
            canvas.drawBitmap(enemyShip2.getBitmap(), enemyShip2.getX(), enemyShip2.getY(), paint);
            canvas.drawBitmap(enemyShip3.getBitmap(), enemyShip3.getX(), enemyShip3.getY(), paint);


            //drawing the left button
            canvas.drawBitmap(leftButton.getBitmap(), right, left - leftButton.getBitmap().getHeight(), paint);

            //drawing the right button
            canvas.drawBitmap(rightButton.getBitmap(), rightForRightButton -
                    rightButton.getBitmap().getWidth(), leftForRightButton -
                    rightButton.getBitmap().getHeight(), paint);

         //   canvas.drawBitmap(targetButton.getBitmap(), ((right/2) - (targetButton.getBitmap().getWidth() - (targetButton.getBitmap().getWidth() *2))), left - targetButton.getBitmap().getHeight(), paint);
        canvas.drawBitmap(targetButton.getBitmap(), ((rightForRightButton/2) - (targetButton.getBitmap().getWidth()/2)),
                left - targetButton.getBitmap().getHeight(), paint);

            paint.setColor(Color.argb(255, 255, 255, 255));

         for (int i = 0; i <listOfLaserBlasts.size(); i++)
            {
                canvas.drawBitmap(listOfLaserBlasts.get(i).getBitmap(), listOfLaserBlasts.get(i).getX(), listOfLaserBlasts.get(i).getY(), paint);
                canvas.drawRect(listOfLaserBlasts.get(i).getHitbox().left,
                        listOfLaserBlasts.get(i).getHitbox().top,
                        listOfLaserBlasts.get(i).getHitbox().right,
                        listOfLaserBlasts.get(i).getHitbox().bottom,
                        paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);


        }

    }
    //it makes thread run slower and therefore update player ship slower
    private void control()
    {
        /*

        try {
            gameThread.sleep(50);
        }
        catch (InterruptedException e)
        {

        } */
    }

    public void pause()
    {
        /*
        playing = false;

        try {
            gameThread.join(10);
        }
        catch (InterruptedException e)
        {

        } */
    }

    //make a new thread and start it
    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();

    }
    @Override
    public boolean onTouchEvent (MotionEvent motionEvent)
    {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK )
        {

            //player touched the screen
            case MotionEvent.ACTION_DOWN:

                //x and y coordinates of touch
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();

                //checking if we have pressed LEFT button
                if ((x > right && x < right + leftButton.getBitmap().getWidth()) &&
                        (y > left - leftButton.getBitmap().getHeight() && y < left))
                {

                    int height = targetButton.getBitmap().getHeight();
                    int width = targetButton.getBitmap().getWidth();
                    String string1 = height + "height";
                    String string2 = width + "width";
                    Log.i("You pressed left button" , "cool");
                    Log.i(string2 , string1);
                    playerShip.goLeft();

                }

                //checking if player clicked RIGHT button
                else if ((x <= rightForRightButton && x >= rightForRightButton - rightButton.getBitmap().getWidth()) &&
                        ( y  <= left && y >= left - rightButton.getBitmap().getHeight()))
            {
                playerShip.goRight();
            }
                //cheking if player clicked SHOOT button
                else if ((x <= rightForRightButton/2 + targetButton.getBitmap().getWidth()/2) &&
                ((x >= rightForRightButton/2 - (targetButton.getBitmap().getWidth()/2)))
                        && (y <= left && (y >= left - rightButton.getBitmap().getHeight())))

                {
                    int whatever = playerShip.getX();
                    Log.i("x coordi of the ship" + whatever, "Yes");
                    listOfLaserBlasts.add(new LaserBlast(whatever/2, playerShip.getY()
                            - targetButton.getBitmap().getHeight() - playerShip.getBitmap().getHeight()));

                    Log.i("Size of blasts" + listOfLaserBlasts.size(), "hehe");

                }
                //if player clicked somewhere else
            else
                {
                    //do nothing

                }

                break;

            //player not touching the screen anymore
            case MotionEvent.ACTION_UP:
                //when screen is stoped to be touched playersShip doesnt go right anymore
              //  playerShip.goLeft();
                playerShip.standStill();
                break;
        }
        return true;

    }

    public int getFramesPerSecond()
    {
        return framesPerSecond;
    }


    }

