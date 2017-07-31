package com.chatengine.chatengine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    static int update=0;
    static Bitmap img=null;
    ListView recentChats;
    RecentChatsArrayAdapter recentChatsArrayAdapter;
    static String[] name,msg,time,status,imgURL,email,sent_recd;
    static Bitmap[] bitmap;
    private String LINK = "http://kcdastrust.org/project_chatEngine/";
    private static Message last;
    private DBHandler dbHandler;
    private User user;
    private static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("ChatEngine");
        dbHandler = new DBHandler(this,null,null,1);
        user = dbHandler.getUser();
        last = new Message();
        last.setMsg("No Message to show");
        last.setDatetime("0000-00-00 00:00:00 AM");
        user.setStatus("online");
        activity = this;
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        recentChats = (ListView) rl.findViewById(R.id.recentChats);
        User you = dbHandler.getUser();
        you.setStatus("online");
        Intent i = new Intent(HomeActivity.this,SyncService.class);
        startService(i);
        if(dbHandler.updateUser(you) > 0){
            Log.i("cE","Updated User Internally");
            User u = JDBCConnector.updateUser(you);
            if(u!=null){
                if(u.getStatus().equals("online"))
                Log.i("cE","Updated User Externally");
            }else{
                Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_LONG).show();
            }
        }
        recentChats.setLongClickable(true);
        recentChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChatWindowActivity.class);
                intent.putExtra("email", email[position]);
                startActivity(intent);
            }
        });
        recentChats.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = {"Delete Chat", "Cancel"};
                final int x = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Delete Chat")) {
                            dbHandler.removeChat(email[position]);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        dbHandler = new DBHandler(getApplicationContext(),null,null,1);
        updateList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setStatus("offline");
        user.setLast_online(df.format(new Date()));
        if(dbHandler.updateUser(user) > 0){
            Log.i("cE","Updated User Internally");
            User u = JDBCConnector.updateUser(user);
            if(u!=null){
                if(u.getStatus().equals("offline"))
                Log.i("cE","Updated User Externally");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.settings){
            Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);
        } else if(id==R.id.offline){
            Intent intent = new Intent(HomeActivity.this,OfflineActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateList(){
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message m = dbHandler.getLastMessage();
                if(!last.getMsg().equals(m.getMsg()) && !last.getDatetime().equals(m.getDatetime())) {
                    Message[] recents = dbHandler.getRecents();
                    int i;
                    if (recents != null) {
                        i=recents.length;
                        if (i > 0) {
                            name = new String[i];
                            time = new String[i];
                            msg = new String[i];
                            status = new String[i];
                            imgURL = new String[i];
                            email = new String[i];
                            sent_recd = new String[i];
                            bitmap = new Bitmap[i];
                            for (int x = 0; x < i;x++) {
                                email[x] = recents[x].getFrom_name();
                                time[x] = recents[x].getDatetime();
                                msg[x] = recents[x].getMsg();
                                status[x] = recents[x].getStatus();
                                imgURL[x] = recents[x].getImgURL();
                                imgURL[x] = LINK + imgURL[x];
                                sent_recd[x] = recents[x].getSent_recd();
                                User u=null;
                                while(u==null){
                                    u = JDBCConnector.getUser(recents[x].getFrom_name());
                                }
                                name[x] = u.getFirstname() + " " + u.getLastname();
                                Bitmap internal = BitmapInterface.loadBitmap(getApplicationContext(), email[x]);
                                if (internal == null) {
                                    getBitmapFromURL(imgURL[x]);
                                    while (true) {
                                        if (update == 1) {
                                            BitmapInterface.saveFile(getApplicationContext(), img, email[x]);
                                            bitmap[x] = BitmapInterface.getRoundedShape(img);
                                            break;
                                        }
                                    }
                                } else {
                                    bitmap[x] = BitmapInterface.getRoundedShape(internal);
                                }
                                if (x == i-1) {
                                    last = recents[x];
                                }
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recentChatsArrayAdapter = new RecentChatsArrayAdapter(activity.getApplicationContext(), user.getEmail(), name, email, msg, time, status, imgURL, bitmap, sent_recd);
                                    recentChats.setAdapter(recentChatsArrayAdapter);
                                    recentChatsArrayAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            }
        };
        t.schedule(task,3000);
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
