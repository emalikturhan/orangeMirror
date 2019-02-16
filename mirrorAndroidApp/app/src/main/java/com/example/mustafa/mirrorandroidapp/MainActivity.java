package com.example.mustafa.mirrorandroidapp;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Button get = findViewById(R.id.button);
        final TextView textView = findViewById(R.id.textView);


        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText= (EditText) findViewById(R.id.editText);
                String name = editText.getText().toString();
                EditText editText2= (EditText) findViewById(R.id.editText);
                String home = editText.getText().toString();
                String str = "";
                try {
                    str = new background().execute("http://biyosecure.westeurope.cloudapp.azure.com:5000/settings",name,home).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(str.length() < 1){
                    textView.setText("user couldn't find");
                }
                else{
                    textView.setText("SETTINGS:\n"+str);
                }
            }
        });
    }

    class background extends AsyncTask<String,String,String> {
        protected String doInBackground (String ... params){
            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            String file = "";

            if(params.length>1){
                try{
                    URL url = new URL(params[0]);
                    Log.d("adsgfdhfsadurl",params[0]);
                    String name = params[1];
                    String home = params[2];

                    /*JSONObject jsonObject = new JSONObject();
                    jsonObject.put("user_name",name);
                    jsonObject.put("home",home);*/
                    String jsonString =" {\"user_name\":\"armut\",\"settings\":[{\"time_module_enable\":false,\"timeTxtSize\":\"210.0\",\"hour24_enable\":true},{\"weather_module_enable\":false,\"weatherTxtSize\":\"210.0\",\"celcius_enable\":true}]}\n";
                    JSONObject jsonObject = new JSONObject(jsonString);





                    Log.d("JSON", jsonObject.toString());


                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.connect();





                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                    dos.writeBytes(jsonObject.toString());
                    dos.flush();
                    dos.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine()) != null)
                    {
                        // Append server response in string
                        sb.append(line + "\n");
                    }
                    String text = sb.toString();
                    reader.close();

                    JSONObject jsonResponse = new JSONObject(text);
                    Log.d("JSON",jsonResponse.toString());
                    String settings = jsonResponse.getString("settings");
                    file = settings;
                    System.out.println(settings);


                    //String result = jsonResponse.get("Scored Labels");
                    //System.out.println(result);


                    Log.i("STATUS", String.valueOf(connection.getResponseCode()));
                    Log.i("MSG" , connection.getResponseMessage());

                    connection.disconnect();


                    //file = connection.getResponseMessage();
                }catch (Exception e){

                }
                return file;
            }
            else{
                try{
                    URL url = new URL(params[0]);
                    Log.d("url",params[0]);

                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        Log.d("line:",line);
                        file += line;
                    }
                }catch (Exception e){

                }
                return file;
            }

        }

    }

}
