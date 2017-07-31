package com.chatengine.chatengine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SyncService extends Service {
    static DBHandler dbHandler;
    static Message last;
    static User user;
    public SyncService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SyncService","Service Started!");
        dbHandler = new DBHandler(this,null,null,1);
        user = dbHandler.getUser();
        last = dbHandler.getLastMessage(user.getEmail());
        if(last==null){
            last = new Message();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat dd = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
            try {
                if(user==null){
                    last.setDatetime("01-01-2016 12:00:00 AM");
                }else{
                    Date date = df.parse(user.getLast_online());
                    String lastDate = dd.format(date);
                    last.setDatetime(lastDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public synchronized void run() {
                Message[] messages = SyncDBConnector.getMessages(user.getEmail(), last.getDatetime());
                if(messages!=null){
                    int len = messages.length;
                    int i=0;
                    Log.w("SyncService", "New messages length: " + len);
                    while(i<len){
                        Message m = dbHandler.getMessage(messages[i]);
                        if(m.getFrom_name().equals("chatEngine")){
                            dbHandler.addMessage(messages[i]);
                            last = messages[i];
                            Log.w("SyncService", "New message added: " + messages[i].getMsg());
                        }else{
                            last = m;
                        }
                        i++;
                    }
                }
                Message[] messages1 = dbHandler.getMessages(user.getEmail());
                if(messages1!=null){
                    int len = messages1.length;
                    int i=0;
                    while(i<len){
                        Message m = SyncDBConnector.getMessage(messages1[i]);
                        if(m!=null) {
                            Log.i("SyncService","Message status updating...");
                            if (!messages1[i].getStatus().equals(m.getStatus())) {
                                if(dbHandler.updateMessage(m)>0) {
                                    Log.i("SyncService", "Message Status Updated: " + m.getMsg() + " Sent to: " + m.getTo_name() + " Status: " + m.getStatus());
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        };
        t.schedule(task,10,1000);
        return Service.START_STICKY;
    }
}
