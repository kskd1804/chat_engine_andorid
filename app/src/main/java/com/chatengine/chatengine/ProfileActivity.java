package com.chatengine.chatengine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    User u;
    ListView profileList;
    String key[],value[];
    ProfileArrayAdapter profileArrayAdapter;
    static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        final String email = extras.getString("email");
        final Bitmap bitmap = (Bitmap) new Intent().getParcelableExtra("bitmap");
        setContentView(R.layout.activity_profile);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        activity = this;
        //If network avaialable...
        if(NetworkStatus.getInstance(this).isOnline()){
            u = JDBCConnector.getUser(email);
            if(u.getStatus().equals("online")) {
                setTitle(Html.fromHtml("<font size='15px'>"+u.getFirstname() + " " + u.getLastname()+"</font><br><font size='10px'>online</font>"));
            }else{
                setTitle(Html.fromHtml("<font size='15px'>"+u.getFirstname() + " " + u.getLastname()+"</font><br><font size='10px'>last seen at: "+u.getLast_online()+"</font>"));
            }
            Drawable d = new BitmapDrawable(getResources(),bitmap);
            appBarLayout.setBackground(d);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileActivity.this,ChatWindowActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("bitmap",bitmap);
                    startActivity(intent);
                }
            });
            View v = findViewById(R.id.profile_view);
            profileList = (ListView)v.findViewById(R.id.detailsView);
            updateList();
        }else{
            setTitle(email);
            appBarLayout.setBackgroundResource(R.drawable.profile_00);
            fab.setVisibility(View.GONE);
        }
    }

    private void updateList(){
        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading details...Please wait!");
        progressDialog.setCanceledOnTouchOutside(false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                key = new String[3];
                value = new String[3];
                key[0] = "Name";
                key[1] = "Email";
                key[2] = "Mobile";
                value[0] = u.getFirstname()+" "+u.getLastname();
                value[1] = u.getEmail();
                value[2] = u.getMobile();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        profileArrayAdapter = new ProfileArrayAdapter(getApplication().getApplicationContext(),key,value);
                        profileList.setAdapter(profileArrayAdapter);
                        profileArrayAdapter.notifyDataSetChanged();
                    }
                });
                progressDialog.dismiss();
            }
        });
    }
}
