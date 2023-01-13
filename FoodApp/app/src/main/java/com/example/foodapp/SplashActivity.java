package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startingIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(startingIntent);
                finish();
            }
        }, SPLASH_LENGTH);

    }
}