package com.edgarasvilija.spaceshooter.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.edgarasvilija.spaceshooter.Controller.GameplayController;
import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.Helper.DrawElements;
import com.edgarasvilija.spaceshooter.Model.EnemyShip;
import com.edgarasvilija.spaceshooter.Model.BlueLaser;
import com.edgarasvilija.spaceshooter.Model.RedLaser;
import com.edgarasvilija.spaceshooter.Model.LeftButton;
import com.edgarasvilija.spaceshooter.Model.Meteor;
import com.edgarasvilija.spaceshooter.Model.PlayerShip;
import com.edgarasvilija.spaceshooter.Model.RightButton;
import com.edgarasvilija.spaceshooter.Model.StopButton;
import com.edgarasvilija.spaceshooter.Model.TargetButton;
import com.edgarasvilija.spaceshooter.R;
import com.edgarasvilija.spaceshooter.Settings.AudioConfiguration;
import com.edgarasvilija.spaceshooter.Settings.Dashboard;
import com.edgarasvilija.spaceshooter.Settings.PointsHandler;
import com.edgarasvilija.spaceshooter.Settings.ShieldsHandler;

import java.util.ArrayList;
import java.util.Random;


public class Gameplay extends SurfaceView implements Runnable {

    GameActivity gameActivity;
    Context context;

    private SharedPreferences highestScoreLoader;
    private SharedPreferences.Editor highestScoreWritter;
    private int highestScore;

    private volatile boolean playing;
    private volatile boolean gameEnded = false;
    private volatile static int numberOfShoots;

    Thread gameThread = null;

    private Random randomGenerator = new Random();

    private PlayerShip playerShip;
    private LeftButton leftButton;
    private RightButton rightButton;
    private TargetButton targetButton;
    private StopButton stopButton;
    private RedLaser redLaser;
    private EnemyShip enemyShip1;
    private EnemyShip enemyShip2;
    private EnemyShip enemyShip3;
    private Meteor meteor1;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public int right;
    public int rightForPlayerShip;
    public int rightForRightButton;
    public int leftForRightButton;
    public int left;

    private int framesPerSecond = 0;

    public static ArrayList<RedLaser> listOfRedLasers = new ArrayList<>() ;
    public static ArrayList<BlueLaser> enemyShip1LaserBlasts = new ArrayList<>();
    public static ArrayList<BlueLaser> enemyShip2LaserBlasts = new ArrayList<>();
    public static ArrayList<BlueLaser> enemyShip3LaserBlasts = new ArrayList<>();
    public static ArrayList<ArrayList<BlueLaser>> enemyShipsLaserBlasts = new ArrayList<>();

    AudioConfiguration audioConfig;
    Dashboard dashboard;
    DrawElements drawElements;
    GameplayController controller = new GameplayController();
    ShieldsHandler shieldsHandler = new ShieldsHandler();
    PointsHandler pointsHandler = new PointsHandler();

    public Gameplay(GameActivity gameActivity, int x, int y, int rightForPlayerShip)
    {
        super(gameActivity);
        this.gameActivity = gameActivity;
        this.context = gameActivity;

        //getting file and if it does not exist creating it
        highestScoreLoader = context.getSharedPreferences("HiScores", context.MODE_PRIVATE);
        highestScoreWritter = highestScoreLoader.edit();
        //getting fastest time from entry, if its not there then result is 0
        highestScore = highestScoreLoader.getInt("highestScore", 0);

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
        //pointsScored = 0;
        pointsHandler.setPoints();
        shieldsHandler.setShieldsLeft();
        numberOfShoots = 0;

        playerShip = new PlayerShip(context, rightForRightButton/2 , leftForRightButton); //x y
        leftButton = new LeftButton(context, rightForRightButton, 0, R.drawable.img_left_button);
        rightButton = new RightButton(context, rightForRightButton, 0, R.drawable.img_right_button);
        targetButton = new TargetButton(context, rightForRightButton, leftForRightButton, R.drawable.img_target);
        stopButton = new StopButton(context, rightForRightButton, leftForRightButton, R.drawable.img_stop_button);

        enemyShip1 = new EnemyShip(context, 5, 0);
        enemyShip2 = new EnemyShip(context, 5, 0);
        enemyShip3 = new EnemyShip(context, 5, 0);
        redLaser = new RedLaser(gameActivity, rightForRightButton ,leftForRightButton, R.drawable.img_red_laser);
        meteor1 = new Meteor(context, randomGenerator.nextInt(gameActivity.getScreenSizeX() + 1), 0);
        listOfRedLasers = new ArrayList<>();
        enemyShip1LaserBlasts = new ArrayList<>();

        audioConfig = new AudioConfiguration(context);
        dashboard = new Dashboard();
        drawElements = new DrawElements();
    }

