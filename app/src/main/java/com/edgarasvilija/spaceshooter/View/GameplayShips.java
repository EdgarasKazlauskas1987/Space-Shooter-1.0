package com.edgarasvilija.spaceshooter.View;

import android.content.Context;

import com.edgarasvilija.spaceshooter.Model.EnemyShip;
import com.edgarasvilija.spaceshooter.Model.PlayerShip;

import java.util.ArrayList;
import java.util.Arrays;

public class GameplayShips
{
    private PlayerShip playerShip;
    private EnemyShip enemyShip1;
    private EnemyShip enemyShip2;
    private EnemyShip enemyShip3;
    private ArrayList<EnemyShip> enemyShips = new ArrayList<>();


    public void createShips(Context context, int screenWidth, int screenHeight)
    {
        createPlayerShip(context, screenWidth, screenHeight);
        createEnemyShips(context);
    }

    private void createPlayerShip(Context context, int screenWidth, int screenHeight)
    {
        playerShip = new PlayerShip(context, screenWidth/2 , screenHeight);
    }

    private void createEnemyShips(Context context)
    {
        enemyShip1 = new EnemyShip(context);
        enemyShip2 = new EnemyShip(context);
        enemyShip3 = new EnemyShip(context);
        enemyShips.addAll(Arrays.asList(enemyShip1, enemyShip2, enemyShip3));
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

    public ArrayList<EnemyShip> getAllEnemyShips()
    {
        return enemyShips;
    }
}
