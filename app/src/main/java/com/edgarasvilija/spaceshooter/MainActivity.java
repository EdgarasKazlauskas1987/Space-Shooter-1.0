package com.edgarasvilija.spaceshooter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs;

        prefs = getSharedPreferences("HiScores", MODE_PRIVATE);

        final TextView textFastestTime = (TextView)findViewById(R.id.textView);

        int bestResult = prefs.getInt("highestScore", 1);
        textFastestTime.setText("Best Result:  " + bestResult);

        //Getting reference to Play button in layout
        Button playButton = (Button) findViewById(R.id.playButton);
        //Listening for clicks
        playButton.setOnClickListener(this);

        Button quitButton = (Button) findViewById(R.id.buttonQuit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    //After clicking Play button
    @Override
    public void onClick(View view)
    {
        //Intent object will let us switch between activities
        Intent intent = new Intent(this, GameActivity.class);
        //Starting new activity
        startActivity(intent);
        //Finishing this(layout) activity
        finish();
    }
}
