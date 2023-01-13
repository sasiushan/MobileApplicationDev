package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent startingIntent = new Intent(SpashScreen.this, MainActivity.class);
                Intent startingIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(startingIntent);
                finish();
            }
        }, SPLASH_LENGTH);
    }
}