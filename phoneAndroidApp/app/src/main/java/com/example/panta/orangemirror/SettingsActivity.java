package com.example.panta.orangemirror;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button timeDecrementBtn = (Button) findViewById(R.id.timeDecrementBtn);
        Button timeIncrementBtn = (Button) findViewById(R.id.timeIncrementBtn);

        Button weatherDecrementBtn = (Button) findViewById(R.id.weatherDecrementBtn);
        Button weatherIncrementBtn = (Button) findViewById(R.id.weatherIncrementBtn);

        timeDecrementBtn.setOnClickListener(this);
        timeIncrementBtn.setOnClickListener(this);
        weatherDecrementBtn.setOnClickListener(this);
        weatherIncrementBtn.setOnClickListener(this);


        final Switch time_module_enable = findViewById(R.id.time_module_enable);
        final Switch weather_module_enable = findViewById(R.id.weather_module_enable);
        Switch hour24_switch = findViewById(R.id.hour24_switch);
        Switch celcius_switch = findViewById(R.id.celcius_switch);

        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                switch (v.getId()){
                    case R.id.time_module_enable:
                        LinearLayout layout1 = (LinearLayout) findViewById(R.id.timeModuleLayout);
                        if(isChecked){
                            enable(layout1);
                        }else{
                            disable(layout1);
                            time_module_enable.setEnabled(true);
                        }
                        break;

                    case R.id.weather_module_enable:
                        LinearLayout layout2 = (LinearLayout) findViewById(R.id.weatherModuleLayout);
                        if(isChecked){
                            enable(layout2);
                        }else{
                            disable(layout2);
                            weather_module_enable.setEnabled(true);
                        }
                        break;
                    case R.id.hour24_switch:
                        if(isChecked){
                           //TODO
                        }else{
                            //TODO
                        }

                        break;
                    case R.id.celcius_switch:
                        if(isChecked){
                            //TODO
                        }else{
                            //TODO
                        }
                        break;
                }
            }
        };


        time_module_enable.setOnCheckedChangeListener(multiListener);
        weather_module_enable.setOnCheckedChangeListener(multiListener);
        hour24_switch.setOnCheckedChangeListener(multiListener);
        celcius_switch.setOnCheckedChangeListener(multiListener);



    }

    @Override
    public void onClick(View v) {
        TextView txtSizeView;
        TextView txtSize;
        switch (v.getId()){

            case R.id.timeDecrementBtn:
                txtSizeView = (TextView) findViewById(R.id.timeModule_textSize_view);
                txtSize = (TextView) findViewById(R.id.timeModule_textSizeInt);
                decrement(txtSizeView,txtSize);

                break;
            case R.id.timeIncrementBtn:
                txtSizeView = (TextView) findViewById(R.id.timeModule_textSize_view);
                txtSize = (TextView) findViewById(R.id.timeModule_textSizeInt);
                increment(txtSizeView,txtSize);
                break;
            case R.id.weatherDecrementBtn:
                txtSizeView = (TextView) findViewById(R.id.weatherModule_textSize_view);
                txtSize = (TextView) findViewById(R.id.weatherModule_textSizeInt);
                decrement(txtSizeView,txtSize);
                break;
            case R.id.weatherIncrementBtn:

                txtSizeView = (TextView) findViewById(R.id.weatherModule_textSize_view);
                txtSize = (TextView) findViewById(R.id.weatherModule_textSizeInt);
                increment(txtSizeView,txtSize);

                break;


        }

    }
    public void decrement(TextView txtSizeView,TextView txtSize) {

        int currentSize = Integer.parseInt(txtSizeView.getText().toString());
        txtSizeView.setText(""+(Math.max(0,currentSize-1)));
        txtSize.setTextSize(currentSize-1);

    }

    public void increment(TextView txtSizeView,TextView txtSize) {
        int currentSize = Integer.parseInt(txtSizeView.getText().toString());
        txtSizeView.setText(""+(Math.max(0,currentSize+1)));
        txtSize.setTextSize(currentSize+1);
    }

    private static void enable(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                enable((ViewGroup) child);
            } else {
                child.setEnabled(true);
            }
        }
    }
    private static void disable(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disable((ViewGroup) child);
            } else {
                child.setEnabled(false);
            }
        }

    }


}
