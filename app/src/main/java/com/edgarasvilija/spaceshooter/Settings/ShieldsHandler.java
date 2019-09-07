package com.edgarasvilija.spaceshooter.Settings;

public class ShieldsHandler
{
    private volatile static int shieldsLeft;

    public void decrementShieldsLeft()
    {
        shieldsLeft--;
    }

    public void setShieldsLeft()
    {
        shieldsLeft = 3;
    }

    public boolean areShieldsLeft()
    {
        if (shieldsLeft < 0)
            return false;
        else
            return true;
    }

    public int getShieldsLeft()
    {
        return shieldsLeft;
    }
}
