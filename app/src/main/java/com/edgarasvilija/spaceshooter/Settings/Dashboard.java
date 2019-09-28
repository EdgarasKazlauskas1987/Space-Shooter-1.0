package com.edgarasvilija.spaceshooter.Settings;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.edgarasvilija.spaceshooter.GameActivity;


public class Dashboard
{
    GameActivity gameActivity;
    private float pointsSize;
    private float resultSize;
    private float restartGameSize;

    public Dashboard(GameActivity gameActivity)
    {
        this.gameActivity = gameActivity;
    }

    public void gamePlayingInfo(Paint paint, Canvas canvas, int pointsScored, long timeLeft, int screenWidth)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.argb(255, 255, 255, 255));
        pointsSize = gameActivity.getTextSize(2.5f);
        paint.setTextSize(pointsSize);
        canvas.drawText("Points: " + pointsScored, 10, 20, paint);
        pointsSize = gameActivity.getTextSize(5.5f);
        paint.setTextSize(pointsSize);
        canvas.drawText("" + timeLeft, screenWidth - 65, 350, paint);
    }

    public void gameEndedInfo(Paint paint, Canvas canvas, int pointsScored, int highestScore, SharedPreferences.Editor highestScoreWritter,
        int screenWidth, int screenHeight )
    {
        if (pointsScored > highestScore)
        {
            highestScoreWritter.putInt("highestScore", pointsScored);
            highestScoreWritter.commit();
            highestScore = pointsScored;
        }

        resultSize = gameActivity.getTextSize(10);
        paint.setTextSize(resultSize);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Your Result: " + pointsScored, screenWidth / 2, screenHeight / 2, paint);
        restartGameSize = gameActivity.getTextSize(6);
        paint.setTextSize(restartGameSize);
        canvas.drawText("Press red button to play again", screenWidth / 2, screenHeight / 3, paint);
    }
}
