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
import com.edgarasvilija.spaceshooter.Model.BlueLaser;
import com.edgarasvilija.spaceshooter.Model.RedLaser;
import com.edgarasvilija.spaceshooter.Model.Meteor;
import com.edgarasvilija.spaceshooter.Model.Shield;
import com.edgarasvilija.spaceshooter.R;
import com.edgarasvilija.spaceshooter.Settings.AudioConfiguration;
import com.edgarasvilija.spaceshooter.Settings.Dashboard;
import com.edgarasvilija.spaceshooter.Settings.PointsHandler;
import com.edgarasvilija.spaceshooter.Settings.ShieldsHandler;
import com.edgarasvilija.spaceshooter.Settings.TimeHandler;

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

    private RedLaser redLaser;
    private Meteor meteor1;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private int screenWidth;
    private int screenHeight;

    private final int xLeftCorner = 0;
    private int framesPerSecond = 0;

    private static ArrayList<RedLaser> listOfRedLasers = new ArrayList<>() ;
    private static ArrayList<BlueLaser> enemyShip1LaserBlasts = new ArrayList<>();
    private static ArrayList<BlueLaser> enemyShip2LaserBlasts = new ArrayList<>();
    private static ArrayList<BlueLaser> enemyShip3LaserBlasts = new ArrayList<>();
    private static ArrayList<Shield> shields = new ArrayList<>();

    AudioConfiguration audioConfig;
    Dashboard dashboard = new Dashboard();
    DrawElements drawElements = new DrawElements();
    GameplayController controller = new GameplayController();
    ShieldsHandler shieldsHandler = new ShieldsHandler();
    PointsHandler pointsHandler = new PointsHandler();

    GameplayButtons gameplayButtons = new GameplayButtons();
    GameplayShips gameplayShips = new GameplayShips();
    GameplayShields gameplayShields = new GameplayShields();

    //TimeHandler timeHandler;

    public Gameplay(GameActivity gameActivity, int screenWidth, int screenHeight, int rightForPlayerShip)
    {
        super(gameActivity);
        this.gameActivity = gameActivity;
        this.context = gameActivity;

        //Getting Highest Score file and if it does not exist creating it
        highestScoreLoader = context.getSharedPreferences("HiScores", context.MODE_PRIVATE);
        highestScoreWritter = highestScoreLoader.edit();
        //Getting Highest Score and if its not there then result is 0
        highestScore = highestScoreLoader.getInt("highestScore", 0);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        surfaceHolder = getHolder();
        paint = new Paint();

        startGame();
    }

    public void startGame()
    {
        pointsHandler.setPoints();
        shieldsHandler.setShieldsLeft();
        numberOfShoots = 0;

        gameplayButtons.createButtons(context, screenWidth, screenHeight);
        gameplayShips.createShips(context, screenWidth, screenHeight);
        gameplayShields.createShields(shields, context, screenWidth, screenHeight);

        redLaser = new RedLaser(gameActivity, screenWidth, screenHeight, R.drawable.img_red_laser);
        meteor1 = new Meteor(context, randomGenerator.nextInt(gameActivity.getScreenWidth() + 1), 0);
        listOfRedLasers = new ArrayList<>();
        enemyShip1LaserBlasts = new ArrayList<>();
        audioConfig = new AudioConfiguration(context);

/*        timeHandler = new TimeHandler(30000, 1000);
        timeHandler.start();*/
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

            generateRandomNumber(enemyShip1LaserBlasts, gameplayShips.getEnemyShip1().getxCoordinate(), gameplayShips.getEnemyShip1().getyCoordinate());
            generateRandomNumber(enemyShip2LaserBlasts, gameplayShips.getEnemyShip2().getxCoordinate(), gameplayShips.getEnemyShip2().getyCoordinate());
            generateRandomNumber(enemyShip3LaserBlasts, gameplayShips.getEnemyShip3().getxCoordinate(), gameplayShips.getEnemyShip3().getyCoordinate());

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
        //Log.d("Edgaras","Seconds left: " + timeHandler.getCountdownMilliseconds() );
        decrementPointsScored();

        gameplayShips.getPlayerShip().update(deltaTime, screenHeight - gameplayButtons.getTargetButton().getButton().getHeight() - gameplayShips.getPlayerShip().getRawPlayerShip().getHeight());

        gameplayShips.getEnemyShip1().update(deltaTime);
        gameplayShips.getEnemyShip2().update(deltaTime);
        gameplayShips.getEnemyShip3().update(deltaTime);
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
            if (enemyShip1LaserBlasts.get(i).getCoordinateY() > gameActivity.getScreenHeight()) {
                enemyShip1LaserBlasts.remove(enemyShip1LaserBlasts.get(i));

            } else {
                enemyShip1LaserBlasts.get(i).update(deltaTime);
            }
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++) {
            if (enemyShip2LaserBlasts.get(i).getCoordinateY() > gameActivity.getScreenHeight()) {
                enemyShip2LaserBlasts.remove(enemyShip2LaserBlasts.get(i));

            } else {
                enemyShip2LaserBlasts.get(i).update(deltaTime);
            }
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++) {
            if (enemyShip3LaserBlasts.get(i).getCoordinateY() > gameActivity.getScreenHeight()) {
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

        if (Rect.intersects(gameplayShips.getEnemyShip1().getEnemyShipRect(), gameplayShips.getPlayerShip().getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            gameplayShips.getEnemyShip1().setyCoordinate(0);
            shieldsHandler.decrementShieldsLeft();
            gameplayShields.removeShield(shields);
            decrementPointsScored();
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }
        }

        if (Rect.intersects(gameplayShips.getEnemyShip2().getEnemyShipRect(), gameplayShips.getPlayerShip().getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            gameplayShips.getEnemyShip2().setyCoordinate(0);
            shieldsHandler.decrementShieldsLeft();
            gameplayShields.removeShield(shields);
            decrementPointsScored();
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }

        }
        if (Rect.intersects(gameplayShips.getEnemyShip3().getEnemyShipRect(), gameplayShips.getPlayerShip().getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            gameplayShips.getEnemyShip3().setyCoordinate(0);
            shieldsHandler.decrementShieldsLeft();
            gameplayShields.removeShield(shields);
            decrementPointsScored();
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }
        }


        //Check if Player Ship and Meteor collided
        if (controller.isMeteorPlayerShipCollided(meteor1, gameplayShips.getPlayerShip()))
        {   //Handle consequences
            audioConfig.playExplosionSound();
            meteor1.setYCoorinate();
            shieldsHandler.decrementShieldsLeft();
            gameplayShields.removeShield(shields);
            if (!shieldsHandler.areShieldsLeft()) {
                gameEnded = true;
            }
        }

        //checking if enemy ship laser blasts have hit the players space ship
        //if so then player looses 1 shield and 1 point
        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++) {
            if (Rect.intersects(gameplayShips.getPlayerShip().getHitBox(), enemyShip1LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip1LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                shieldsHandler.decrementShieldsLeft();
                gameplayShields.removeShield(shields);
                if (!shieldsHandler.areShieldsLeft()) {
                    gameEnded = true;
                }
            }
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++) {
            if (Rect.intersects(gameplayShips.getPlayerShip().getHitBox(), enemyShip2LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip2LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                shieldsHandler.decrementShieldsLeft();
                gameplayShields.removeShield(shields);
                if (!shieldsHandler.areShieldsLeft()) {
                    gameEnded = true;
                }
            }
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++) {
            if (Rect.intersects(gameplayShips.getPlayerShip().getHitBox(), enemyShip3LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip3LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                shieldsHandler.decrementShieldsLeft();
                gameplayShields.removeShield(shields);
                if (!shieldsHandler.areShieldsLeft()) {
                    gameEnded = true;
                }
            }
        }

        //checking if players laser blast has hit enemy's space ship
        //if so then player gets 1 point, and enemy ship is destroyed
        for (int i = 0; i < listOfRedLasers.size(); i++) {

            if (Rect.intersects(gameplayShips.getEnemyShip1().getEnemyShipRect(), listOfRedLasers.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfRedLasers.get(i).setCoordinateY();
                gameplayShips.getEnemyShip1().setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            if (Rect.intersects(gameplayShips.getEnemyShip2().getEnemyShipRect(), listOfRedLasers.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfRedLasers.get(i).setCoordinateY();
                gameplayShips.getEnemyShip2().setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            if (Rect.intersects(gameplayShips.getEnemyShip3().getEnemyShipRect(), listOfRedLasers.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfRedLasers.get(i).setCoordinateY();
                gameplayShips.getEnemyShip3().setyCoordinate(0);
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
            drawElements.drawPlayerShip(paint, canvas, gameplayShips.getPlayerShip(), gameplayButtons.getTargetButton(), screenHeight);
            drawElements.drawEnemyShips(paint, canvas, gameplayShips.getEnemyShip1(), gameplayShips.getEnemyShip2(), gameplayShips.getEnemyShip3());

            //Draw Buttons
            drawElements.drawLeftButton(paint, canvas, gameplayButtons.getLeftButton(), xLeftCorner, screenHeight);
            drawElements.drawRightButton(paint, canvas, gameplayButtons.getRightButton(), screenWidth, screenHeight);
            drawElements.drawTargetButton(paint, canvas, gameplayButtons.getTargetButton(), screenWidth, screenHeight);
            drawElements. drawStopButton(paint, canvas, gameplayButtons.getStopButton(), screenWidth);

            //Draw Shields
            drawElements.drawShields(paint, canvas, shields, screenHeight);

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
                dashboard.gameEndedInfo(paint, canvas, pointsHandler.getPoints(), highestScore, highestScoreWritter, screenWidth, screenHeight);
                pause();
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        //timeHandler.cancel();
        try {
            gameThread.join(10);
        } catch (InterruptedException e) {

        }
    }

    //Makes new thread and starts it
    public void resume()
    {
        playing = true;
/*        timeHandler = new TimeHandler(timeHandler.getCountdownMilliseconds(), 1000);
        timeHandler.start();*/
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
                if ((x > xLeftCorner && x < xLeftCorner + gameplayButtons.getLeftButton().getButton().getWidth()) &&
                        (y > screenHeight - gameplayButtons.getLeftButton().getButton().getHeight() && y < screenHeight)) {
                    gameplayShips.getPlayerShip().goLeft();
                    //pause();
                }

                //checking if player clicked RIGHT button
                else if ((x <= screenWidth && x >= screenWidth - gameplayButtons.getRightButton().getButton().getWidth()) &&
                        (y <= screenHeight && y >= screenHeight - gameplayButtons.getRightButton().getButton().getHeight())) {
                    gameplayShips.getPlayerShip().goRight();

                    //resume();
                }

                //cheking if player clicked SHOOT button
                else if ((x <= screenWidth / 2 + gameplayButtons.getTargetButton().getButton().getWidth() / 2) &&
                        ((x >= screenWidth / 2 - (gameplayButtons.getTargetButton().getButton().getWidth() / 2)))
                        && (y <= screenHeight && (y >= screenHeight - gameplayButtons.getRightButton().getButton().getHeight()))) {
                    audioConfig.getSoundPool().play(audioConfig.getLaserBlastSound(), 1, 1, 0, 0, 1);
                    numberOfShoots++;
                    if (numberOfShoots > 150) {
                        gameEnded = true;
                    }
                    Log.i("Number of laser Shoots" + numberOfShoots, "+++++++++");
                    listOfRedLasers.add(new RedLaser(gameActivity, (gameplayShips.getPlayerShip().getxCoordinate() + (gameplayShips.getPlayerShip().getBitmapWidth() / 2)) - (redLaser.getLaser().getWidth() / 2)
                            , gameplayShips.getPlayerShip().getyCoordinate()
                            - gameplayButtons.getTargetButton().getButton().getHeight() - gameplayShips.getPlayerShip().getRawPlayerShip().getHeight(), R.drawable.img_red_laser));

                } else if ((y > 0 && y < gameplayButtons.getStopButton().getButton().getHeight()) && (x < screenWidth && x >
                        screenWidth - gameplayButtons.getStopButton().getButton().getWidth())) {
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
                gameplayShips.getPlayerShip().standStill();
                break;
        }
        return true;
    }

    //Decrement points scored when enemy ship leaves screen
    private void decrementPointsScored()
    {
        if (gameplayShips.getEnemyShip1().getyCoordinate() > gameActivity.getScreenHeight())
            pointsHandler.decrementPoints();

        if (gameplayShips.getEnemyShip2().getyCoordinate() > gameActivity.getScreenHeight())
            pointsHandler.decrementPoints();

        if (gameplayShips.getEnemyShip3().getyCoordinate() > gameActivity.getScreenHeight())
            pointsHandler.decrementPoints();
    }

    //Generate random number to decide if enemy ship shoots
    private void generateRandomNumber(ArrayList<BlueLaser> listOfLaserBlasts, int x, int y)
    {
        int number = randomGenerator.nextInt(125);

        if (number == 1) {
            listOfLaserBlasts.add(new BlueLaser(gameActivity, (x + gameplayShips.getEnemyShip1().getRawEnemyShip().getWidth() / 2) -
                    redLaser.getLaser().getWidth() / 2,
                    y + gameplayShips.getEnemyShip1().getRawEnemyShip().getHeight() / 2, R.drawable.img_blue_laser));
        }
    }
}

