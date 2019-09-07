package com.edgarasvilija.spaceshooter.View;

import android.content.Context;

import com.edgarasvilija.spaceshooter.Model.LeftButton;
import com.edgarasvilija.spaceshooter.Model.RightButton;
import com.edgarasvilija.spaceshooter.Model.StopButton;
import com.edgarasvilija.spaceshooter.Model.TargetButton;
import com.edgarasvilija.spaceshooter.R;

public class GameplayButtons
{
    private LeftButton leftButton;
    private RightButton rightButton;
    private TargetButton targetButton;
    private StopButton stopButton;

    public void createButtons(Context context, int rightForRightButton, int leftForRightButton)
    {
        leftButton = new LeftButton(context, rightForRightButton, 0, R.drawable.img_left_button);
        rightButton = new RightButton(context, rightForRightButton, 0, R.drawable.img_right_button);
        targetButton = new TargetButton(context, rightForRightButton, leftForRightButton, R.drawable.img_target);
        stopButton = new StopButton(context, rightForRightButton, leftForRightButton, R.drawable.img_stop_button);
    }

    public LeftButton getLeftButton()
    {
        return leftButton;
    }

    public RightButton getRightButton()
    {
        return rightButton;
    }

    public TargetButton getTargetButton()
    {
        return targetButton;
    }

    public StopButton getStopButton()
    {
        return stopButton;
    }
}
