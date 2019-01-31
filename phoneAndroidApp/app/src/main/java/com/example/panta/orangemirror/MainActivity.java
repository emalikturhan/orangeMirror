package com.example.panta.orangemirror;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    EditText name;
    Editable username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.editText);
        username = name.getText();


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //  startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                Intent mainIntent = new Intent(MainActivity.this, SettingsActivity.class);
                mainIntent.putExtra("USERNAME", username.toString());
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        });
    }
}