    @Override
    public void run()
    {
        int frames = 0;
        long startTime = System.nanoTime();
        long currTime = 0;
        long lastTime = System.nanoTime();

        while (playing) {
            //Getting current time
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
            update((currTime - lastTime) / 1000000000.0f); //updates the game data
            //here we are setting lastTime
            lastTime = currTime; //1

            draw();

            generateRandomNumber(enemyShip1LaserBlasts, enemyShip1.getxCoordinate(), enemyShip1.getyCoordinate());
            generateRandomNumber(enemyShip2LaserBlasts, enemyShip2.getxCoordinate(), enemyShip2.getyCoordinate());
            generateRandomNumber(enemyShip3LaserBlasts, enemyShip3.getxCoordinate(), enemyShip3.getyCoordinate());

            frames = frames + 1;
            if (System.nanoTime() - startTime > 100000000) {
                framesPerSecond = frames;
                frames = 0;
                startTime = System.nanoTime();
            }
        }
    }

    private void update(float deltaTime)
    {
        decrementPointsScored();

        playerShip.update(deltaTime, left - targetButton.getButton().getHeight() - playerShip.getRawPlayerShip().getHeight());

        enemyShip1.update(deltaTime);
        enemyShip2.update(deltaTime);
        enemyShip3.update(deltaTime);
        meteor1.update(deltaTime);

        //handles enemy ship laser blasts
        //checks if laser blasts are still within the screen
        //and if they leave the screen they are removed from arrayList
        //if they are still on screen they are updated

        //ToDo: uncomment when ready
/*        enemyShipsLaserBlasts.add(enemyShip1LaserBlasts);
        enemyShipsLaserBlasts.add(enemyShip2LaserBlasts);
        enemyShipsLaserBlasts.add(enemyShip3LaserBlasts);*/

        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++) {
            if (enemyShip1LaserBlasts.get(i).getCoordinateY() > gameActivity.getScreenSizeY()) {
                enemyShip1LaserBlasts.remove(enemyShip1LaserBlasts.get(i));

            } else {
                enemyShip1LaserBlasts.get(i).update(deltaTime);
            }
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++) {
            if (enemyShip2LaserBlasts.get(i).getCoordinateY() > gameActivity.getScreenSizeY()) {
                enemyShip2LaserBlasts.remove(enemyShip2LaserBlasts.get(i));

            } else {
                enemyShip2LaserBlasts.get(i).update(deltaTime);
            }
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++) {
            if (enemyShip3LaserBlasts.get(i).getCoordinateY() > gameActivity.getScreenSizeY()) {
                enemyShip3LaserBlasts.remove(enemyShip3LaserBlasts.get(i));

            } else {
                enemyShip3LaserBlasts.get(i).update(deltaTime);
            }
        }
        //ToDo: Uncomment when ready
        //enemyShipsLaserBlasts.clear();

        //checking if enemy ship and players ship collides with each other
        //if they collide then player looses 1 shield and 1 point
        //enemy ship is destroyed

        if (Rect.intersects(enemyShip1.getEnemyShipRect(), playerShip.getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            enemyShip1.setyCoordinate(0);
            shieldsHandler.decrementShieldsLeft();
            decrementPointsScored();
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }
        }

        if (Rect.intersects(enemyShip2.getEnemyShipRect(), playerShip.getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            enemyShip2.setyCoordinate(0);
            shieldsHandler.decrementShieldsLeft();
            decrementPointsScored();
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }

        }
        if (Rect.intersects(enemyShip3.getEnemyShipRect(), playerShip.getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            enemyShip3.setyCoordinate(0);
            shieldsHandler.decrementShieldsLeft();
            decrementPointsScored();
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }
        }


