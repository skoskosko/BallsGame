package com.example.skosko.ballsgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private int width;
    private int height;
    float degs = 0;
    float movment = 1;
    Canvas canvas;
    ImageView gameview;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            if (Build.VERSION.SDK_INT > 10) {
                findViewById(android.R.id.content).setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
        }


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x + 150;
        height = size.y;

        bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);

        ImageView newView = new ImageView(this);
        newView.setX(0);
        newView.setY(0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        newView.setLayoutParams(layoutParams);
        gameview = newView;

        //gameview = (ImageView) findViewById(R.id.imageViewGame);
        setContentView(gameview);

        Timer timer = new Timer();
        final int FPS = 24;
        TimerTask updateBall = new UpdateScreenTask();
        timer.scheduleAtFixedRate(updateBall, 0, 1000/FPS);



    }


    class UpdateScreenTask extends TimerTask {
        public void run() {
            //calculate the new position of myBall

            drawMap();

            if(degs < 180){
                degs+=movment;
            }else{
                degs = 0;
            }

        }
    }


    void drawMap(){

        canvas = new Canvas(bitmap);

        Paint bluesky = new Paint();
        bluesky.setColor(Color.BLUE);
        bluesky.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint greenplanet = new Paint();
        greenplanet.setColor(Color.GREEN);
        greenplanet.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint characterPaint = new Paint();
        characterPaint.setColor(Color.RED);
        characterPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint EnemyPaint = new Paint();
        EnemyPaint.setColor(Color.CYAN);
        EnemyPaint.setStyle(Paint.Style.FILL_AND_STROKE);



        RectF planet = new RectF(0,height/2,width,height/2+width);

        canvas.drawRect(0,0,width,height,bluesky);


        canvas.drawArc(planet,180,180,true,greenplanet);

        canvas.drawCircle(width/2,height/2,50,characterPaint);

        Cords cords = getCordsOndeg(planet,degs);

        canvas.drawRect(cords.x-50,cords.y-50,cords.x+50,cords.y+50,EnemyPaint);





        GameActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameview.setImageDrawable(null);
                gameview.draw(canvas);
                gameview.setImageBitmap(bitmap);
            }
        });





    }

    Cords getCordsOndeg(RectF circle, float Degs){ // 0-180 0 on oikeella 180 o vasemmalla
        float deg = (float)Math.toRadians(360 - Degs);
        Cords cords = new Cords((float)(circle.centerX() + circle.height()/2*Math.cos(deg)),(float) (circle.centerY() + circle.height()/2* Math.sin(deg)));
        return cords;
    }


    class Cords{
        float x;
        float y;

        Cords(float nx, float ny){
            x= nx;
            y = ny;
        }
    }



}
