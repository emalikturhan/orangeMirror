package com.example.panta.orangemirror;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

//Calendar Activity çağırma örneği

public class MainActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CallCalendar();
    }



    public void CallCalendar(){
        Intent i1= new Intent(this,CalendarActivity.class);
        startActivity(i1);

    }


}

