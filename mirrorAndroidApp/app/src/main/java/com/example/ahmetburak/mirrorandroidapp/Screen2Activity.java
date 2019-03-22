package com.example.ahmetburak.mirrorandroidapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Screen2Activity extends AppCompatActivity {
    TextView temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        temperature=(TextView)findViewById(R.id.temperature);


        getWeather weather=new getWeather();
        weather.execute();

    }

    private class getWeather extends AsyncTask<Void, Void, Void>{

        int temperatureNo;
        String cityName="ankara";

        protected void onPreExecute(){
            super.onPreExecute();
        }
        protected Void doInBackground(Void... params){
            String url="http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&APPID=485492b80171aada0e5d376d46584e4a";
            JSONObject jsonObject=null;

            try{
                String json=JsonParser.getJSONFromUrl(url);
                try{
                    jsonObject=new JSONObject(json);
                }
                catch (JSONException e){
                    Log.e("JsonParser","Error creating json object");
                }


                JSONObject mainObject=jsonObject.getJSONObject("main");
                temperatureNo=mainObject.getInt("temp");
            }
            catch (JSONException e){
                Log.e("json","background");
            }

            return null;
        }
        protected void onPostExecute(Void args){
            temperature.setText(temperatureNo-273+"\u2103");
        }

    }
}