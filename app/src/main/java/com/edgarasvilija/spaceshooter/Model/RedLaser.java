package com.edgarasvilija.spaceshooter.Model;


import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.Model.Abstract.AbstractLaser;


public class RedLaser extends AbstractLaser
{
    public RedLaser(GameActivity gameActivity, int xCoordinate, int yCoordinate, int drawableId)
    {
        super(gameActivity, xCoordinate, yCoordinate, drawableId);
    }

    public void setCoordinateY()
    {
        yCoordinate = -10;
    }

    public void update(float deltaTime)
    {
        yCoordinate -= 800 * deltaTime;

        // Refresh laser location
        rectLaser.left = xCoordinate;
        rectLaser.top = yCoordinate;
        rectLaser.right = xCoordinate + laser.getWidth();
        rectLaser.bottom = yCoordinate + laser.getHeight();
    }
}
