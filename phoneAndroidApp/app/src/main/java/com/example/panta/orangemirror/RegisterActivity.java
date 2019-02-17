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


public class RegisterActivity extends AppCompatActivity {

    EditText name;
    Editable username;
    String home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.editText);
        username = name.getText();
        home="securify"; //default home
        Button submitBtn = (Button) findViewById(R.id.button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String jsonString ="";
                jsonString ="{\n" +
                        "  \"user_name\":\""+username.toString().trim()+"\",\n"+
                        "  \"home\":\""+home+"\"\n"+
                        "}\n"
                ;

                new SendDeviceDetails().execute("http://biyosecure.westeurope.cloudapp.azure.com:5000/register",jsonString);

                Intent mainIntent = new Intent(RegisterActivity.this, SettingsActivity.class);


                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putString("username", username.toString().trim()).commit();

                mainIntent.putExtra("USERNAME", username.toString().trim());


                RegisterActivity.this.startActivity(mainIntent);
                RegisterActivity.this.finish();

                Toast.makeText(getApplicationContext(), username+" ismiyle kaydoldunuz.", Toast.LENGTH_LONG).show();

            }
        });
    }

}


