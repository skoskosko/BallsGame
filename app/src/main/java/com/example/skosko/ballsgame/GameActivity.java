package com.example.skosko.ballsgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private int width;
    private int height;
    float degs = 0;
    List<Float> enemeys =new ArrayList<Float>();
    float movment;
    Canvas canvas;
    ImageView gameview;
    Bitmap bitmap;
    Bitmap bitmapbackground;
    Paint bluesky;
    Paint greenplanet;
    Paint characterPaint;
    Paint EnemyPaint;
    Paint ScorePaint;
    RectF planet;
    boolean jumping;
    boolean falling;
    int jumpHeight = 0;
    Bitmap enemybitmap;
    int seconds = 0;
    float speed = (float)0.5;
    int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);

// SET WINDOW

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

//// SET PAINTS
        bluesky = new Paint();
        bluesky.setColor(Color.BLUE);
        bluesky.setStyle(Paint.Style.FILL_AND_STROKE);

        greenplanet = new Paint();
        greenplanet.setColor(Color.GREEN);
        greenplanet.setStyle(Paint.Style.FILL_AND_STROKE);

        characterPaint = new Paint();
        characterPaint.setColor(Color.RED);
        characterPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        EnemyPaint = new Paint();
        EnemyPaint.setColor(Color.CYAN);
        EnemyPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        ScorePaint = new Paint();
        ScorePaint.setColor(Color.RED);
        ScorePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        ScorePaint.setTextSize(100);

        // Make static items




// get display size

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;


// fill location list on planet

//        for(int i = 0 ; i < 180 ; i++){
//            Cords cords = getCordsOndeg(planet,i);
//            planetcordlist.add(cords);
//        }

// MAKE GAME VIEW



        ImageView newView = new ImageView(this);
        newView.setX(0);
        newView.setY(0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        newView.setLayoutParams(layoutParams);

        RelativeLayout relview = new RelativeLayout(this);
        relview.setLayoutParams(layoutParams);
        relview.addView(newView);
        gameview = newView;

        //gameview = (ImageView) findViewById(R.id.imageViewGame);
        Button jump = new Button(this);
        jump.setWidth((int) ( width/2));
        jump.setHeight(height/4);
        jump.setX(width/4);
        jump.setY(height /4 *3);
        jump.setText("!JUMP!");
        relview.addView(jump);
        setContentView(relview);


        // Draw empty field
        //bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        //canvas = new Canvas(bitmap);
        //canvas.drawRect(0,0,width,height,bluesky);
        //planet = new RectF(0,height/2,width,height/2+width);
        //canvas.drawArc(planet,180,180,true,greenplanet);

        bitmapbackground = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapbackground);
        canvas2.drawRect(0,0,width,height,bluesky);
        planet = new RectF(0,height/2,width,height/2+width);
        canvas2.drawArc(planet,180,180,true,greenplanet);

        // DRAW ENEMY AND MAKE MATRIX
        // enemy

        enemybitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas enemyccanvas = new Canvas(enemybitmap);
        enemyccanvas.drawRect(0,0,100,100,EnemyPaint); // enemy

        enemeys.add((float)0);
        // START TIMER

        Timer timer = new Timer();
        final int FPS = 30;
        TimerTask updateBall = new UpdateScreenTask();
        timer.scheduleAtFixedRate(updateBall, 0, 1000/FPS);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor vatupassisensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (vatupassisensor == null) {
            // ei kiihtyvyysanturia
            Toast.makeText(this, "Paskaks män", Toast.LENGTH_SHORT).show();
            return;
        }

        sensorManager.registerListener(this,vatupassisensor, SensorManager.SENSOR_DELAY_NORMAL);


        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!jumping && !falling){
                    jumping = true;
                }

            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        movment = sensorEvent.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

int frames;
    class UpdateScreenTask extends TimerTask {
        public void run() {
            //calculate the new position of myBall
               frames++;
            if(frames == 30){
                enemeys.add((float)0);
                seconds++;
                frames =0;
            }
            drawMap();


            //List<Object> toRemove = new ArrayList<Object>();
            for (int index = 0; index < enemeys.size(); index++) {

                if(enemeys.get(index) < 180){
                    enemeys.set(index, enemeys.get(index)+movment*speed);

                }else{
                    points ++;
                    enemeys.remove(index);
                    //enemeys.set(index, Float.valueOf(0));
                }

            }
           // enemeys.removeAll(toRemove);



//            if( degs < 0 ){
//                degs = 179;
//            }else if(degs < 180){
//                degs+=movment*speed;
//            }else{
//                degs = 0;
//            }
        }
    }

    void jump(){
        if(jumping){
            jumpHeight+=(height/3-jumpHeight)/2;
            if(jumpHeight > height/3-10) {
                jumping = false;
                falling = true;
            }
        }else if(falling){
            jumpHeight-=height/40;
            if(jumpHeight < 0) {
                jumpHeight = 0;
                falling = false;
            }
        }
    }
// DRAWING THINGS EVERY SECOND
    void drawMap(){
        jump();



        bitmap = Bitmap.createBitmap(bitmapbackground);
        canvas = new Canvas(bitmap);


        canvas.drawCircle(width/2,height/2-jumpHeight,50,characterPaint); // player




        for(float a: enemeys){
            Cords cords = getCordsOndeg(planet,a);
            android.graphics.Matrix matrix = new android.graphics.Matrix();

            matrix.postRotate(-a,cords.x,cords.y);
            matrix.preTranslate(cords.x-50,cords.y-50);
            canvas.drawBitmap(enemybitmap,matrix,EnemyPaint);


            //toRemove.add(a);

        }

        canvas.drawText(points + "" , 100 , 100 ,ScorePaint);

        //canvas.drawRect(cords.x-50,cords.y-50,cords.x+50,cords.y+50,EnemyPaint); // enemy
        //83 - 96

        drawstuff();


    }

    void drawstuff(){

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
