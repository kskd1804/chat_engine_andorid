package com.chatengine.chatengine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateFragment extends Fragment {

    private Button start,stop;
    private TextView chatTitle;
    private EditText ipaddress,port;
    private RadioGroup onlineRadio;
    private DBHandler dbHandler;

    public CreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create, container, false);
        dbHandler = new DBHandler(getActivity().getApplicationContext(),null,null,1);
        start = (Button) v.findViewById(R.id.start);
        chatTitle = (TextView) v.findViewById(R.id.chatTitle);
        stop = (Button) v.findViewById(R.id.stop);
        String status = dbHandler.getConnectionStatus();
        if(status.equals("Running")){
            start.setClickable(false);
            start.setBackgroundColor(Color.GRAY);
            chatTitle.setText(dbHandler.getIPAddress() + ":" + dbHandler.getPort());
            chatTitle.setVisibility(View.VISIBLE);
        }else{
            stop.setClickable(false);
            stop.setBackgroundColor(Color.GRAY);
            chatTitle.setVisibility(View.GONE);
        }
        RelativeLayout rl = (RelativeLayout)v.findViewById(R.id.rl_input);
        ipaddress = (EditText) rl.findViewById(R.id.ipaddress);
        port = (EditText) rl.findViewById(R.id.port);
        chatTitle = (TextView) v.findViewById(R.id.chatTitle);
        onlineRadio = (RadioGroup) v.findViewById(R.id.radioOffline);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IPAddress=ipaddress.getText().toString();
                String portno=port.getText().toString();
                int id = onlineRadio.getCheckedRadioButtonId();
                String type="";
                switch (id){
                    case R.id.create_radio:
                        type="Server";
                        break;
                    case R.id.join_radio:
                        type="Client";
                        break;
                }
                if(!IPAddress.equals("") && !portno.equals("")){
                    long res=-1;
                    while(true){
                        if(res>0){
                            break;
                        }else{
                            res = dbHandler.addHost(type,Integer.parseInt(portno),IPAddress,"Running");
                        }
                    }
                    start.setClickable(false);
                    start.setBackgroundColor(Color.GRAY);
                    stop.setBackgroundColor(Color.RED);
                    stop.setClickable(true);
                    chatTitle.setText(dbHandler.getIPAddress() + ":" + dbHandler.getPort());
                    chatTitle.setVisibility(View.VISIBLE);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new ChatFragment())
                            .commit();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Please enter ip address and port no",Toast.LENGTH_LONG).show();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while(true){
                    String status=dbHandler.getConnectionStatus();
                    if(status.equals("Completed")){
                        break;
                    }else{
                        Log.i("CreateFragment","Connection status is: "+status);
                        dbHandler.setConnectionStatus("Completed");
                    }
                }
                if(dbHandler.getHost().equals("Server")){
                    if(ChatFragment.server!=null){
                        ChatFragment.server.stop();
                    }
                }else{
                    if(ChatFragment.client!=null){
                        ChatFragment.client.stop();
                    }
                }
                if(dbHandler.getHost().equals("Server")){
                    dbHandler.truncateTable();
                    Log.i("CreateFragment","Offline table truncated!");
                }
                start.setBackgroundColor(Color.parseColor("#551A8B"));
                start.setClickable(true);
                stop.setBackgroundColor(Color.GRAY);
                stop.setClickable(false);
                chatTitle.setVisibility(View.GONE);
            }
        });
        return v;
    }


}
