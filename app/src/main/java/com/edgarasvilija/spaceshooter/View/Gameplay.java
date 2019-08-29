package com.edgarasvilija.spaceshooter.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.Model.EnemyShip;
import com.edgarasvilija.spaceshooter.Model.EnemyShipLaserBlast;
import com.edgarasvilija.spaceshooter.Model.LaserBlast;
import com.edgarasvilija.spaceshooter.Model.LeftButton;
import com.edgarasvilija.spaceshooter.Model.Meteor;
import com.edgarasvilija.spaceshooter.Model.PlayerShip;
import com.edgarasvilija.spaceshooter.Model.RightButton;
import com.edgarasvilija.spaceshooter.Model.StopButton;
import com.edgarasvilija.spaceshooter.Model.TargetButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Edgaras on 25/09/2016.
 */
//this class will provide the view for the game
public class Gameplay extends SurfaceView implements Runnable {

    GameActivity gameActivity;

    Context context;


    private SharedPreferences prefs; //used to load highest score if it exists
    private SharedPreferences.Editor editor; // writtes actual result into file
    private int bestResult; // best result will be kept here

    //volatile variable can be used by multiple threads and also outside a thread
    volatile boolean playing;
    volatile boolean gameEnded = false;
    volatile static int pointsScored;
    volatile static int shieldsLeft;
    volatile static int numberOfShoots;

    Thread gameThread = null;

    Random placeGenerator = new Random();

    //game object
    private PlayerShip playerShip;
    private LeftButton leftButton;
    private RightButton rightButton;
    private TargetButton targetButton;
    private StopButton stopButton;
    private LaserBlast laserBlast;
    private EnemyShip enemyShip1;
    private EnemyShip enemyShip2;
    private EnemyShip enemyShip3;

    private Meteor meteor1;
    private Meteor meteor2;

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
    public static Random random = new Random();

    //This is for dealing with sounds
    private SoundPool soundPool;
    int explosionSound = -1;
    int destroyedSound = -1;
    int hitSound = -1;
    int laserBlastSound = -1;

    //list of players laser blasts
    public static ArrayList<LaserBlast> listOfLaserBlasts = new ArrayList<>() ;
    //list of enemy ships laser blasts
    public static ArrayList<EnemyShipLaserBlast> enemyShip1LaserBlasts = new ArrayList<>();
    public static ArrayList<EnemyShipLaserBlast> enemyShip2LaserBlasts = new ArrayList<>();
    public static ArrayList<EnemyShipLaserBlast> enemyShip3LaserBlasts = new ArrayList<>();

//i could also use this
//    public GameView(Context context, int x, int y, int rightForPlayerShip) {
    public Gameplay(GameActivity gActivity, int x, int y, int rightForPlayerShip)
    {
        super(gActivity);
        gameActivity = gActivity;
        this.context = gActivity;
        //this.context = context;

        //getting file and if it does not exist creating it
        prefs = context.getSharedPreferences("HiScores", context.MODE_PRIVATE);
        editor = prefs.edit();
        //getting fastest time from entry, if its not there then result is 0
        bestResult = prefs.getInt("highestScore", 0);


        right = 0;
        left = y;
        this.rightForPlayerShip = rightForPlayerShip;
        rightForRightButton = x;
        leftForRightButton = y;
        surfaceHolder = getHolder();

        paint = new Paint();

        startGame();
    }

