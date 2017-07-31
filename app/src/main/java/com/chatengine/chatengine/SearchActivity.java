package com.chatengine.chatengine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {

    static String[] name,status,email,last_online,imgRes;
    static Bitmap[] imgURL,imgURLU;
    static int len;
    static Thread thread;
    static EditText search;
    static Button search_btn;
    static ListView searchList;
    static ProgressBar progressBar;
    static Activity activity;
    User user;
    DBHandler dbHandler;

    static int update=0;
    static Bitmap img=null;
    private String link = "http://kcdastrust.org/project_chatEngine/";



    static  SearchArrayAdapter searchArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        activity = this;
        thread = null;
        dbHandler = new DBHandler(this,null,null,1);
        user = dbHandler.getUser();
        setSupportActionBar(toolbar);
        if(NetworkStatus.getInstance(this).isOnline()){
            search = (EditText) toolbar.findViewById(R.id.search);
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.searchListHolder);
            searchList = (ListView) rl.findViewById(R.id.searchList);
            progressBar = (ProgressBar) rl.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            search_btn = (Button) toolbar.findViewById(R.id.search_btn);
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateList(search.getText().toString().trim());
                }
            });
            searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(), ChatWindowActivity.class);
                    i.putExtra("email", email[position]);
                    i.putExtra("img", link + imgRes[position]);
                    startActivity(i);
                    finish();
                    System.exit(0);
                }
            });


        }else{
            Toast.makeText(getApplicationContext(),"No Internet Connection! Redirecting to home...",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
            System.exit(0);
        }
    }

    private void updateList(final String key){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                User[] users = JDBCConnector.getUsers(key);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                if(users!=null){
                    len=users.length;
                    name = new String[len];
                    status = new String[len];
                    email = new String[len];
                    last_online = new String[len];
                    imgRes = new String[len];
                    imgURL = new Bitmap[len];
                    imgURLU = new Bitmap[len];
                    int i=0;
                    while(i<len)
                    {
                        name[i] = users[i].getFirstname()+" "+users[i].getLastname();
                        status[i] = users[i].getStatus();
                        email[i] = users[i].getEmail();
                        last_online[i] = users[i].getLast_online();
                        imgRes[i] = users[i].getImgURL();
                        getBitmapFromURL(link+users[i].getImgURL());
                        while(true)
                        {
                            if(update==1)
                            {
                                imgURLU[i] = img;
                                break;
                            }
                        }
                        imgURL[i] = BitmapInterface.getRoundedShape(imgURLU[i]);
                        i++;
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            searchArrayAdapter = new SearchArrayAdapter(activity.getApplicationContext(),name,status,last_online,imgURL);
                            searchList.setAdapter(searchArrayAdapter);
                            searchArrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    public static void getBitmapFromURL(final String src) {
        img = null;
        update=0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("src", src);
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    Log.e("Bitmap","returned");
                    img = myBitmap;
                    update=1;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Exception", e.getMessage());
                    img = null;
                    update=1;
                }
            }
        });
        thread.start();
    }
}
