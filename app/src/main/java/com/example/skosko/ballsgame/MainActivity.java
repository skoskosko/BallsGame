package com.example.skosko.ballsgame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MediaPlayer backroundmpm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//comment
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        } else {
//            if (Build.VERSION.SDK_INT > 10) {
//                findViewById(android.R.id.content).setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//            }
//        }


        Button startGame = (Button) findViewById(R.id.buttonStartGame);
        Button highScores = (Button) findViewById(R.id.buttonHighScore);
        Button settings = (Button) findViewById(R.id.buttonSettings);


        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);

                startActivity(gameIntent);

            }
        });

        highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highIntent = new Intent(getApplicationContext(), HighScores.class);

                startActivity(highIntent);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highIntent = new Intent(getApplicationContext(), Settings.class);

                startActivity(highIntent);

            }
        });



    }

    @Override
    public void onResume(){

        super.onResume();

        // put your code here...

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        System.exit(0);
        finish();
    }




}