    public void startGame()
    {
        pointsScored = 0;
        shieldsLeft = 3;
        numberOfShoots = 0;
        playerShip = new PlayerShip(context, rightForRightButton/2 , leftForRightButton); //x y
        leftButton = new LeftButton(context, rightForRightButton, 0);
        rightButton = new RightButton(context, rightForRightButton, 0);
        targetButton = new TargetButton(context, rightForRightButton, leftForRightButton);
        stopButton = new StopButton(context, rightForRightButton, leftForRightButton);




        enemyShip1 = new EnemyShip(context, 5, 0);
        enemyShip2 = new EnemyShip(context, 5, 0);
        enemyShip3 = new EnemyShip(context, 5, 0);
        laserBlast = new LaserBlast( rightForRightButton ,leftForRightButton);
        meteor1 = new Meteor(context, placeGenerator.nextInt(gameActivity.getXpart() + 1), 0);
        meteor2 = new Meteor(context, placeGenerator.nextInt(gameActivity.getXpart() + 1), 0);
        listOfLaserBlasts = new ArrayList<>();
        enemyShip1LaserBlasts = new ArrayList<>();

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor assetFileDescriptor;

            //4 sound of the game
            assetFileDescriptor = assetManager.openFd("collision.ogg");
            destroyedSound = soundPool.load(assetFileDescriptor, 0);

            assetFileDescriptor = assetManager.openFd("explosion.ogg");
            explosionSound = soundPool.load(assetFileDescriptor, 0);

            assetFileDescriptor = assetManager.openFd("hit.ogg");
            hitSound = soundPool.load(assetFileDescriptor, 0);

            assetFileDescriptor = assetManager.openFd("laserShot.ogg");
            laserBlastSound = soundPool.load(assetFileDescriptor, 0);
        }

