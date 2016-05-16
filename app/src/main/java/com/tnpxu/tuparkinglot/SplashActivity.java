package com.tnpxu.tuparkinglot;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    public  Handler deleyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        deleyHandler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                return true;
            }
        }
        );


        deleyHandler.sendEmptyMessageDelayed(0, 700);

    }
}
