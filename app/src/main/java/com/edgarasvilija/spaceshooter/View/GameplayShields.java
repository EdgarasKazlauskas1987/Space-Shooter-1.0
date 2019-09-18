package com.edgarasvilija.spaceshooter.View;

import android.content.Context;

import com.edgarasvilija.spaceshooter.Model.Shield;
import com.edgarasvilija.spaceshooter.R;

import java.util.ArrayList;
import java.util.Arrays;

public class GameplayShields
{
    private Shield shield1;

    private Shield shield2;
    private Shield shield3;

    public void createShields(ArrayList<Shield> shields, Context context, int rightForRightButton, int leftForRightButton)
    {
        shield1 = new Shield(context, rightForRightButton, leftForRightButton, R.drawable.img_shield);
        shield2 = new Shield(context, rightForRightButton, leftForRightButton, R.drawable.img_shield);
        shield3 = new Shield(context, rightForRightButton, leftForRightButton, R.drawable.img_shield);

        shields.addAll(Arrays.asList(shield1, shield2, shield3));
    }

    public void removeShield(ArrayList<Shield> shields)
    {
        if (shields.size() > 0)
            shields.remove(shields.size() - 1);
    }
}
