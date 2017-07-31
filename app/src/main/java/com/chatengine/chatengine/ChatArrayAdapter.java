package com.chatengine.chatengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChatArrayAdapter extends BaseAdapter{

    public String[] from,msg,datetime;
    Context context;
    private String you;

    public ChatArrayAdapter(Context context,String you,String[] from, String[] msg, String[] datetime) {
        this.from = from;
        this.msg = msg;
        this.datetime = datetime;
        this.context=context;
        this.you=you;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatHolder chatHolder=null;
        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            if(you.equals(from[position])){
                convertView = layoutInflater.inflate(R.layout.offline_chat_layout,null);
            }else{
                convertView = layoutInflater.inflate(R.layout.offline_chat_recieved_layout,null);
            }
            chatHolder = new ChatHolder(convertView);
            convertView.setTag(chatHolder);
        }else{
            chatHolder = (ChatHolder) convertView.getTag();
        }
        chatHolder.from.setText(from[position]);
        chatHolder.msg.setText(msg[position]);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return msg[position];
    }

    @Override
    public int getCount() {
        return msg.length;
    }

    private class ChatHolder{
        TextView msg,from;
        ChatHolder(View v){
            RelativeLayout layout = (RelativeLayout)v.findViewById(R.id.mesageHolder);
            msg = (TextView) layout.findViewById(R.id.messageText);
            from = (TextView) v.findViewById(R.id.msgFrom);
        }
    }
}