        //Check if Player Ship and Meteor collided
        if (controller.isMeteorPlayerShipCollided(meteor1, playerShip))
        {   //Handle consequences
            audioConfig.playExplosionSound();
            meteor1.setYCoorinate();
            shieldsHandler.decrementShieldsLeft();
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }
        }

        //checking if enemy ship laser blasts have hit the players space ship
        //if so then player looses 1 shield and 1 point
        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++) {
            if (Rect.intersects(playerShip.getHitBox(), enemyShip1LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip1LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                shieldsHandler.decrementShieldsLeft();
                if (!shieldsHandler.areShieldsLeft()) {
                    gameEnded = true;
                }
            }
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++) {
            if (Rect.intersects(playerShip.getHitBox(), enemyShip2LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip2LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                shieldsHandler.decrementShieldsLeft();
                if (!shieldsHandler.areShieldsLeft()) {
                    gameEnded = true;
                }
            }
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++) {
            if (Rect.intersects(playerShip.getHitBox(), enemyShip3LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip3LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                shieldsHandler.decrementShieldsLeft();
                if (!shieldsHandler.areShieldsLeft()) {
                    gameEnded = true;
                }
            }
        }

        //checking if players laser blast has hit enemy's space ship
        //if so then player gets 1 point, and enemy ship is destroyed
        for (int i = 0; i < listOfRedLasers.size(); i++) {

            if (Rect.intersects(enemyShip1.getEnemyShipRect(), listOfRedLasers.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfRedLasers.get(i).setCoordinateY();
                enemyShip1.setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            if (Rect.intersects(enemyShip2.getEnemyShipRect(), listOfRedLasers.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfRedLasers.get(i).setCoordinateY();
                enemyShip2.setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            if (Rect.intersects(enemyShip3.getEnemyShipRect(), listOfRedLasers.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfRedLasers.get(i).setCoordinateY();
                enemyShip3.setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            //if players space ship's laser blast is out of the screen
            //then remove it, otherwise - update it's position
            if (listOfRedLasers.get(i).getCoordinateY() < 0) {
                listOfRedLasers.remove(listOfRedLasers.get(i));

            } else {
                listOfRedLasers.get(i).update(deltaTime);
            }
        }
    }

    private void draw()
    {
        if (surfaceHolder.getSurface().isValid())
        {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //Draw Ships
            drawElements.drawPlayerShip(paint, canvas, playerShip, targetButton, left);
            drawElements.drawEnemyShips(paint, canvas, enemyShip1, enemyShip2, enemyShip3);

            //Draw Buttons
            drawElements.drawLeftButton(paint, canvas, leftButton, right, left);
            drawElements.drawRightButton(paint, canvas, rightButton, rightForRightButton, leftForRightButton);
            drawElements.drawTargetButton(paint, canvas, targetButton, rightForRightButton, left);
            drawElements. drawStopButton(paint, canvas, stopButton, rightForRightButton);

            //Draw Laser blasts
            drawElements. drawPlayerLaserBlasts(paint, canvas, listOfRedLasers);
            drawElements. drawEnemyShip1LaserBlasts(paint, canvas, enemyShip1LaserBlasts);
            drawElements.drawEnemyShip2LaserBlasts(paint, canvas, enemyShip2LaserBlasts);
            drawElements.drawEnemyShip3LaserBlasts(paint, canvas, enemyShip3LaserBlasts);

            //Draw meteor
            drawElements.drawMeteor(paint, canvas, meteor1);

            //Information showed if game is still played or ended
            if (!gameEnded)
            {
                dashboard.gamePlayingInfo(paint, canvas, pointsHandler.getPoints(), shieldsHandler.getShieldsLeft());
            }
            else
            {
                dashboard.gameEndedInfo(paint, canvas, pointsHandler.getPoints(), highestScore, highestScoreWritter, rightForRightButton, leftForRightButton);
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
        } catch (InterruptedException e) {

        }
    }

    //Makes new thread and starts it
    public void resume()
    {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
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
                if ((x > right && x < right + leftButton.getButton().getWidth()) &&
                        (y > left - leftButton.getButton().getHeight() && y < left)) {
                    playerShip.goLeft();
                    //pause();
                }

                //checking if player clicked RIGHT button
                else if ((x <= rightForRightButton && x >= rightForRightButton - rightButton.getButton().getWidth()) &&
                        (y <= left && y >= left - rightButton.getButton().getHeight())) {
                    playerShip.goRight();

                    //resume();
                }

                //cheking if player clicked SHOOT button
                else if ((x <= rightForRightButton / 2 + targetButton.getButton().getWidth() / 2) &&
                        ((x >= rightForRightButton / 2 - (targetButton.getButton().getWidth() / 2)))
                        && (y <= left && (y >= left - rightButton.getButton().getHeight()))) {
                    audioConfig.getSoundPool().play(audioConfig.getLaserBlastSound(), 1, 1, 0, 0, 1);
                    numberOfShoots++;
                    if (numberOfShoots > 150) {
                        gameEnded = true;
                    }
                    Log.i("Number of laser Shoots" + numberOfShoots, "+++++++++");
                    listOfRedLasers.add(new RedLaser(gameActivity, (playerShip.getxCoordinate() + (playerShip.getBitmapWidth() / 2)) - (redLaser.getLaser().getWidth() / 2)
                            , playerShip.getyCoordinate()
                            - targetButton.getButton().getHeight() - playerShip.getRawPlayerShip().getHeight(), R.drawable.img_red_laser));

                } else if ((y > 0 && y < stopButton.getButton().getHeight()) && (x < rightForRightButton && x >
                        rightForRightButton - stopButton.getButton().getWidth())) {
                    if (playing == false) {
                        resume();
                    } else if (playing == true) {
                        pause();
                    }
                }
                //if player clicked somewhere else
                else {
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

    //Decrement points scored when enemy ship leaves screen
    private void decrementPointsScored()
    {
        if (enemyShip1.getyCoordinate() > gameActivity.getScreenSizeY())
            pointsHandler.decrementPoints();

        if (enemyShip2.getyCoordinate() > gameActivity.getScreenSizeY())
            pointsHandler.decrementPoints();

        if (enemyShip3.getyCoordinate() > gameActivity.getScreenSizeY())
            pointsHandler.decrementPoints();
    }

    //Generate random number to decide if enemy ship shoots
    private void generateRandomNumber(ArrayList<BlueLaser> listOfLaserBlasts, int x, int y)
    {
        int number = randomGenerator.nextInt(125);

        if (number == 1) {
            listOfLaserBlasts.add(new BlueLaser(gameActivity, (x + enemyShip1.getRawEnemyShip().getWidth() / 2) -
                    redLaser.getLaser().getWidth() / 2,
                    y + enemyShip1.getRawEnemyShip().getHeight() / 2, R.drawable.img_blue_laser));
        }
    }
}

