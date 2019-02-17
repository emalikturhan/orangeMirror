package com.example.panta.orangemirror;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



class SendDeviceDetails extends AsyncTask<String,String,String> {
    protected String doInBackground (String ... params){
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        String file = "";

        if(params.length>1){
            try{
                URL url = new URL(params[0]);

                JSONObject jsonObject = new JSONObject();

                String jsonString =params[1];

                jsonObject = new JSONObject(jsonString);


                Log.i("JSON", jsonObject.toString());


                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                //connection.setRequestProperty("Accept","application/json");
                //connection.setRequestProperty("Authentication","Bearer Q70lWlPwbjFQDWHcPkvZSrx1RTReqvu9DuW/Ff1JO1cZNhVb7d5ekJ2ra0eJ4PF1au7HtX3NTxIiyFwXEORxow==");

                //connection.setRequestProperty("Authorization","Bearer Q70lWlPwbjFQDWHcPkvZSrx1RTReqvu9DuW/Ff1JO1cZNhVb7d5ekJ2ra0eJ4PF1au7HtX3NTxIiyFwXEORxow==");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();




                System.out.print("AAAAAAAAAAAAAAAAAAAA:   " + jsonObject.toString());
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
                String response = sb.toString();
                reader.close();
                 System.out.println("RESPONSE: "+response);


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
    protected void onPostExecute(String s){
        Log.d("fromPostExecute",s);
       // System.out.print("onPostExecute-------> "+s);
    }
}