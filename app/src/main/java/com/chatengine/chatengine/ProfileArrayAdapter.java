package com.chatengine.chatengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProfileArrayAdapter extends BaseAdapter{

    String key[],value[];
    Context context;

    public ProfileArrayAdapter(Context context, String[] key, String[] value) {
        this.key = key;
        this.value = value;
        this.context = context;
    }

    @Override
    public int getCount() {
        return key.length;
    }

    @Override
    public Object getItem(int position) {
        return value[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProfileHolder profileHolder;
        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.profile_array_layout,null);
            profileHolder = new ProfileHolder(convertView);
            convertView.setTag(profileHolder);
        }else{
            profileHolder = (ProfileHolder) convertView.getTag();
        }
        profileHolder.key.setText(key[position]);
        profileHolder.value.setText(value[position]);
        return convertView;
    }

    private class ProfileHolder{
        TextView key,value;
        ProfileHolder(View view){
            key = (TextView) view.findViewById(R.id.key);
            value = (TextView) view.findViewById(R.id.value);
        }
    }
}
