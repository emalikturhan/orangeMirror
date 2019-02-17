package com.example.panta.orangemirror;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String jsonString =" {\"user_name\":\"armut\",\"settings\":[{\"time_module_enable\":false,\"timeTxtSize\":\"210.0\",\"hour24_enable\":true},{\"weather_module_enable\":false,\"weatherTxtSize\":\"210.0\",\"celcius_enable\":true}]}\n";


                new SendDeviceDetails().execute("http://biyosecure.westeurope.cloudapp.azure.com:5000/settings",jsonString);


                Toast.makeText(getApplicationContext(), "basıldı: "+ jsonString, Toast.LENGTH_LONG).show();

            }
        });



    }
}
