package com.edgarasvilija.spaceshooter.Settings;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Dashboard
{
    public void gamePlayingInfo(Paint paint, Canvas canvas, int pointsScored, int shieldsLeft)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(20);
        canvas.drawText("Points scored: " + pointsScored, 10, 20, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(20);
        canvas.drawText("Lives img_left_button: " + shieldsLeft, 10, 200, paint);
    }

    public void gameEndedInfo(Paint paint, Canvas canvas, int pointsScored, int highestScore, SharedPreferences.Editor highestScoreWritter,
        int rightForRightButton, int leftForRightButton )
    {
        if (pointsScored > highestScore)
        {
            highestScoreWritter.putInt("highestScore", pointsScored);
            highestScoreWritter.commit();
            highestScore = pointsScored;
        }

        paint.setTextSize(80);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Your Result: " + pointsScored, rightForRightButton / 2, leftForRightButton / 2, paint);
        canvas.drawText("Press red button to play again " + pointsScored, rightForRightButton / 2, leftForRightButton / 4, paint);
    }
}
