package com.chatengine.chatengine;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(new DBHandler(this,null,null,1).getUser()!=null){
            Timer t = new Timer();
            TimerTask tt = new TimerTask(){
                public void run(){
                    Intent intent =  new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
            };
            t.schedule(tt, 5000);
        }else{
            requestLogin();
        }

    }

    private void requestLogin() {
        Timer t = new Timer();
        TimerTask tt = new TimerTask(){
            public void run(){
                Intent intent =  new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        };
        t.schedule(tt,5000);
    }

}

