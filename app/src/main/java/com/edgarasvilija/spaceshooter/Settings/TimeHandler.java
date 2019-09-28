package com.edgarasvilija.spaceshooter.Settings;


import android.os.SystemClock;

public class TimeHandler
{
    public static long TimeLeft(long levelTime, long elapsedTime)
    {
        return levelTime - ((SystemClock.elapsedRealtime() - elapsedTime)/1000);
    }
}
