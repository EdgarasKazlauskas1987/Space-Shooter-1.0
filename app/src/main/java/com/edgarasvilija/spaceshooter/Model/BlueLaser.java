package com.edgarasvilija.spaceshooter.Model;


import com.edgarasvilija.spaceshooter.GameActivity;
import com.edgarasvilija.spaceshooter.Model.Abstract.AbstractLaser;


public class BlueLaser extends AbstractLaser
{
    public BlueLaser(GameActivity gameActivity, int xCoordinate, int yCoordinate, int drawableId)
    {
        super(gameActivity, xCoordinate, yCoordinate, drawableId);
    }

    public void setCoordinateY()
    {
        yCoordinate = 5000;
    }

    public void update(float deltaTime)
    {
        yCoordinate += 600 * deltaTime;

        // Refresh hit box location
        rectLaser.left = xCoordinate;
        rectLaser.top = yCoordinate;
        rectLaser.right = xCoordinate + laser.getWidth();
        rectLaser.bottom = yCoordinate + laser.getHeight();
    }
}
