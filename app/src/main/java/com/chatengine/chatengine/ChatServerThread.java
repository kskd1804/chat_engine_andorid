package com.chatengine.chatengine;

import java.net.*;
import java.io.*;

public class ChatServerThread{

    private Socket          socket   = null;
    private OfflineServer      server   = null;
    private int             ID       = -1;
    private ObjectInputStream streamIn =  null;
    private ObjectOutputStream streamout =  null;
    private DBHandler dbHandler;

    public ChatServerThread(OfflineServer _server, Socket _socket,DBHandler dbHandler)
    {  server = _server;  socket = _socket;  ID = socket.getPort();
        this.dbHandler = dbHandler;
    }
    public void run()
    {
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Server Thread " + ID + " running.");
                while (true)
                {
                    String msg = null;
                    try {
                        if((msg=(String)streamIn.readObject())!=null){
                            if(msg.contains("Messages Request!")){
                                writeMessage(dbHandler.getMessagesOffline());
                            }else{
                                if(!msg.equals("")){
                                    long res=0;
                                    while(true){
                                        if(res>0){
                                            break;
                                        }else{
                                            res=dbHandler.addMessageOffline(msg);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch(IOException ioe)
                    {

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void open() throws IOException
    {  streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        streamout = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }
    public void close() throws IOException
    {  if (socket != null)    socket.close();
        if (streamIn != null)  streamIn.close();
    }
    public void writeMessage(String msg){
        try {
            streamout.writeObject(msg);
            streamout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