        catch (IOException e)
        {
            //do nothing
        }


    }

    @Override
    public void run()
    {

        //try to measure everything in this method

        //not sure what is that
        //Looper.prepare();

        int frames = 0;
        long startTime = System.nanoTime();
        long currTime = 0;
        long lastTime = System.nanoTime();

        while (playing)
        {
            //here we get current time
            currTime = System.nanoTime();   //1  //2
            //here we calculate the difference between current time and last time
            //this helps to see how much time does it take to do update() method
            //this difference is used in update() methods
            //if difference is 2 then it will multiple for example the distance
            //that enemyShip has to move by 2. If the difference is 5, then it
            //will multiple the distance to move by 5.
            //it will mean that when the difference is 2 then it updates fast
            //therefore you dont need the enemyShip to move by that much, but
            //if the difference is 5, then it means that it updates a bit
            //slow, therefore it means that you have to move the enemyShip by
            //more positions (x5 time more). See the code in enemyShip class
            //where I use deltaTime which is the difference between currentTime
            //and lastTime
            update((currTime - lastTime)/ 1000000000.0f); //updates the game data
            //here we are setting lastTime
            lastTime = currTime; //1

            draw(); //draws the screen based on the game data

            generateRandomNumber(enemyShip1LaserBlasts, enemyShip1.getX(), enemyShip1.getY());
            generateRandomNumber(enemyShip2LaserBlasts, enemyShip2.getX(), enemyShip2.getY());
            generateRandomNumber(enemyShip3LaserBlasts, enemyShip3.getX(), enemyShip3.getY());


            frames = frames + 1;
            if (System.nanoTime() - startTime > 100000000)
            {
                framesPerSecond = frames;
                frames = 0;
                startTime = System.nanoTime();
            }
        }

    }

    private void update(float deltaTime)
    {

        decrementPointsScored();

        playerShip.update(deltaTime, left - targetButton.getBitmap().getHeight() - playerShip.getBitmap().getHeight());

        //updating enemyship
        enemyShip1.update(deltaTime);
        enemyShip2.update(deltaTime);
        enemyShip3.update(deltaTime);

        meteor1.update(deltaTime);
        meteor2.update(deltaTime);

        //handles enemy ship laser blasts
        //checks if laser blasts are still within the screen
        //and if they leave the screen they are removed from arrayList
        //if they are still on screen they are updated
        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++ )
        {
            if (enemyShip1LaserBlasts.get(i).getY() > gameActivity.getYpart())
            {
                enemyShip1LaserBlasts.remove(enemyShip1LaserBlasts.get(i));

            }

            else
            {
                enemyShip1LaserBlasts.get(i).update(deltaTime);
            }
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++ )
        {
            if (enemyShip2LaserBlasts.get(i).getY() > gameActivity.getYpart())
            {
                enemyShip2LaserBlasts.remove(enemyShip2LaserBlasts.get(i));

            }

            else {
                enemyShip2LaserBlasts.get(i).update(deltaTime);
            }
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++ )
        {
            if (enemyShip3LaserBlasts.get(i).getY() > gameActivity.getYpart())
            {
                enemyShip3LaserBlasts.remove(enemyShip3LaserBlasts.get(i));

            }

            else {
                enemyShip3LaserBlasts.get(i).update(deltaTime);
            }
        }


        //cheking if enemy ship and players ship collides with each other
        //if they collide then player looses 1 shield and 1 point
        //enemy ship is destroyed

        if (Rect.intersects(enemyShip1.getEnemyShipRect(), playerShip.getHitBox()))
        {
            soundPool.play(explosionSound, 1,1,0,0,1);
            enemyShip1.setY(0);
            shieldsLeft--;
            decrementPointsScored();
            if (shieldsLeft <0)
            {
                gameEnded = true;
            }
            //gameEnded = true;

        }

        if (Rect.intersects(enemyShip2.getEnemyShipRect(), playerShip.getHitBox()))
        {
            soundPool.play(explosionSound, 1,1,0,0,1);
            enemyShip2.setY(0);
            shieldsLeft--;
            decrementPointsScored();
            if (shieldsLeft <0)
            {
                gameEnded = true;
            }
            //gameEnded = true;

        }
        if (Rect.intersects(enemyShip3.getEnemyShipRect(), playerShip.getHitBox()))
        {
            soundPool.play(explosionSound, 1,1,0,0,1);
            enemyShip3.setY(0);
            shieldsLeft--;
            decrementPointsScored();
            if (shieldsLeft <0)
            {
                gameEnded = true;
            }
            //gameEnded = true;
        }

        //cheking if player's ship collides with meteors
        //if so the player looses 1 shield and meteor dissapers
        //also cheking if player still have shields, if not then game over
        if (Rect.intersects(meteor1.getHitBox(), playerShip.getHitBox()))
        {
            soundPool.play(explosionSound, 1,1,0,0,1);
            meteor1.setY();
            shieldsLeft--;
            if (shieldsLeft <0)
            {
                 gameEnded = true;
            }

        }

        if (Rect.intersects(meteor2.getHitBox(), playerShip.getHitBox()))
        {
            soundPool.play(explosionSound, 1,1,0,0,1);
            meteor2.setY();
            shieldsLeft--;
            if (shieldsLeft <0)
            {
                gameEnded = true;
            }
        }


        //cheking if enemy ship laser blasts have hit the players space ship
        //if so then player looses 1 shield and 1 point
        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++)
        {
            if (Rect.intersects(playerShip.getHitBox(), enemyShip1LaserBlasts.get(i).getEnemyShipLaserBlastRect()))
            {
                soundPool.play(hitSound, 1,1,0,0,1);
                enemyShip1LaserBlasts.get(i).setY();
                decrementPointsScored();
                shieldsLeft--;
                if (shieldsLeft <0)
                {
                    gameEnded = true;
                }
            }
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++)
        {
            if (Rect.intersects(playerShip.getHitBox(), enemyShip2LaserBlasts.get(i).getEnemyShipLaserBlastRect()))
            {
                soundPool.play(hitSound, 1,1,0,0,1);
                enemyShip2LaserBlasts.get(i).setY();
                decrementPointsScored();
                shieldsLeft--;
                if (shieldsLeft <0)
                {
                    gameEnded = true;
                }
            }
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++)
        {
            if (Rect.intersects(playerShip.getHitBox(), enemyShip3LaserBlasts.get(i).getEnemyShipLaserBlastRect()))
            {
                soundPool.play(hitSound, 1,1,0,0,1);
                enemyShip3LaserBlasts.get(i).setY();
                decrementPointsScored();
                shieldsLeft--;
                if (shieldsLeft <0)
                {
                    gameEnded = true;
                }
            }
        }


        //cheking if players laser blast has hit enemy's space ship
        //if so then player gets 1 point, and enemy ship is destroyed
        for (int i = 0; i < listOfLaserBlasts.size(); i ++)
        {

            if (Rect.intersects(enemyShip1.getEnemyShipRect(), listOfLaserBlasts.get(i).getHitbox()))

            {
                soundPool.play(explosionSound, 1,1,0,0,1);
                listOfLaserBlasts.get(i).setY();
                enemyShip1.setY(0);
                pointsScored++;

            }

            if (Rect.intersects(enemyShip2.getEnemyShipRect(), listOfLaserBlasts.get(i).getHitbox()))

            {
                soundPool.play(explosionSound, 1,1,0,0,1);
                listOfLaserBlasts.get(i).setY();
                enemyShip2.setY(0);
                pointsScored++;

            }

            if (Rect.intersects(enemyShip3.getEnemyShipRect(), listOfLaserBlasts.get(i).getHitbox()))

            {
                soundPool.play(explosionSound, 1,1,0,0,1);
                listOfLaserBlasts.get(i).setY();
                enemyShip3.setY(0);
                pointsScored++;

            }

            //if players space ship's laser blast is out of the screen
            //then remove it, otherwise - update it's position
            if (listOfLaserBlasts.get(i).getY() < 0)
            {
                listOfLaserBlasts.remove(listOfLaserBlasts.get(i));

            }

            else {
                listOfLaserBlasts.get(i).update(deltaTime);
            }

        }

    }

    private void draw()
    {

        if (surfaceHolder.getSurface().isValid())
        {

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // drawing players ship
            canvas.drawBitmap(playerShip.getBitmap(), playerShip.getX()  ,
                    left - targetButton.getBitmap().getHeight() - playerShip.getBitmap().getHeight(), paint);


            //drawing enemy ships
            canvas.drawBitmap(enemyShip1.getBitmap(), enemyShip1.getX(), enemyShip1.getY(), paint);
            canvas.drawBitmap(enemyShip2.getBitmap(), enemyShip2.getX(), enemyShip2.getY(), paint);
            canvas.drawBitmap(enemyShip3.getBitmap(), enemyShip3.getX(), enemyShip3.getY(), paint);


            //drawing meteors
            canvas.drawBitmap(meteor1.getBitmap(), meteor1.getX(), meteor1.getY(), paint);
            canvas.drawBitmap(meteor2.getBitmap(), meteor2.getX(), meteor2.getY(), paint);

            //drawing the img_left_button button
            canvas.drawBitmap(leftButton.getBitmap(), right, left - leftButton.getBitmap().getHeight(), paint);

            //drawing the img_right_button button
            canvas.drawBitmap(rightButton.getBitmap(), rightForRightButton -
                    rightButton.getBitmap().getWidth(), leftForRightButton -
                    rightButton.getBitmap().getHeight(), paint);

         //   canvas.drawBitmap(targetButton.getBitmap(), ((img_right_button/2) - (targetButton.getBitmap().getWidth() - (targetButton.getBitmap().getWidth() *2))), img_left_button - targetButton.getBitmap().getHeight(), paint);
                canvas.drawBitmap(targetButton.getBitmap(), ((rightForRightButton/2) - (targetButton.getBitmap().getWidth()/2)),
                left - targetButton.getBitmap().getHeight(), paint);

            //drawing stop button
            canvas.drawBitmap(stopButton.getBitmap(), rightForRightButton-stopButton.getBitmap().getWidth(), 0, paint);


            //drawing players laser blasts
         for (int i = 0; i <listOfLaserBlasts.size(); i++)
            {
                canvas.drawBitmap(listOfLaserBlasts.get(i).getBitmap(), listOfLaserBlasts.get(i).getX(),
                        listOfLaserBlasts.get(i).getY(), paint);
            }

            //paints img_enemy_ship1 space ship laser blasts
            for (int i = 0; i < enemyShip1LaserBlasts.size(); i++)
            {
                canvas.drawBitmap(enemyShip1LaserBlasts.get(i).getBitmap(), enemyShip1LaserBlasts.get(i).getX(),
                        enemyShip1LaserBlasts.get(i).getY(), paint);
            }
            //paints img_enemy_ship2 space ship laser blasts
            for (int i = 0; i < enemyShip2LaserBlasts.size(); i++)
            {
                canvas.drawBitmap(enemyShip2LaserBlasts.get(i).getBitmap(), enemyShip2LaserBlasts.get(i).getX(),
                        enemyShip2LaserBlasts.get(i).getY(), paint);
            }
            //paints img_enemy_ship3 space ship laser blasts
            for (int i = 0; i < enemyShip3LaserBlasts.size(); i++)
            {
                canvas.drawBitmap(enemyShip3LaserBlasts.get(i).getBitmap(), enemyShip3LaserBlasts.get(i).getX(),
                        enemyShip3LaserBlasts.get(i).getY(), paint);
            }

            //if the game is not ended then show this information for the user
            if (!gameEnded) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(20);
                canvas.drawText("Points scored: " + pointsScored, 10, 20, paint);

                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(20);
                canvas.drawText("Lives img_left_button: " + shieldsLeft, 10, 200, paint);
            }
            //if the game ended then show this information for the user
             else
             {
                 if (pointsScored > bestResult)
                 {
                     editor.putInt("highestScore", pointsScored);
                     editor.commit();
                     bestResult = pointsScored;
                 }

                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Your Result: " + pointsScored, rightForRightButton/2, leftForRightButton/2 , paint);
                 pause();
                 canvas.drawText("Press red button to play again " + pointsScored, rightForRightButton/2, leftForRightButton/4 , paint);
                 pause();
            }

            surfaceHolder.unlockCanvasAndPost(canvas);


        }

    }


    public void pause()
    {

        playing = false;

        try {
            gameThread.join(10);

        }
        catch (InterruptedException e)
        {

        }
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

                //when game is ended touch the screen and it starts again
                if (gameEnded) {
                    startGame();
                    gameEnded = false;
                }

                //x and y coordinates of touch
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();

                //checking if we have pressed LEFT button
                if ((x > right && x < right + leftButton.getBitmap().getWidth()) &&
                        (y > left - leftButton.getBitmap().getHeight() && y < left))
                {
                    playerShip.goLeft();
                    //pause();
                }

                //checking if player clicked RIGHT button
                else if ((x <= rightForRightButton && x >= rightForRightButton - rightButton.getBitmap().getWidth()) &&
                        ( y  <= left && y >= left - rightButton.getBitmap().getHeight()))
            {
                playerShip.goRight();
                //resume();
            }

                //cheking if player clicked SHOOT button
                else if ((x <= rightForRightButton/2 + targetButton.getBitmap().getWidth()/2) &&
                ((x >= rightForRightButton/2 - (targetButton.getBitmap().getWidth()/2)))
                        && (y <= left && (y >= left - rightButton.getBitmap().getHeight())))

                {
                    soundPool.play(laserBlastSound, 1,1,0,0,1);
                    numberOfShoots++;
                    if (numberOfShoots > 150)
                    {
                        gameEnded = true;
                    }
                    Log.i("Number of laser Shoots" + numberOfShoots, "+++++++++");
                    listOfLaserBlasts.add(new LaserBlast((playerShip.getX() + (playerShip.getBitmapWidth()/2)) - (laserBlast.getBitmap().getWidth()/2)
                            , playerShip.getY()
                            - targetButton.getBitmap().getHeight() - playerShip.getBitmap().getHeight()));

                }

                else if ((y > 0 && y < stopButton.getBitmap().getHeight()) && (x < rightForRightButton && x >
                        rightForRightButton - stopButton.getBitmap().getWidth()))
            {
                if (playing == false)
                {
                    resume();
                }

                else if (playing == true)
                {
                    pause();
                }
            }
                //if player clicked somewhere else
            else
                {
                    //do nothing

                }

                break;

            //player not touching the screen anymore
            case MotionEvent.ACTION_UP:
                //when screen is stoped to be touched playersShip doesnt go img_right_button anymore
                playerShip.standStill();
                break;
        }
        return true;

    }


    //decrement points scored by the player
    public void decrementPointsScored()
    {
        if (enemyShip1.getY() > gameActivity.getYpart()) {

            pointsScored--;
        }

        if (enemyShip2.getY() > gameActivity.getYpart()) {

            pointsScored--;
        }

        if (enemyShip3.getY() > gameActivity.getYpart()) {

            pointsScored--;
        }

    }

    //generates random number which later is used to decide if shoot ot not
    public void generateRandomNumber(ArrayList<EnemyShipLaserBlast> listOfLaserBlasts, int x, int y)
    {
        int number = random.nextInt(125);

        if (number == 1)
        {
            listOfLaserBlasts.add(new EnemyShipLaserBlast(gameActivity, (x + enemyShip1.getBitmap().getWidth()/2)-
                    laserBlast.getBitmap().getWidth()/2,
                    y + enemyShip1.getBitmap().getHeight()/2));
        }

        else
        {
            //do nothing
        }
    }


    }

