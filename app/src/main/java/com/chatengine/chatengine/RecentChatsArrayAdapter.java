package com.chatengine.chatengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecentChatsArrayAdapter extends BaseAdapter{

    private String[] name,msg,time,status,imgURL,sent_recd,email;
    private Bitmap[] bitmap;
    private Context context;

    private static Bitmap img;
    private static int update=0;
    private static String user;

    public RecentChatsArrayAdapter(Context context, String user,String[] name, String[] email, String[] msg, String[] time, String[] status, String[] imgURL, Bitmap[] bitmap,String[] sent_recd) {
        this.name = name;
        this.email = email;
        this.msg = msg;
        this.time = time;
        this.status = status;
        this.user = user;
        this.imgURL = imgURL;
        this.bitmap = bitmap;
        this.context = context;
        this.sent_recd = sent_recd;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecentChatHolder recentChatHolder;
        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.recent_chat_layout,null);
            recentChatHolder = new RecentChatHolder(convertView);
            convertView.setTag(recentChatHolder);
        }
        else{
            recentChatHolder = (RecentChatHolder) convertView.getTag();
        }
        img = null;
        update=0;
        if(name[position].length()>16) name[position] = name[position].substring(0,13)+"...";
        recentChatHolder.name.setText(name[position]);
        if(msg[position].length()>26) msg[position] = msg[position].substring(0,23)+"...";
        recentChatHolder.msg.setText(msg[position]);
        recentChatHolder.time.setText(time[position].substring(11,16)+" "+time[position].substring(20));
        if(!email[position].equals(user) && status[position].equals("SENT")){
            recentChatHolder.status.setText(Html.fromHtml("&#9733;"));
            recentChatHolder.status.setTextColor(Color.WHITE);
            recentChatHolder.status.setBackgroundResource(R.drawable.circle);
        }
        else if(!email[position].equals(user) && status[position].equals("READ")){
            recentChatHolder.status.setText("");
            recentChatHolder.status.setTextColor(Color.BLACK);
        }
        else if(email[position].equals(user) && status[position].equals("SENT")){
            recentChatHolder.status.setText("S");
            recentChatHolder.status.setTextColor(Color.parseColor("#551A8B"));
        }
        else if(email[position].equals(user) && status[position].equals("READ")){
            recentChatHolder.status.setText("R");
            recentChatHolder.status.setTextColor(Color.parseColor("#551A8B"));
        }
        recentChatHolder.dp.setImageBitmap(bitmap[position]);
        return convertView;
    }

    private class RecentChatHolder{
        TextView name,msg,time,status;
        ImageView dp;
        RecentChatHolder(View v){
            name = (TextView) v.findViewById(R.id.recent_chat_name);
            msg = (TextView) v.findViewById(R.id.recent_chat_msg);
            time = (TextView) v.findViewById(R.id.recent_chat_msg_time);
            status = (TextView) v.findViewById(R.id.recent_chat_msg_status);
            dp = (ImageView) v.findViewById(R.id.recent_chat_img);
        }
    }
}
