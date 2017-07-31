package com.chatengine.chatengine;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessageArrayAdapter extends BaseAdapter{

    Context context;
    static String user;
    static String[] message,status,msg_type,from,to;

    public MessageArrayAdapter(Context context, String user,String from[], String to[],String[] message, String[] status, String[] msg_type) {
        this.context = context;
        this.message = message;
        this.user = user;
        this.from = from;
        this.to = to;
        this.status = status;
        this.msg_type = msg_type;
    }

    @Override
    public int getCount() {
        return message.length;
    }

    @Override
    public Object getItem(int position) {
        return message[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageHolder messageHolder;
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("MessageArrayAdapter","From: "+from[position]+" To: "+to[position]+" Msg: "+message[position]);
            if(from[position].equals(user)){
                convertView = layoutInflater.inflate(R.layout.message_sent,parent,false);
            }else{
                convertView = layoutInflater.inflate(R.layout.message_array_layout,parent,false);
            }
            messageHolder = new MessageHolder(convertView);
            convertView.setTag(messageHolder);
        }else{
            messageHolder = (MessageHolder) convertView.getTag();
        }
        messageHolder.message.setText(Html.fromHtml(message[position]));
        return convertView;
    }

    private class MessageHolder{
        TextView message,status;
        RelativeLayout messageHolder;
        MessageHolder(View v){
            messageHolder = (RelativeLayout) v.findViewById(R.id.mesageHolder);
            message = (TextView) messageHolder.findViewById(R.id.messageText);
        }
    }
}
