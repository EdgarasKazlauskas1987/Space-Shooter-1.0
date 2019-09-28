package com.edgarasvilija.spaceshooter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
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

        //Start Game Activity
        Button startButton = (Button) findViewById(R.id.playButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Start Credits activity
        Button creditsButton = (Button) findViewById(R.id.buttonCredits);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreditsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Quit game
        Button quitButton = (Button) findViewById(R.id.buttonQuit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}
