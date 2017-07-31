package com.chatengine.chatengine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ChatWindowActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    static MessageArrayAdapter messageArrayAdapter;
    static String[] from,to,message,time,status,sent_recd,msg_type;
    User u;
    User you;
    private static Message last;
    static String email,imgUrl;
    static DBHandler dbHandler;
    private static Activity activity;

    static ListView chatList;
    EditText messageText;
    ImageView send;
    static int count = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        imgUrl = extras.getString("img");
        last = new Message();
        dbHandler = new DBHandler(this,null,null,1);
        you = dbHandler.getUser();
        last.setMsg("No Message to show");
        last.setDatetime("0000-00-00 00:00:00");
        activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatWindowActivity.this,ProfileActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        setTitle(email);
        if(NetworkStatus.getInstance(this).isOnline()) {
            u = JDBCConnector.getUser(email);
            if (u.getStatus().equals("online")) {
                setTitle(u.getFirstname()+" "+u.getLastname());
                toolbar.setSubtitle("online");
            }
            else {
                setTitle(u.getFirstname()+" "+u.getLastname());
                toolbar.setSubtitle("last seen at " + u.getLast_online());
            }
        }else{
            setTitle(email);
        }
        RelativeLayout chatListHolder = (RelativeLayout) findViewById(R.id.chatListHolder);
        chatList = (ListView) chatListHolder.findViewById(R.id.chaList);
        chatList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    count += 50;
                }
            }
        });
        chatList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = {"Message Details", "Delete Message", "Cancel"};
                final View x = view;
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatWindowActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("MessageDetails")) {
                            dialog.dismiss();
                            AlertDialog.Builder details = new AlertDialog.Builder(ChatWindowActivity.this);
                            details.setTitle("Message Details");
                            details.setCancelable(true);
                            details.setIcon(android.R.drawable.ic_menu_info_details);
                            details.setMessage("From: " + from[position] + "\n" + "To: " + to[position] + "\nTime: " + time[position]);
                            details.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog d = details.create();
                            d.show();
                        } else if (items[which].equals("Delete Message")) {
                            Message m = new Message(from[position],to[position],message[position],time[position],"","","","");
                            dbHandler.removeMessage(m);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        chatListHolder = (RelativeLayout) chatListHolder.findViewById(R.id.rl_input);
        messageText = (EditText) chatListHolder.findViewById(R.id.message);
        send = (ImageView) chatListHolder.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                if(!message.equals("")){
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                    String date = df.format(new Date());
                    message=message.replace(",",";");
                    Message msg = new Message(you.getEmail(),email,message,"s", date,you.getImgURL(),"Sending","img");
                    if(dbHandler.addMessage(msg)>0){
                        msg.setStatus("SENT");
                        String res = JDBCConnector.addMessage(msg);
                        if(res.equals("Query Successful!")){
                            dbHandler.updateMessage(msg);
                            messageText.setText("");
                        }else{
                            Toast.makeText(getApplicationContext(),"Message could not be sent. Error: "+res,Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        updateMessages();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DBHandler dbHandler = new DBHandler(this,null,null,1);
        User user=dbHandler.getUser();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.profile){
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        else if(id==R.id.send_image){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("video/*, images/*");
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }else if(id==R.id.remove_chat){
            if(dbHandler.removeChat(email)>0){
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
                System.exit(0);
            }else{
                Toast.makeText(getApplicationContext(),"Could not remove chat!",Toast.LENGTH_LONG).show();
            }
        }else if(id==android.R.id.home){
            Intent intent = new Intent(ChatWindowActivity.this,HomeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Intent intent = new Intent(ChatWindowActivity.this,ImageConfirmActivity.class);
            intent.putExtra("path",picturePath);
            intent.putExtra("email",email);
            startActivity(intent);
        }
    }

    private void updateMessages(){
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!last.getMsg().equals(dbHandler.getLastMessage().getMsg()) && !last.getDatetime().equals(dbHandler.getLastMessage().getDatetime())){
                    Message[] messages = dbHandler.getMessage(email,count);
                    if(messages!=null) {
                        int i = messages.length;
                        if (i != 0) {
                            from = new String[i];
                            to = new String[i];
                            message = new String[i];
                            time = new String[i];
                            msg_type = new String[i];
                            status = new String[i];
                            sent_recd = new String[i];
                            Log.i("ChatWindowActivity", "Length of messages: " + i);
                            SimpleDateFormat dd = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                            for (int y = 0; y < i; y++) {
                                from[y] = messages[y].getFrom_name();
                                to[y] = messages[y].getTo_name();
                                message[y] = messages[y].getMsg().replace(";", ",");
                                time[y] = messages[y].getDatetime();
                                msg_type[y] = messages[y].getMsg_type();
                                status[y] = messages[y].getStatus();
                                sent_recd[y] = messages[y].getSent_recd();
                                if (y == i - 1) {
                                    last = messages[y];
                                }
                            }
                        /*for (int x = i - 1, y = 0; x >= 0; x--, y++) {
                            from[y] = messages[x].getFrom_name();
                            to[y] = messages[x].getTo_name();
                            message[y] = messages[x].getMsg();
                            time[y] = messages[x].getDatetime();
                            try {
                                Date d = dd.parse(messages[x].getDatetime());
                                timeDate[y] = df.format(d);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            msg_type[y] = messages[x].getMsg_type();
                            status[y] = messages[x].getStatus();
                            sent_recd[y] = messages[x].getSent_recd();
                            if(x==0){
                                last = messages[x];
                            }
                        }*/
                            DBHandler dbHandler = new DBHandler(getApplicationContext(),null,null,1);
                            Message[] messages1 = dbHandler.getMessages();
                            if(messages!=null){
                                for(i=0;i<messages.length;i++){
                                    Log.i("ChatWindowActivity",messages1[i].getFrom_name());
                                    Log.i("ChatWindowActivity",messages1[i].getTo_name());
                                    Log.i("ChatWindowActivity",messages1[i].getMsg());
                                    Log.i("ChatWindowActivity",messages1[i].getDatetime());
                                    Log.i("ChatWindowActivity",messages1[i].getStatus());
                                    Log.i("ChatWindowActivity","*-*-*-*-*-*-*-*");
                                }
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    messageArrayAdapter = new MessageArrayAdapter(activity.getApplicationContext(),you.getEmail() ,from,to,message, status, msg_type);
                                    chatList.setAdapter(messageArrayAdapter);
                                    messageArrayAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                    Message m = new Message(email,dbHandler.getUser().getEmail(),"","r","","","READ","");
                    if(JDBCConnector.updateMessage(m).equals("Query Successful!")){
                        dbHandler.updateMessages(m);
                    }
                }
            }
        };
        t.schedule(task, 10, 1000);
    }
}
