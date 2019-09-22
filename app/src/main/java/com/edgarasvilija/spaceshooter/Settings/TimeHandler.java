package com.edgarasvilija.spaceshooter.Settings;

import android.os.CountDownTimer;


public class TimeHandler extends CountDownTimer
{
    private volatile long countdownMilliseconds ;

    public TimeHandler(long duration, long interval)
    {
        super(duration, interval);
    }

    @Override
    public void onTick(long l)
    {
        countdownMilliseconds = l;
    }

    @Override
    public void onFinish() {

    }

    public long getCountdownMilliseconds()
    {
        return countdownMilliseconds;
    }
}
