package com.example.ahmetburak.mirrorandroidapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer=new Timer();


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScreen();
                finish();
            }
        },10000);


    }


    public void onScreen() {
        // Handle navigation view item clicks here.
        Intent intent = new Intent(this, ScreenActivity.class);
        startActivity(intent);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScreen2();
                finish();
            }
        },10000);

    }
    public void onScreen2(){
        Intent intent2 = new Intent(this, Screen2Activity.class);
        startActivity(intent2);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                CallCalendar();
                finish();
            }
        },5000);
    }
    public void CallCalendar(){
        Intent i1= new Intent(this,CalendarActivity.class);
        startActivity(i1);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScreen();
                finish();
            }
        },5000);
    }


}

