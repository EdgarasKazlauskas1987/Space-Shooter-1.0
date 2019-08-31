package com.edgarasvilija.spaceshooter.Helper;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.edgarasvilija.spaceshooter.Model.BlueLaser;
import com.edgarasvilija.spaceshooter.Model.EnemyShip;
import com.edgarasvilija.spaceshooter.Model.LeftButton;
import com.edgarasvilija.spaceshooter.Model.Meteor;
import com.edgarasvilija.spaceshooter.Model.PlayerShip;
import com.edgarasvilija.spaceshooter.Model.RedLaser;
import com.edgarasvilija.spaceshooter.Model.RightButton;
import com.edgarasvilija.spaceshooter.Model.StopButton;
import com.edgarasvilija.spaceshooter.Model.TargetButton;

import java.util.List;

public class DrawElements
{
    public void drawPlayerShip(Paint paint, Canvas canvas, PlayerShip playerShip, TargetButton button, int left)
    {
        canvas.drawBitmap(playerShip.getRawPlayerShip(), playerShip.getxCoordinate(),
                left - button.getButton().getHeight() - playerShip.getRawPlayerShip().getHeight(), paint);
    }

    //ToDo: Change to List of Enemy Ships
    public void drawEnemyShips(Paint paint, Canvas canvas, EnemyShip ship1, EnemyShip ship2, EnemyShip ship3)
    {
        canvas.drawBitmap(ship1.getRawEnemyShip(), ship1.getxCoordinate(), ship1.getyCoordinate(), paint);
        canvas.drawBitmap(ship2.getRawEnemyShip(), ship2.getxCoordinate(), ship2.getyCoordinate(), paint);
        canvas.drawBitmap(ship3.getRawEnemyShip(), ship3.getxCoordinate(), ship3.getyCoordinate(), paint);
    }

    //ToDo: Change to List of Meteors
    public void drawMeteors(Paint paint, Canvas canvas, Meteor meteor1, Meteor meteor2)
    {
        canvas.drawBitmap(meteor1.getRawMeteor(), meteor1.getXCoordinate(), meteor1.getYCoordinate(), paint);
        canvas.drawBitmap(meteor2.getRawMeteor(), meteor2.getXCoordinate(), meteor2.getYCoordinate(), paint);
    }

    public void drawLeftButton(Paint paint, Canvas canvas, LeftButton button, int right, int left)
    {
        canvas.drawBitmap(button.getButton(), right, left - button.getButton().getHeight(), paint);
    }

    public void drawRightButton(Paint paint, Canvas canvas, RightButton button, int rightForRightButton, int leftForRightButton)
    {
        canvas.drawBitmap(button.getButton(), rightForRightButton -
                button.getButton().getWidth(), leftForRightButton -
                button.getButton().getHeight(), paint);
    }

    public void drawTargetButton(Paint paint, Canvas canvas, TargetButton button, int rightForRightButton, int left)
    {
        canvas.drawBitmap(button.getButton(), ((rightForRightButton / 2) - (button.getButton().getWidth() / 2)),
                left - button.getButton().getHeight(), paint);
    }

    public void drawStopButton(Paint paint, Canvas canvas, StopButton button, int rightForRightButton)
    {
        canvas.drawBitmap(button.getButton(), rightForRightButton - button.getButton().getWidth(), 0, paint);
    }

    public void drawPlayerLaserBlasts(Paint paint, Canvas canvas, List<RedLaser> listOfRedLasers)
    {
        for (int i = 0; i < listOfRedLasers.size(); i++) {
            canvas.drawBitmap(listOfRedLasers.get(i).getLaser(), listOfRedLasers.get(i).getCoordinateX(),
                    listOfRedLasers.get(i).getCoordinateY(), paint);
        }
    }

    public void drawEnemyShip1LaserBlasts(Paint paint, Canvas canvas, List<BlueLaser> enemyShip1LaserBlasts)
    {
        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++) {
            canvas.drawBitmap(enemyShip1LaserBlasts.get(i).getLaser(), enemyShip1LaserBlasts.get(i).getCoordinateX(),
                    enemyShip1LaserBlasts.get(i).getCoordinateY(), paint);
        }
    }

    public void drawEnemyShip2LaserBlasts(Paint paint, Canvas canvas, List<BlueLaser> enemyShip2LaserBlasts)
    {
        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++) {
            canvas.drawBitmap(enemyShip2LaserBlasts.get(i).getLaser(), enemyShip2LaserBlasts.get(i).getCoordinateX(),
                    enemyShip2LaserBlasts.get(i).getCoordinateY(), paint);
        }
    }

    public void drawEnemyShip3LaserBlasts(Paint paint, Canvas canvas, List<BlueLaser> enemyShip3LaserBlasts)
    {
        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++) {
            canvas.drawBitmap(enemyShip3LaserBlasts.get(i).getLaser(), enemyShip3LaserBlasts.get(i).getCoordinateX(),
                    enemyShip3LaserBlasts.get(i).getCoordinateY(), paint);
        }
    }
}
