package com.chatengine.chatengine;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    static ImageView dp;
    static TextView firstname,lastname,mobile;
    ImageButton edit_first,edit_last,edit_mobile;
    static User you;
    DBHandler dbHandler;
    static Activity activity;
    private String link = "http://www.kcdastrust.org/project_chatEngine/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Settings");
        RelativeLayout settingsHolder = (RelativeLayout) findViewById(R.id.settingsHolder);
        activity = this;
        dbHandler = new DBHandler(this,null,null,1);
        you = dbHandler.getUser();
        dp = (ImageView) settingsHolder.findViewById(R.id.dp);
        getBitmapFromURL(link+you.getImgURL());
        RelativeLayout firstnameHolder = (RelativeLayout) settingsHolder.findViewById(R.id.firstname_holder);
        firstname = (TextView) firstnameHolder.findViewById(R.id.firstname);
        firstname.setText(you.getFirstname());
        edit_first = (ImageButton) firstnameHolder.findViewById(R.id.edit_firstname);
        edit_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Enter first name");

                final EditText input = new EditText(SettingsActivity.this);
                builder.setView(input);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        you.setFirstname(input.getText().toString());
                        if(JDBCConnector.updateUser(you).getFirstname().equals(you.getFirstname())){
                            if(dbHandler.updateUser(you)>0){
                                firstname.setText(you.getFirstname());
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        RelativeLayout lastnameHolder = (RelativeLayout) settingsHolder.findViewById(R.id.lastname_holder);
        lastname = (TextView) lastnameHolder.findViewById(R.id.lastname);
        lastname.setText(you.getLastname());
        edit_last = (ImageButton) lastnameHolder.findViewById(R.id.edit_lastname);
        edit_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Enter last name");

                final EditText input = new EditText(SettingsActivity.this);
                builder.setView(input);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        you.setLastname(input.getText().toString());
                        if(JDBCConnector.updateUser(you).getLastname().equals(you.getLastname())){
                            if(dbHandler.updateUser(you)>0){
                                lastname.setText(you.getLastname());
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        RelativeLayout mobileHolder = (RelativeLayout) settingsHolder.findViewById(R.id.mobile_holder);
        mobile = (TextView) mobileHolder.findViewById(R.id.mobile);
        mobile.setText(you.getMobile());
        edit_mobile = (ImageButton) mobileHolder.findViewById(R.id.edit_mobile);
        edit_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Enter mobile");

                final EditText input = new EditText(SettingsActivity.this);
                builder.setView(input);

                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        you.setMobile(input.getText().toString());
                        if(JDBCConnector.updateUser(you).getMobile().equals(you.getMobile())){
                            if(dbHandler.updateUser(you)>0){
                                mobile.setText(you.getMobile());
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }

    public static void getBitmapFromURL(final String src) {
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
                    final Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    Log.e("Bitmap","returned");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dp.setImageBitmap(BitmapInterface.getRoundedShape(myBitmap));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Exception", e.getMessage());

                }
            }
        });
        thread.start();
    }
}
