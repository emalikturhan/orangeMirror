package com.example.ahmetburak.mirrorandroidapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    public void CallCalendar(){
        Intent i1= new Intent(this,CalendarActivity.class);
        startActivity(i1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CallCalendar();

    }
}

