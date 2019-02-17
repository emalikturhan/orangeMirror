package com.example.panta.orangemirror;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SettingsActivity extends Activity implements View.OnClickListener{


    private boolean time_module_enable_bool, weather_module_enable_bool,date_module_enable_bool;
    private String username, timeTxtSize, weatherTxtSize,dateTxtSize;
    private boolean hour24_enable,celcius_enable;
    JSONObject settings= new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //////////////////// ONLY FIRST TIME SHOW REGISTER PAGE ///////////////////////////////
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show sign up activity
            startActivity(new Intent(SettingsActivity.this, RegisterActivity.class));

        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        //////////////////////////////////////////////////////////////////////////////////////


        Intent intent = getIntent();


        //////////INITIALIZE SETTINGS VARIABLES /////////////
       // username = intent.getStringExtra("USERNAME");
        username = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("username", "");

        time_module_enable_bool = true;
        weather_module_enable_bool= true;
        date_module_enable_bool=true;

        timeTxtSize= ((TextView) findViewById(R.id.timeModule_textSizeInt)).getTextSize() +"";
        weatherTxtSize= ((TextView) findViewById(R.id.weatherModule_textSizeInt)).getTextSize() +"";
        dateTxtSize= ((TextView) findViewById(R.id.weatherModule_textSizeInt)).getTextSize() +"";

        hour24_enable=true;
        celcius_enable=true;

        /////////////////////////////////////////////////////

        Button timeDecrementBtn = (Button) findViewById(R.id.timeDecrementBtn);
        Button timeIncrementBtn = (Button) findViewById(R.id.timeIncrementBtn);

        Button weatherDecrementBtn = (Button) findViewById(R.id.weatherDecrementBtn);
        Button weatherIncrementBtn = (Button) findViewById(R.id.weatherIncrementBtn);

        Button dateDecrementBtn = (Button) findViewById(R.id.dateDecrementBtn);
        Button dateIncrementBtn = (Button) findViewById(R.id.dateIncrementBtn);


        Button saveBtn = (Button) findViewById(R.id.saveBtn);

        timeDecrementBtn.setOnClickListener(this);
        timeIncrementBtn.setOnClickListener(this);
        weatherDecrementBtn.setOnClickListener(this);
        weatherIncrementBtn.setOnClickListener(this);
        dateDecrementBtn.setOnClickListener(this);
        dateIncrementBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        final Switch time_module_enable = findViewById(R.id.time_module_enable);
        final Switch weather_module_enable = findViewById(R.id.weather_module_enable);
        final Switch date_module_enable = findViewById(R.id.date_module_enable);

        Switch hour24_switch = findViewById(R.id.hour24_switch);
        Switch celcius_switch = findViewById(R.id.celcius_switch);

        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                switch (v.getId()){
                    case R.id.time_module_enable:
                        LinearLayout layout1 = (LinearLayout) findViewById(R.id.timeModuleLayout);
                        if(isChecked){
                            enable(layout1);
                            time_module_enable_bool=true;
                        }else{
                            disable(layout1);
                            time_module_enable.setEnabled(true);
                            time_module_enable_bool=false;
                        }
                        break;

                    case R.id.weather_module_enable:
                        LinearLayout layout2 = (LinearLayout) findViewById(R.id.weatherModuleLayout);
                        if(isChecked){
                            enable(layout2);
                            weather_module_enable_bool= true;
                        }else{
                            disable(layout2);
                            weather_module_enable.setEnabled(true);
                            weather_module_enable_bool= false;
                        }
                        break;
                    case R.id.date_module_enable:
                        LinearLayout layout3 = (LinearLayout) findViewById(R.id.dateModuleLayout);
                        if(isChecked){
                            enable(layout3);
                            date_module_enable_bool= true;
                        }else{
                            disable(layout3);
                            date_module_enable.setEnabled(true);
                            date_module_enable_bool= false;
                        }
                        break;
                    case R.id.hour24_switch:
                        if(isChecked){
                            hour24_enable=true;
                        }else{
                            hour24_enable=false;
                        }

                        break;
                    case R.id.celcius_switch:
                        if(isChecked){
                            celcius_enable=true;
                        }else{
                            celcius_enable=false;
                        }
                        break;
                }
            }
        };


        time_module_enable.setOnCheckedChangeListener(multiListener);
        weather_module_enable.setOnCheckedChangeListener(multiListener);
        date_module_enable.setOnCheckedChangeListener(multiListener);

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

            case R.id.dateDecrementBtn:
                txtSizeView = (TextView) findViewById(R.id.dateModule_textSize_view);
                txtSize = (TextView) findViewById(R.id.dateModule_textSizeInt);
                decrement(txtSizeView,txtSize);
                break;
            case R.id.dateIncrementBtn:

                txtSizeView = (TextView) findViewById(R.id.dateModule_textSize_view);
                txtSize = (TextView) findViewById(R.id.dateModule_textSizeInt);
                increment(txtSizeView,txtSize);

                break;

            case R.id.saveBtn:
                settings=createJSON();


               //new SendDeviceDetails().execute("http://biyosecure.westeurope.cloudapp.azure.com:5000/settings", settings.toString());

               /* String jsonString ="";
                jsonString ="{\n" +
                        "  \"user_name\":\""+"AAAAA"+"\",\n"+
                        "  \"home\":\""+"dummyhsfsdome"+"\"\n"+
                        "}\n"
                ;

                new SendDeviceDetails().execute("http://biyosecure.westeurope.cloudapp.azure.com:5000/register",jsonString);

*/
                String jsonStr = settings.toString();
                System.out.println("jsonString101010: "+jsonStr);
                Toast.makeText(getApplicationContext(), jsonStr, Toast.LENGTH_LONG).show();

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
    private JSONObject createJSON(){

        ////////////////////////CREATING JSON OBJECT///////////////////////////////
        JSONObject timeModuleObj = new JSONObject();
        try {

            timeModuleObj.put("time_module_enable", time_module_enable_bool);
            timeModuleObj.put("timeTxtSize", timeTxtSize);
            timeModuleObj.put("hour24_enable", hour24_enable);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject weatherModuleObj = new JSONObject();
        try {
            weatherModuleObj.put("weather_module_enable", weather_module_enable_bool);
            weatherModuleObj.put("weatherTxtSize", weatherTxtSize);
            weatherModuleObj.put("celcius_enable", celcius_enable);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject dateModuleObj = new JSONObject();
        try {
            dateModuleObj.put("date_module_enable", date_module_enable_bool);
            dateModuleObj.put("dateTxtSize", dateTxtSize);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();

        jsonArray.put(timeModuleObj);
        jsonArray.put(weatherModuleObj);
       // jsonArray.put(dateModuleObj);


        try {
            settings.put("user_name", username);
            settings.put("settings", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return settings;
    }


    public void onBackPressed() {
        finish();
    }

}

