package com.chatengine.chatengine;

import java.net.*;
import java.io.*;

public class OfflineClient {

    private Socket socket              = null;
    private ObjectInputStream console = null;
    private ObjectOutputStream streamOut = null;
    private static String msg;

    public OfflineClient(final String serverName, final int serverPort)
    {  Thread t=new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("Establishing connection. Please wait ...");
            try
            {  socket = new Socket(serverName, serverPort);
                System.out.println("Connected: " + socket);
                start();
            }
            catch(UnknownHostException uhe)
            {  System.out.println("Host unknown: " + uhe.getMessage());
            }
            catch(IOException ioe)
            {  System.out.println("Unexpected exception: " + ioe.getMessage());
            }
            String line = null;
            while (true)
            {
                try {
                    line = (String)console.readObject();
                    if(line!=null){
                       msg=line;
                    }
                }
                catch(IOException ioe)
                {  System.out.println("Sending error: " + ioe.getMessage());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    });
        t.start();
    }
    public void start() throws IOException
    {  console   = new ObjectInputStream(socket.getInputStream());
        streamOut = new ObjectOutputStream(socket.getOutputStream());
    }
    public void stop()
    {  try
    {  if (console   != null)  console.close();
        if (streamOut != null)  streamOut.close();
        if (socket    != null)  socket.close();
    }
    catch(IOException ioe)
    {  System.out.println("Error closing ...");
    }
    }

    public static String getMsg(){
        return msg;
    }

    public void writeMsg(String msg){
        try {
            streamOut.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
