package com.edgarasvilija.spaceshooter.Controller;

import android.graphics.Rect;

import com.edgarasvilija.spaceshooter.Model.Meteor;
import com.edgarasvilija.spaceshooter.Model.PlayerShip;

public class GameplayController
{
    private volatile static int shieldsLeft;

    //ToDo more abstract method which fits all Objects
    public boolean isMeteorPlayerShipCollided(Meteor meteor, PlayerShip ship)
    {
        if(Rect.intersects(meteor.getHitBox(), ship.getHitBox()))
            return true;
        else
            return false;
    }
}
