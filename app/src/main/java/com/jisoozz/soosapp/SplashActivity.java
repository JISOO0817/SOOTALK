package com.jisoozz.soosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();
        handler.postDelayed(new splashhandler(),2000);
    }

    private class splashhandler implements Runnable{

        public void run(){
            startActivity(new Intent(getApplication(),Login_Activity.class));
            SplashActivity.this.finish();
        }

    }
}