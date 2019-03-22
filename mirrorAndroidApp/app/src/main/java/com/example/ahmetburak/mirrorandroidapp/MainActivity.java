package com.example.ahmetburak.mirrorandroidapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.Timer;

public class MainActivity extends Activity {

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView;
        textView=(TextView)findViewById(R.id.mainText);
        textView.setText("Merhaba!");

        MainActivity x=new MainActivity();
        Class cls=x.getClass();

        Method[]methods=cls.getMethods();
        User user=new User();
        user.borsa=true;
        user.takvim=true;
        user.hava=true;

        timer=new Timer();


           /* if(user.borsa==true)
            {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        onScreen();
                        finish();
                    }
                },10000);
            }
            if(user.hava==true)
            {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        onScreen2();
                        finish();
                    }
                },10000);
            }
            if(user.takvim==true)
            {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        CallCalendar();
                        finish();
                    }
                },10000);
            } */

           onScreen();


    }


    public void onScreen() {
        // Handle navigation view item clicks here.
        Intent intent = new Intent(this, ScreenActivity.class);
        startActivity(intent);


    }
    public void onScreen2(){
        Intent intent2 = new Intent(this, Screen2Activity.class);
        startActivity(intent2);

    }
    public void CallCalendar(){
        Intent i1= new Intent(this,CalendarActivity.class);
        startActivity(i1);

    }


}

