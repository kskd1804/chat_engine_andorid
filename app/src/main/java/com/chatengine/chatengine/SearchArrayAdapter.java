package com.chatengine.chatengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchArrayAdapter extends BaseAdapter{

    Context context;
    String[] name,status,last_online;
    Bitmap[] img;

    public SearchArrayAdapter(Context context, String[] name, String[] status, String[] last_online, Bitmap[] img) {
        this.context = context;
        this.name = name;
        this.status = status;
        this.last_online = last_online;
        this.img = img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchHolder searchHolder;
        if(convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.search_array_layout,null);
            searchHolder = new SearchHolder(convertView);
            convertView.setTag(searchHolder);
        }else{
            searchHolder = (SearchHolder) convertView.getTag();
        }
        searchHolder.image.setImageBitmap(img[position]);
        searchHolder.name.setText(name[position]);
        if(status[position].equals("online")){
            searchHolder.status.setText("online");
        }else{
            searchHolder.status.setText("last seen at "+last_online[position]);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return name[position];
    }

    @Override
    public int getCount() {
        return name.length;
    }

    private class SearchHolder{
        TextView name,status;
        ImageView image;

        SearchHolder(View v){
            name = (TextView) v.findViewById(R.id.search_name);
            status = (TextView) v.findViewById(R.id.search_status);
            image = (ImageView) v.findViewById(R.id.search_img);
        }
    }
}
