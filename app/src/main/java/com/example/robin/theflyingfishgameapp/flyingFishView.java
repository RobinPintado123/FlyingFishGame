package com.example.robin.theflyingfishgameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.StreamCorruptedException;

//draw the birds the background and the fish
public class flyingFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    //yellow balls
    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int score, lifeCounterOfFish;

    //green balls
    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    //red balls
    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();


    private boolean touch = false;

    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    public flyingFishView(Context context){
        super(context);
        //fish
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        //background
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        //scores
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        //hearts
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        //dead heart
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3; //starts with three life
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //gets canvas width and height
        canvasWidth = canvas.getWidth();
        canvasHeight =canvas.getHeight();


        canvas.drawBitmap(backgroundImage,0,0,null);
        //drawing the fish

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() *3;
        fishY = fishY + fishSpeed;

        if(fishY < minFishY){
            fishY = minFishY;
        }
        if(fishY > maxFishY){
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed + 2;

        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;
        }else{
            canvas.drawBitmap(fish[0],fishX, fishY,null);

        }


//========================================Yellow Balls===================================================
        yellowX = yellowX - yellowSpeed;

        if(hitBallChecker(yellowX,yellowY)){
            score = score + 10; // gets ten points
            yellowX = - 100;
        }


        //random yellow balls
        if(yellowX < 0){
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }

        //create yellow ball
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);
//======================================End of Yellow Balls========================================


// ==============================Green Balls===========================================
        greenX = greenX - greenSpeed;

        if(hitBallChecker(greenX,greenY)){
            score = score + 20; // gets ten points
            greenX = - 100;
        }


        //random yellow balls
        if(greenX < 0){
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }

        //create green ball
        canvas.drawCircle(greenX, greenY, 25, greenPaint);
//================================================End of Green Ball================================


//=======================================Red Ball=================================================
        redX = redX - redSpeed;

        //checks if fish hits the red ball
        if(hitBallChecker(redX,redY)){
            redX = - 100;
            lifeCounterOfFish--;
        }
        //checks if life is zero
            if(lifeCounterOfFish == 0){
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                //sends user to game over activity
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score); // displays score on game over scren
                getContext().startActivity(gameOverIntent);


                }

        //random yellow balls
        if(redX < 0){
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }

        //create red ball
        canvas.drawCircle(redX, redY, 30, redPaint);

        //drawing the score board
       canvas.drawText("Score : " + score, 20, 60, scorePaint);

        for(int i=0; i < 3; i++){
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i < lifeCounterOfFish){
                // these are for the red hearts
                canvas.drawBitmap(life[0], x, y, null);
            }else{ //display when fish eats the red ball
                //these are the dead hearts
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
//================================================End of Red Ball================================


        //drawing the score board
       // canvas.drawText("Score : " + score, 20, 60, scorePaint);

        //distance between the red hearts
       // canvas.drawBitmap(life[0], 580, 10, null);
      //  canvas.drawBitmap(life[0], 780, 10, null);
       // canvas.drawBitmap(life[0], 980, 10, null);
    }

    public boolean hitBallChecker(int x, int y){
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY +fish[0].getHeight())){
            return true;
        }
        return false;
    }

    //checks if user touch the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;

            fishSpeed = -22;  //you can increase the speed of fish here by decreasing number to negative
        }

        return true;
    }
}
