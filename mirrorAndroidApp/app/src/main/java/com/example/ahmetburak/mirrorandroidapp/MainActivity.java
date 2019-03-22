package com.example.ahmetburak.mirrorandroidapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    Timer timer;
    User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView;
        textView=(TextView)findViewById(R.id.mainText);
        textView.setText("Merhaba!");

        MainActivity x=new MainActivity();
        Class cls=x.getClass();

        Method[]methods=cls.getMethods();

        user.borsa=true;
        user.takvim=true;
        user.hava=true;

        timer=new Timer();

        if(user.borsa==true && user.hava==true && user.takvim ==true )
            ttt();
        else if(user.borsa==true && user.hava==true && user.takvim ==false)
            ttf();
        else if(user.borsa==true && user.hava==false && user.takvim ==true)
            tft();
        else if(user.borsa==false && user.hava==true && user.takvim ==true)
            ftt();
        else if(user.borsa==true && user.hava==false && user.takvim ==false)
            onScreen();
        else if(user.borsa==false && user.hava==true && user.takvim ==false)
            onScreen2();
        else if(user.borsa==false && user.hava==false && user.takvim ==true)
            CallCalendar();








        /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScreen();
                finish();
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScreen2();
                finish();
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onScreen();
                finish();
            }
        }, 5000); */




       /*     if (user.borsa == true) {
                onScreen();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                       finish();
                    }
                }, 5000);
            }
            if (user.hava == true) {

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        onScreen2();
                        finish();

                    }
                }, 10000);
            }


           /* if (user.takvim == true) {
                CallCalendar();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5000);
            } */





    }
    public void tft(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScreen();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        CallCalendar();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                tft();;
                                finish();
                            }
                        }, 5000);
                        finish();
                    }
                }, 5000);
                finish();
            }
        }, 5000);
    }
    public void ttf(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScreen();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        onScreen2();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                ttf();
                                finish();
                            }
                        }, 5000);
                        finish();
                    }
                }, 5000);
                finish();
            }
        }, 5000);
    }
    public void ftt(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScreen2();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        CallCalendar();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                ftt();
                                finish();
                            }
                        }, 5000);
                        finish();
                    }
                }, 5000);
                finish();
            }
        }, 5000);
    }

    public void ttt(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onScreen();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        onScreen2();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                CallCalendar();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        ttt();
                                        finish();
                                    }
                                }, 5000);
                                finish();
                            }
                        }, 5000);
                        finish();
                    }
                }, 5000);
                finish();
            }
        }, 5000);

    }

    public void onScreen() {
        // Handle navigation view item clicks here.
        Intent intent = new Intent(this, ScreenActivity.class);
        startActivity(intent);


    }
    public void onScreen2(){
        Intent intent2 = new Intent(this, Screen2Activity.class);
        startActivity(intent2);

    }
    public void CallCalendar(){
        Intent i1= new Intent(this,CalendarActivity.class);
        startActivity(i1);

    }

    public void sendPost(){
        try{
            URL url=new URL("http://biyosecure.westeurope.cloudapp.azure.com:5000/settings");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

