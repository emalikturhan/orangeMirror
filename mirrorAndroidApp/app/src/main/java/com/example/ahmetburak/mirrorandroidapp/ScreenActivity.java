package com.example.ahmetburak.mirrorandroidapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ScreenActivity extends AppCompatActivity {
    ArrayList<String>hisseler=new ArrayList<>();
    ArrayList<String>degerler=new ArrayList<>();
    ArrayList<String>değişim;
    ArrayAdapter<String>adapter;
    String URL="https://tr.investing.com/equities/";
    private ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        hisseler.add("net-holding");
        hisseler.add("penguen-gida");
        lv=(ListView)findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,degerler);

        jsoups x=new jsoups();
        x.execute();

    }



    private class jsoups extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected Void doInBackground(Void... voids) {

            try {

                for(int i=0;i<hisseler.size();i++) {
                    Document doc = Jsoup.connect(URL+hisseler.get(i)).timeout(30 * 1000).get();

                    Elements hisse = doc.select("div.left");
                    degerler.add(hisseler.get(i)+"->"+hisse.get(1).text().substring(0,hisse.get(1).text().indexOf("+")));
                }

                for(int i=0;i<2;i++)
                {
                    Log.i("SASASA",degerler.get(i));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lv.setAdapter(adapter);

        }
    }
}


