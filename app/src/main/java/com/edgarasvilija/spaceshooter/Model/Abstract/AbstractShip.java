package com.edgarasvilija.spaceshooter.Model.Abstract;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class AbstractShip
{
    protected int xCoordinate;
    protected int yCoordinate;

    protected Bitmap ship;
    protected Rect rectShip;

    public int getCoordinateX()
    {
        return xCoordinate;
    }

    public int getCoordinateY()
    {
        return yCoordinate;
    }

    public Rect getRectShip()
    {
        return rectShip;
    }

    public Bitmap getShip()
    {
        return ship;
    }
}
