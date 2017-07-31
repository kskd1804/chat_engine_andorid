package com.chatengine.chatengine;

import java.net.*;
import java.io.*;

public class OfflineServer implements Runnable{

    private ServerSocket     server = null;
    private Thread           thread = null;
    private ChatServerThread client = null;
    private DBHandler dbHandler;

    public OfflineServer(int port,DBHandler dbHandler) {
        try
        {  System.out.println("Binding to port " + port + ", please wait  ...");
            server = new ServerSocket(port);
            start();
        }
        catch(IOException ioe)
        {  System.out.println(ioe); }
        this.dbHandler =dbHandler;
    }

    public void run()
    {  while (thread != null)
    {  try
    {
        System.out.println("Waiting for a client ...");
        addThread(server.accept());
    }
    catch(IOException ie)
    {
        System.out.println("Acceptance Error: " + ie); }
    }
    }

    public void addThread(Socket socket)
    {  System.out.println("Client accepted: " + socket);
        client = new ChatServerThread(this, socket,dbHandler);
        try
        {  client.open();
            client.run();
        }
        catch(IOException ioe)
        {  System.out.println("Error opening thread: " + ioe); }
    }

    public void start()
    {  if (thread == null)
    {  thread = new Thread(this);
        thread.start();
    }
    }
    public void stop()
    {  if (thread != null)
    {  thread.interrupt();
        thread = null;
    }
    }

}
