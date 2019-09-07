package com.edgarasvilija.spaceshooter.View;

import android.content.Context;

import com.edgarasvilija.spaceshooter.Model.EnemyShip;
import com.edgarasvilija.spaceshooter.Model.PlayerShip;

public class GameplayShips
{
    private PlayerShip playerShip;
    private EnemyShip enemyShip1;
    private EnemyShip enemyShip2;
    private EnemyShip enemyShip3;

    public void createShips(Context context, int rightForRightButton, int leftForRightButton)
    {
        createPlayerShip(context, rightForRightButton, leftForRightButton);
        createEnemyShips(context);
    }

    private void createPlayerShip(Context context, int rightForRightButton, int leftForRightButton)
    {
        playerShip = new PlayerShip(context, rightForRightButton/2 , leftForRightButton);
    }

    private void createEnemyShips(Context context)
    {
        enemyShip1 = new EnemyShip(context, 5, 0);
        enemyShip2 = new EnemyShip(context, 5, 0);
        enemyShip3 = new EnemyShip(context, 5, 0);
    }

    public PlayerShip getPlayerShip()
    {
        return playerShip;
    }

    public EnemyShip getEnemyShip1()
    {
        return enemyShip1;
    }
    public EnemyShip getEnemyShip2()
    {
        return enemyShip2;
    }
    public EnemyShip getEnemyShip3()
    {
        return enemyShip3;
    }
}
