package com.chatengine.chatengine;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class JDBCConnector extends AsyncTask<String,Void,String>{

    private static String user_table="user";
    private static String firstname="firstname";
    private static String lastname="lastname";
    private static String email="email";
    private static String password="password";
    private static String mobile="mobile";
    private static String dob="dob";
    private static String gender="gender";
    private static String joined_on="joineddate";
    private static String imgURL="imgURL";
    private static String msgtable="msgtable";
    private static String status="status";
    private static String last_online="last_online";

    private static String message_table="messages";
    private static String from_name="from_name";
    private static String to_name="to_name";
    private static String msg="msg";
    private static String sent_recd="sent_recd";
    private static String datetime ="datetime";
    private static String imgURL2="imgURL";
    private static String status2="status";
    private static String msg_type = "msg_type";

    private static String link = "jdbc:mysql://192.168.1.8:3306/cheatengine?user=kskd&password=kskd1997";
    private static String users_link = "http://kcdastrust.org/project_chatEngine/user.php";
    private static String msg_link = "http://kcdastrust.org/project_chatEngine/message.php";
    private static Connection con;
    private static ResultSet rs=null;
    public static boolean b = false;
    public static int executed=0;
    private static String RESULT="";
    private static int return_update=0;

    public JDBCConnector() {
    }

    public static String addUser(User user){
        executed=0;
        RESULT="";
        return_update=0;
        JDBCConnector add = new JDBCConnector();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        String sql = "INSERT INTO " + user_table + " (" + firstname + "," + lastname + "," + email + "," + mobile + "," + dob + "," + password + "," + gender + "," + joined_on + "," + msgtable + "," + last_online + ") VALUES ('" + user.getFirstname() + "','" + user.getLastname() + "','" + user.getEmail() + "','" + user.getMobile() + "','" + user.getDob() + "','" + user.getPassword() + "','" + user.getGender() + "','" + date + "','" + user.getEmail() + "','" + user.getLast_online() + "');";
        add.execute(sql,"1","1");
        while(true){
            if(!add.RESULT.equals("")){
                return add.RESULT;
            }
        }
    }

    public static User updateUser(User user){
        executed=0;
        RESULT="";
        return_update=0;
        User u = null;
        JDBCConnector update = new JDBCConnector();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "UPDATE " + user_table + " SET " + firstname + "=\"" + user.getFirstname() + "\", " + lastname + "=\"" + user.getLastname() + "\", " + mobile + "=\"" + user.getMobile() + "\", " + status + "=\"" + user.getStatus() + "\", " + last_online + "=\"" + user.getLast_online() + "\" WHERE " + email + "=\"" + user.getEmail() + "\"";
        update.execute(sql, "2", "1");
        while(true){
            if(!update.RESULT.equals("")){
                u = getUser(user);
                return u;
            }
        }
    }

    public static User getUser(User user){
        RESULT="";
        return_update=0;
        User u = new User();
        JDBCConnector get = new JDBCConnector();
        String sql = "SELECT * FROM " + user_table + " WHERE " + email + "='" + user.getEmail() + "' AND " + password + "='" + user.getPassword() + "'";
        get.execute(sql, "3", "1");
        while(true){
            if(!get.RESULT.equals("")){
                Log.i("cE","Entered into if loop");
                if(!RESULT.contains(user.getEmail())){
                    Log.i("cE","u is null");
                    u = null;
                    return u;
                }else{
                    Log.i("cE","u is not null");
                    u = strToUser(RESULT);
                    return  u;
                }
            }
        }
    }

    private static User strToUser(String result){
        Log.i("ChatEngine","Entered JDBC Connector strToUSer");
        User user = new User();
        String arr[] = new String[100];
        int i=0;
        for(String str:result.split(",")){
            arr[i] = str;
            i++;
        }
        user.setFirstname(arr[0]);
        user.setLastname(arr[1]);
        user.setEmail(arr[2]);
        user.setMobile(arr[3]);
        user.setDob(arr[4]);
        user.setPassword(arr[5]);
        user.setGender(arr[6]);
        user.setJoined_date(arr[7]);
        user.setMsg_table(arr[8]);
        user.setStatus(arr[9]);
        user.setImgURL(arr[10]);
        user.setLast_online(arr[11]);
        return user;
    }

    public static User[] getUsers(String name){
        RESULT="";
        return_update=0;
        User[] u;
        JDBCConnector get = new JDBCConnector();
        String sql = "SELECT * FROM " + user_table + " WHERE " + firstname + " LIKE \"%" + name + "%\" OR " + lastname + " LIKE \"%" + name + "%\"";
        get.execute(sql, "5", "1");
        while(true){
            if(!get.RESULT.equals("")){
                u = strToUsers(RESULT);
                return u;
            }
        }
    }

    private static User[] strToUsers(String result){
        if(result.equals("Empty String!")){
            User[] user = null;
            return user;
        }else{
            User[] u;
            int i=0,j=0;
            String arr[][] = new String[12][100];
            for(String str:result.split(",")){
                arr[i%12][j] = str;
                i++;
                if(i%12==0 && i!=0)j++;
            }
            u = new User[j];
            for(i=0;i<j;i++){
                u[i] = new User();
                u[i].setFirstname(arr[0][i]);
                u[i].setLastname(arr[1][i]);
                u[i].setEmail(arr[2][i]);
                u[i].setMobile(arr[3][i]);
                u[i].setDob(arr[4][i]);
                u[i].setPassword(arr[5][i]);
                u[i].setGender(arr[6][i]);
                u[i].setJoined_date(arr[7][i]);
                u[i].setMsg_table(arr[8][i]);
                u[i].setStatus(arr[9][i]);
                u[i].setImgURL(arr[10][i]);
                u[i].setLast_online(arr[11][i]);
            }
            return u;
        }
    }

    public static User getUser(String username){
        RESULT="";
        return_update=0;
        User u = new User();
        JDBCConnector get = new JDBCConnector();
        String sql = "SELECT * FROM " + user_table + " WHERE " + email + "=\"" + username + "\"";
        get.execute(sql,"3","1");
        while(true){
            if(!get.RESULT.equals("")){
                if(!RESULT.contains(username)){
                    u = null;
                    return u;
                }else{
                    u = strToUser(RESULT);
                    return  u;
                }
            }
        }
    }

    public static String checkUser(String username){
        RESULT="";
        return_update=0;
        JDBCConnector get = new JDBCConnector();
        String sql = "SELECT * FROM " + user_table + " WHERE " + email + "=\"" + username + "\"";
        get.execute(sql,"6","1");
        while(true){
            if(!get.RESULT.equals("")){
                Log.i("cE","Entered after executed loop");
                return get.RESULT;
            }
        }
    }

    public static String addMessage(Message message){
        RESULT="";
        return_update=0;
        JDBCConnector add = new JDBCConnector();
        String sql = "INSERT INTO `" + message_table + "` (" + from_name + ", " + to_name + ", " + msg + ", " + sent_recd + ", " + datetime + ", " + imgURL2 + ", " + status2 + ", " + msg_type + ") VALUES ('" + message.getFrom_name() + "', '" + message.getTo_name() + "', '" + message.getMsg() + "', '" + message.getSent_recd() + "', '" + message.getDatetime() + "', '" + message.getImgURL() + "', '" + message.getStatus() + "', '" + message.getMsg_type() + "')";
        add.execute(sql, "1", "2");
        while(true){
            if(!add.RESULT.equals("")){
                if(!add.RESULT.equals("")){
                    return add.RESULT;
                }
            }
        }
    }

    public static String updateMessage(Message message){
        RESULT="";
        return_update=0;
        JDBCConnector update = new JDBCConnector();
        String sql = "UPDATE `" + message_table + "` SET " + status2 + "=\"" + message.getStatus() + "\" WHERE " + from_name + "=\"" + message.getFrom_name() + "\" AND " + to_name + "=\"" + message.getTo_name() + "\"";
        update.execute(sql, "1", "2");
        while(true){
            if(!update.RESULT.equals("")){
                return update.RESULT;
            }
        }
    }

    public static Message[] getMessages(String from,String date){
        RESULT="";
        return_update=0;
        Message[] messages = null;
        JDBCConnector get = new JDBCConnector();
        String sql = "SELECT * FROM " + message_table + " WHERE " + to_name + "=\"" + from + "\" AND " + datetime + ">=\"" + date + "\"";
        get.execute(sql,"5","2");
        while(true){
            if(!get.RESULT.equals("")){
                messages = strToMessages(get.RESULT);
                return messages;
            }
        }
    }

    private static Message[] strToMessages(String result)
    {
        Log.i("JDBCConnector",result);
        if(result.equals("Empty String!")){
            Message[] messages = null;
            return messages;
        }else{
            Message[] messages = null;
            int i=0,j=0;
            String arr[][] = new String[8][100];
            for(String str:result.split(",")){
                arr[i%8][j] = str;
                i++;
                if(i%8==0 && i!=0)j++;
            }
            messages = new Message[j];
            for(i=0;i<j;i++){
                messages[i] = new Message();
                messages[i].setFrom_name(arr[0][i]);
                messages[i].setTo_name(arr[1][i]);
                messages[i].setMsg(arr[2][i]);
                messages[i].setSent_recd(arr[3][i]);
                messages[i].setDatetime(arr[4][i]);
                messages[i].setImgURL(arr[5][i]);
                messages[i].setStatus(arr[6][i]);
                messages[i].setMsg_type(arr[7][i]);
            }
            return messages;
        }
    }

    public static Message getMessage(Message message){
        RESULT="";
        return_update=0;
        Message messages = null;
        JDBCConnector get = new JDBCConnector();
        String sql = "SELECT * FROM " + message_table + " WHERE " + from_name + "=\"" + message.getFrom_name() + "\" AND " + datetime + "=\"" + message.getDatetime() + "\" AND "+ to_name + "=\"" + message.getTo_name() + "\" AND "+ msg + ">=\"" + message.getMsg() + "\"";
        get.execute(sql,"3","2");
        while(true){
            if(!get.RESULT.equals("")){
                messages = strToMessage(RESULT);
                return messages;
            }
        }
    }

    private static Message strToMessage(String result)
    {
        Message message = new Message();
        String arr[] = new String[100];
        int i=0;
        for(String str:result.split(",")){
            arr[i] = str;
            i++;
        }
        message = new Message();
        message.setFrom_name(arr[0]);
        message.setTo_name(arr[1]);
        message.setMsg(arr[2]);
        message.setSent_recd(arr[3]);
        message.setDatetime(arr[4]);
        message.setImgURL(arr[5]);
        message.setStatus(arr[6]);
        message.setMsg_type(arr[7]);
        return message;
    }

    private void execQuery(final String sql){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("cE","Entered Thread connecting database");
                try {
                    Log.i("cE","Entered try");
                    Class.forName("com.mysql.jdbc.Driver");
                    Log.i("cE", "Starting connection to database");
                    con = DriverManager.getConnection(link);
                    Log.i("cE","Connection to database successful!");
                    Statement stmt=con.createStatement();
                    rs = stmt.executeQuery(sql);
                    Log.i("cE","Query Executed!");
                }
                catch(ClassNotFoundException ex) {
                    ex.printStackTrace();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                executed=1;
            }
        });
        thread.start();
    }

    public static void exec(final String sql){
        b=false;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("cE","Entered Thread connecting database");
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    con = DriverManager.getConnection(link);
                    Log.i("cE","Connection to database successful!");
                    Statement stmt=con.createStatement();
                    b = stmt.execute(sql);
                    Log.i("cE","Query Executed");
                }
                catch(ClassNotFoundException ex) {
                    System.out.println("Error: unable to load driver class!");
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                executed=1;
            }
        });
        thread.start();
    }

    @Override
    protected String doInBackground(String... params) {
        Log.i("TEST","Entered JDBC Connector doInBack");
        executed=0;
        return_update=0;
        String sql = params[0];
        Log.i("TEST",sql);
        String sql_code = params[1];
        String seq_code = params[2];
        if(seq_code.equals("1"))
        {
            try {
                URL url = new URL(users_link);
                String data = URLEncoder.encode("sql","UTF-8")+"="+URLEncoder.encode(sql,"UTF-8");
                data += "&"+URLEncoder.encode("sql_code","UTF-8")+"="+URLEncoder.encode(sql_code,"UTF-8");
                URLConnection connection = url.openConnection();
                Log.i("JDBCConnector","Connection Opened JDBCConnector");
                connection.setDoOutput(true);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWriter.write(data);
                outputStreamWriter.flush();
                Log.i("JDBCConnector", "Data written JDBCConnector");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                    break;
                }
                Log.i("JDBCConnector","Data Read JDBC Connector "+stringBuilder.toString());
                RESULT = stringBuilder.toString();
                Log.i("JDBCConnector","Data Read JDBC Connector is "+RESULT);
                return RESULT;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            try {
                URL url = new URL(msg_link);
                String data = URLEncoder.encode("sql","UTF-8")+"="+URLEncoder.encode(sql,"UTF-8");
                data += "&"+URLEncoder.encode("sql_code","UTF-8")+"="+URLEncoder.encode(sql_code,"UTF-8");
                URLConnection connection = url.openConnection();
                Log.i("JDBCConnector","Connection Opened JDBC Connector");
                connection.setDoOutput(true);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWriter.write(data);
                outputStreamWriter.flush();
                Log.i("JDBCConnector","Data written JDBC Connector");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                    break;
                }
                Log.i("JDBCConnector","Data Read JDBC Connector "+stringBuilder.toString());
                RESULT = stringBuilder.toString();
                Log.i("JDBCConnector","Data Read JDBC Connector is "+RESULT);
                return RESULT;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        RESULT = "Unsuccessful Query";
        return RESULT;
    }
}
