package com.example.robin.theflyingfishgameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //make splash screen appear for 5 seconds
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000); // 5 seconds
                }catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    //sends splash screen to main
                    Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                }

            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
