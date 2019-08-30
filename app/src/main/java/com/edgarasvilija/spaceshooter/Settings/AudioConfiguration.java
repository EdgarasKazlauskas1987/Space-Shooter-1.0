package com.edgarasvilija.spaceshooter.Settings;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class AudioConfiguration
{
    private static SoundPool soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC, 0);

    private int explosionSound = -1;
    private int destroyedSound = -1;
    private int hitSound = -1;
    private int laserBlastSound = -1;

    public AudioConfiguration(Context context)
    {
        setSoundConfiguration(context);
    }

    public SoundPool getSoundPool()
    {
        return soundPool;
    }

    public int getExplosionSound()
    {
        return explosionSound;
    }

    public int getDestoyedSound()
    {
        return destroyedSound;
    }

    public int getHitSound()
    {
        return hitSound;
    }

    public int getLaserBlastSound()
    {
        return laserBlastSound;
    }

    private void setSoundConfiguration(Context context)
    {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor assetFileDescriptor;

            //Setting game sounds
            assetFileDescriptor = assetManager.openFd("collision.ogg");
            destroyedSound = soundPool.load(assetFileDescriptor, 0);

            assetFileDescriptor = assetManager.openFd("explosion.ogg");
            explosionSound = soundPool.load(assetFileDescriptor, 0);

            assetFileDescriptor = assetManager.openFd("hit.ogg");
            hitSound = soundPool.load(assetFileDescriptor, 0);

            assetFileDescriptor = assetManager.openFd("laserShot.ogg");
            laserBlastSound = soundPool.load(assetFileDescriptor, 0);
        }

        catch (IOException e)
        {

        }
    }
}
