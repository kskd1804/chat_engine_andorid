package com.chatengine.chatengine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ChatFragment extends Fragment {

    private ListView offlineChats;
    private EditText chatText;
    private ImageButton send;
    private DBHandler dbHandler;
    private static User you;
    private String host;

    private ChatArrayAdapter chatArrayAdapter;
    private static String[] from,datetime,msg;

    public static OfflineClient client=null;
    public static OfflineServer server=null;

    private int port;
    private String ipaddress;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        dbHandler = new DBHandler(getActivity().getApplicationContext(),null,null,1);
        if(dbHandler.getConnectionStatus().equals("Running")){
            you = dbHandler.getUser();
            host = dbHandler.getHost();
            ipaddress = dbHandler.getIPAddress();
            String portno_s=dbHandler.getPort();
            if(!portno_s.equals("") && dbHandler.getConnectionStatus().equals("Running")){
                port = Integer.parseInt(portno_s);
            }else{

            }
            if(host.equals("Server")){
                server = new OfflineServer(port,dbHandler);
            }else if(host.equals("Client")){
                client = new OfflineClient(ipaddress,port);
            }
            offlineChats = (ListView) v.findViewById(R.id.chaList);
            RelativeLayout rl = (RelativeLayout)v.findViewById(R.id.rl_input);
            chatText = (EditText) rl.findViewById(R.id.message);
            send = (ImageButton) rl.findViewById(R.id.send);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date d = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                    if(host.equals("Server")){
                        long res=0;
                        while(true){
                            if(res>0){
                                break;
                            }else{
                                res=dbHandler.addMessageOffline(you.getFirstname()+" "+you.getLastname()+","+chatText.getText().toString()+","+df.format(d));
                            }
                        }
                    }else if(host.equals("Client")){
                        client.writeMsg(you.getFirstname()+" "+you.getLastname()+","+chatText.getText().toString()+","+df.format(d));
                    }
                }
            });
            updateList();
        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Please create/join a room!",Toast.LENGTH_LONG).show();
        }
        return v;
    }

    private void updateList(){
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String msgs="";
                if(host.equals("Server")){
                    msgs = dbHandler.getMessagesOffline();
                }else if(host.equals("Client")){
                    client.writeMsg("Messages Request!");
                    while(msgs.equals("")){
                        msgs = OfflineClient.getMsg();
                    }
                }
                if(!msgs.equals("") && msgs!=null){
                    int i=0,j=0;
                    String arr[][] = new String[3][100];
                    for(String str:msgs.split(",")){
                        arr[i%3][j] = str;
                        i++;
                        if(i%3==0 && i!=0)j++;
                    }
                    from = new String[j];
                    datetime = new String[j];
                    msg = new String[j];
                    for(i=0;i<j;i++){
                        from[i]=arr[0][i];
                        msg[i]=arr[1][i];
                        datetime[i]=arr[2][i];
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatArrayAdapter = new ChatArrayAdapter(getContext(),you.getFirstname()+" "+you.getLastname(),from,msg,datetime);
                            offlineChats.setAdapter(chatArrayAdapter);
                            chatArrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        };
        t.schedule(task,10,2000);
    }


}
