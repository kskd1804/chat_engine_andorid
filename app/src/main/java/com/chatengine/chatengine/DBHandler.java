package com.chatengine.chatengine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "chatEngine.db";
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "id_user";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MOBILE = "mobile";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_JOINED_DATE = "joineddate";
    public static final String COLUMN_MSG_TABLE = "msgtable";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_IMG_URL = "imgURL";
    public static final String COLUMN_LAST_ONLINE = "last_online";

    public static final String TABLE_MESSAGE = "message";
    public static final String COLUMN_ID_MESSAGE = "id";
    public static final String COLUMN_FROM_NAME = "from_name";
    public static final String COLUMN_TO_NAME = "to_name";
    public static final String COLUMN_MSG = "msg";
    public static final String COLUMN_SENT_RECD = "sent_recd";
    public static final String COLUMN_DATE_TIME = "datetime";
    public static final String COLUMN_IMG_URL_MESSAGE = "imgURL";
    public static final String COLUMN_STATUS_MESSAGE = "status";
    public static final String COLUMN_MSG_TYPE = "msg_type";

    public static final String TABLE_OFFLINE = "offline";
    public static final String COLUMN_ID_OFFLINE = "id";
    public static final String COLUMN_FROM_NAME_OFFLINE = "from_name";
    public static final String COLUMN_MSG_OFFLINE = "msg";
    public static final String COLUMN_DATE_TIME_OFFLINE = "datetime";

    public static final String TABLE_HOST = "host";
    public static final String COLUMN_ID_HOST = "id";
    public static final String COLUMN_HOST_TYPE = "host_type";
    public static final String COLUMN_PORT = "port";
    public static final String COLUMN_IPADDRESS = "ipaddress";
    public static final String COLUMN_STATUS_HOST = "status";

    public static final String TABLE_ATTENDANCE_CREATE = "CREATE TABLE " + TABLE_USER + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRSTNAME + " TEXT, " +
            COLUMN_LASTNAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_MOBILE + " TEXT, " +
            COLUMN_DOB + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_GENDER + " TEXT, " +
            COLUMN_JOINED_DATE + " INTEGER, " +
            COLUMN_MSG_TABLE + " INTEGER, " +
            COLUMN_STATUS + " TEXT, " +
            COLUMN_IMG_URL + " TEXT, " +
            COLUMN_LAST_ONLINE + " TEXT" + ");";

    public static final String TABLE_SESSION_CREATE = "CREATE TABLE " + TABLE_MESSAGE + "(" +
            COLUMN_ID_MESSAGE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_FROM_NAME + " TEXT NOT NULL, " +
            COLUMN_TO_NAME + " TEXT NOT NULL, " +
            COLUMN_MSG + " TEXT NOT NULL, " +
            COLUMN_SENT_RECD + " TEXT NOT NULL," +
            COLUMN_DATE_TIME + " TEXT NOT NULL, " +
            COLUMN_IMG_URL_MESSAGE + " TEXT NOT NULL, " +
            COLUMN_STATUS_MESSAGE + " TEXT NOT NULL, " +
            COLUMN_MSG_TYPE + " TEXT NOT NULL" + ");";

    public static final String TABLE_OFFLINE_CREATE = "CREATE TABLE " + TABLE_OFFLINE + "(" +
            COLUMN_ID_OFFLINE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_FROM_NAME_OFFLINE + " TEXT NOT NULL, " +
            COLUMN_MSG_OFFLINE + " TEXT NOT NULL, " +
            COLUMN_DATE_TIME_OFFLINE + " TEXT NOT NULL" + ");";

    public static final String TABLE_HOST_CREATE = "CREATE TABLE " + TABLE_HOST + "(" +
            COLUMN_ID_HOST + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_HOST_TYPE + " TEXT NOT NULL, " +
            COLUMN_PORT + " TEXT NOT NULL, " +
            COLUMN_IPADDRESS + " TEXT NOT NULL, " +
            COLUMN_STATUS_HOST + " TEXT NOT NULL" + ");";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.i("TEST", "Entered DBHandler Constructor");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOST);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ATTENDANCE_CREATE);
        db.execSQL(TABLE_SESSION_CREATE);
        db.execSQL(TABLE_OFFLINE_CREATE);
        db.execSQL(TABLE_HOST_CREATE);
    }

    public long addUser(User user) {
        Log.i("TEST", "Entered DBHandler addUser");
        SQLiteDatabase db;
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, user.getFirstname());
        values.put(COLUMN_LASTNAME, user.getLastname());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_MOBILE, user.getMobile());
        values.put(COLUMN_DOB, user.getDob());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_JOINED_DATE, user.getJoined_date());
        values.put(COLUMN_MSG_TABLE, user.getMsg_table());
        values.put(COLUMN_STATUS, user.getStatus());
        values.put(COLUMN_IMG_URL, user.getImgURL());
        values.put(COLUMN_LAST_ONLINE, user.getLast_online());
        Log.i("TEST", "ContentValues Put");
        db = getWritableDatabase();
        Log.i("TEST", "Got readable database DBHandler addUser");
        long reg_status = db.insert(TABLE_USER, null, values);
        Log.i("TEST", "Inserted into DB DBHandler addUser");
        return reg_status;
    }

    public long updateUser(User user) {
        SQLiteDatabase db;
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, user.getFirstname());
        values.put(COLUMN_LASTNAME, user.getLastname());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_MOBILE, user.getMobile());
        values.put(COLUMN_DOB, user.getDob());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_JOINED_DATE, user.getJoined_date());
        values.put(COLUMN_MSG_TABLE, user.getMsg_table());
        values.put(COLUMN_STATUS, user.getStatus());
        values.put(COLUMN_IMG_URL, user.getImgURL());
        values.put(COLUMN_LAST_ONLINE, user.getLast_online());
        db = getWritableDatabase();
        long result = db.update(TABLE_USER, values, COLUMN_EMAIL + "=\"" + user.getEmail() + "\"", null);
        return result;
    }

    public long removeUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=\"" + user.getEmail() + "\";");
        return 0;
    }

    public User getUser() {
        User u = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        c.move(1);
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_EMAIL)) != null) {
                Log.i("TEST", "Entered while and if block");
                u = new User();
                u.setFirstname(c.getString(1));
                u.setLastname(c.getString(2));
                u.setEmail(c.getString(3));
                u.setMobile(c.getString(4));
                u.setDob(c.getString(5));
                u.setPassword(c.getString(6));
                u.setGender(c.getString(7));
                u.setJoined_date(c.getString(8));
                u.setMsg_table(c.getString(9));
                u.setStatus(c.getString(10));
                u.setImgURL(c.getString(11));
                u.setLast_online(c.getString(12));
                c.moveToNext();
            }
        }
        c.close();
        return u;
    }

    public long addMessage(Message message) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FROM_NAME, message.getFrom_name());
        values.put(COLUMN_TO_NAME, message.getTo_name());
        values.put(COLUMN_MSG, message.getMsg());
        values.put(COLUMN_SENT_RECD, message.getSent_recd());
        values.put(COLUMN_DATE_TIME, message.getDatetime());
        values.put(COLUMN_IMG_URL_MESSAGE, message.getImgURL());
        values.put(COLUMN_STATUS_MESSAGE, message.getStatus());
        values.put(COLUMN_MSG_TYPE, message.getMsg_type());
        long session_status = db.insert(TABLE_MESSAGE, null, values);
        Log.w("DBHandler", "New Message added: " + message.getMsg());
        return session_status;
    }

    public long updateMessage(Message message) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FROM_NAME, message.getFrom_name());
        values.put(COLUMN_TO_NAME, message.getTo_name());
        values.put(COLUMN_MSG, message.getMsg());
        values.put(COLUMN_SENT_RECD, message.getSent_recd());
        values.put(COLUMN_DATE_TIME, message.getDatetime());
        values.put(COLUMN_IMG_URL_MESSAGE, message.getImgURL());
        values.put(COLUMN_STATUS_MESSAGE, message.getStatus());
        values.put(COLUMN_MSG_TYPE, message.getMsg_type());
        long session_status = db.update(TABLE_MESSAGE, values, COLUMN_DATE_TIME + "=\"" + message.getDatetime() + "\" AND " + COLUMN_FROM_NAME + "=\"" + message.getFrom_name() + "\" AND " + COLUMN_TO_NAME + "=\"" + message.getTo_name() + "\"", null);
        return session_status;
    }

    public void updateMessages(Message message) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_MESSAGE + " SET " + COLUMN_STATUS_MESSAGE + "=\"" + message.getStatus() + "\" WHERE " + COLUMN_FROM_NAME + "=\"" + message.getFrom_name() + "\" AND " + COLUMN_TO_NAME + "=\"" + message.getTo_name() + "\"");
    }

    public long removeMessage(Message message) {
        SQLiteDatabase db = getWritableDatabase();
        long session_status = db.delete(TABLE_MESSAGE, COLUMN_DATE_TIME + "=\"" + message.getDatetime() + "\" AND " + COLUMN_FROM_NAME + "=\"" + message.getFrom_name() + "\" AND " + COLUMN_TO_NAME + "=\"" + message.getTo_name() + "\" AND " + COLUMN_MSG + "=\"" + message.getMsg() + "\"", null);
        return session_status;
    }

    public long removeChat(String email) {
        SQLiteDatabase db = getWritableDatabase();
        long session_status = db.delete(TABLE_MESSAGE, COLUMN_FROM_NAME + "=\"" + email + "\" OR " + COLUMN_TO_NAME + "=\"" + email + "\"", null);
        return session_status;
    }

    public synchronized Message[] getRecents() {
        Message[] messages = null;
        int x = 0;
        int y = 0;
        int flag = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT DISTINCT from_name FROM " + TABLE_MESSAGE + ";";
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        c.move(1);
        String[] recents1 = null;
        if (len > 0) {
            if (c.getString(0) != null) {
                recents1 = new String[c.getCount()];
                while (!c.isAfterLast()) {
                    if (!getUser().getEmail().equals(c.getString(0))) {
                        recents1[y] = new String(c.getString(0));
                        Log.i("DBHandler", c.getString(0));
                        y++;
                    }
                    c.moveToNext();
                }
            }
        }
        Log.i("DBHandler", "recents1 length :" + y);
        query = "SELECT DISTINCT to_name FROM " + TABLE_MESSAGE;
        c = db.rawQuery(query, null);
        len = c.getCount();
        c.move(1);
        String[] recents2 = null;
        if (len > 0) {
            if (c.getString(0) != null) {
                recents2 = new String[c.getCount()];
                x = 0;
                while (!c.isAfterLast()) {
                    flag = 0;
                    for (int i = 0; i < x; i++) {
                        if (recents1[i].equals(c.getString(0))) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        if (!getUser().getEmail().equals(c.getString(0))) {
                            recents2[x] = new String(c.getString(0));
                            Log.i("DBHandler", c.getString(0));
                            x++;
                        }
                    }
                    c.moveToNext();
                }
            }
        }
        Log.i("DBHandler", "recents2 length :" + x);
        String[] recents;
        if (x != 0 && y == 0) {
            Log.i("DBHandler", "recents1==null && recents2!=null");
            recents = recents2;
        } else if (y != 0 && x == 0) {
            Log.i("DBHandler", "recents1!=null && recents2==null");
            recents = recents1;
        } else if (y == 0 && x == 0) {
            Log.i("DBHandler", "recents1==null && recents2==null");
            recents = null;
        } else {
            Log.i("DBHandler", "recents1!=null && recents2!=null");
            recents = joinArrayGeneric(recents1, recents2);
        }
        if (recents != null) {
            for (int f = 0; f < recents.length; f++)
                Log.i("DBHandler", "From main recents: " + recents[f]);
            if (recents.length > 0) {
                Log.i("DBHandler", "Length of recents: " + recents.length);
                messages = new Message[recents.length];
                int i = 0;
                for (i = 0; i < recents.length && recents[i] != null; i++) {
                    query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE ( " + COLUMN_FROM_NAME + "=\"" + recents[i] + "\" AND " + COLUMN_TO_NAME + "=\"" + getUser().getEmail() + "\") OR ( " + COLUMN_FROM_NAME + "=\"" + getUser().getEmail() + "\" AND " + COLUMN_TO_NAME + "=\"" + recents[i] + "\" ) ORDER BY " + COLUMN_DATE_TIME + " DESC LIMIT 1";
                    Log.i("DBHandler", query);
                    c = db.rawQuery(query, null);
                    c.move(1);
                    Log.i("DBHandler", "Entering while loop...creating message array");
                    while (!c.isAfterLast()) {
                        Log.i("DBHandler", "condition satisfied...loop entered");
                        messages[i] = new Message();
                        if (c.getString(1).equals(recents[i])) {
                            messages[i].setMsg(c.getString(3));
                        } else {
                            messages[i].setMsg("You: " + c.getString(3));
                        }
                        messages[i].setFrom_name(recents[i]);
                        messages[i].setTo_name(recents[i]);
                        messages[i].setSent_recd(c.getString(4));
                        messages[i].setDatetime(c.getString(5));
                        messages[i].setImgURL(c.getString(6));
                        messages[i].setStatus(c.getString(7));
                        Log.i("DBHandler", messages[i].getFrom_name() + " " + messages[i].getMsg() + " " + messages[i].getDatetime());
                        i++;
                        c.moveToNext();
                    }
                }
            }
        }
        c.close();
        Log.i("DBHandler", "Returned message array");
        return messages;
    }

    public Message[] getMessage(String email, int count) {
        Message[] message = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_FROM_NAME + "=\"" + email + "\" OR " + COLUMN_TO_NAME + "=\"" + email + "\" ORDER BY " + COLUMN_DATE_TIME + " ASC LIMIT " + count;
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        c.move(1);
        int i = 0;
        if (len > 0) {
            message = new Message[len];
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_FROM_NAME)) != null) {
                    message[i] = new Message();
                    message[i].setFrom_name(c.getString(1));
                    message[i].setTo_name(c.getString(2));
                    message[i].setMsg(c.getString(3));
                    message[i].setSent_recd(c.getString(4));
                    message[i].setDatetime(c.getString(5));
                    message[i].setImgURL(c.getString(6));
                    message[i].setStatus(c.getString(7));
                    message[i].setMsg_type(c.getString(8));
                    c.moveToNext();
                    i++;
                }
            }
        }
        c.close();
        return message;
    }

    public Message getMessage(Message m) {
        Message message = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_FROM_NAME + "=\"" + m.getFrom_name() + "\" AND " + COLUMN_TO_NAME + "=\"" + m.getTo_name() + "\" AND " + COLUMN_MSG + "=\"" + m.getMsg() + "\" AND " + COLUMN_DATE_TIME + "=\"" + m.getDatetime() + "\"";
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        Log.w("DBHandler", "len of query: " + len);
        c.move(1);
        int i = 0;
        message = new Message();
        message.setFrom_name("chatEngine");
        if (len > 0) {
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_FROM_NAME)) != null) {
                    message = new Message();
                    message.setFrom_name(c.getString(1));
                    message.setTo_name(c.getString(2));
                    message.setMsg(c.getString(3));
                    message.setSent_recd(c.getString(4));
                    message.setDatetime(c.getString(5));
                    message.setImgURL(c.getString(6));
                    message.setStatus(c.getString(7));
                    message.setMsg_type(c.getString(8));
                    c.moveToNext();
                    i++;
                }
            }
        }
        c.close();
        return message;
    }

    public Message[] getMessages(String email) {
        Message[] message = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_FROM_NAME + "=\"" + email + "\" AND " + COLUMN_STATUS_MESSAGE + "=\"SENT" + "\" ORDER BY " + COLUMN_DATE_TIME + " ASC;";
        Cursor c = db.rawQuery(query, null);
        c.move(1);
        int i = c.getCount();
        if (i > 0) {
            message = new Message[i];
            i = 0;
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_FROM_NAME)) != null) {
                    message[i] = new Message();
                    message[i].setFrom_name(c.getString(1));
                    message[i].setTo_name(c.getString(2));
                    message[i].setMsg(c.getString(3));
                    message[i].setSent_recd(c.getString(4));
                    message[i].setDatetime(c.getString(5));
                    message[i].setImgURL(c.getString(6));
                    message[i].setStatus(c.getString(7));
                    message[i].setMsg_type(c.getString(8));
                    c.moveToNext();
                    i++;
                }
            }
        } else {
            message = null;
        }
        c.close();
        return message;
    }

    public Message[] getMessages() {
        Message[] message = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE;
        Cursor c = db.rawQuery(query, null);
        c.move(1);
        int i = c.getCount();
        if (i > 0) {
            message = new Message[i];
            i = 0;
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_FROM_NAME)) != null) {
                    message[i] = new Message();
                    message[i].setFrom_name(c.getString(1));
                    message[i].setTo_name(c.getString(2));
                    message[i].setMsg(c.getString(3));
                    message[i].setSent_recd(c.getString(4));
                    message[i].setDatetime(c.getString(5));
                    message[i].setImgURL(c.getString(6));
                    message[i].setStatus(c.getString(7));
                    message[i].setMsg_type(c.getString(8));
                    c.moveToNext();
                    i++;
                }
            }
        } else {
            message = null;
        }
        c.close();
        return message;
    }

    public User[] getUsers() {
        User[] u = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        c.move(1);
        int i = c.getCount();
        if (i > 0) {
            u = new User[i];
            i = 0;
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_FIRSTNAME)) != null) {
                    u[i] = new User();
                    u[i].setFirstname(c.getString(1));
                    u[i].setLastname(c.getString(2));
                    u[i].setEmail(c.getString(3));
                    u[i].setMobile(c.getString(4));
                    u[i].setDob(c.getString(5));
                    u[i].setPassword(c.getString(6));
                    u[i].setGender(c.getString(7));
                    u[i].setJoined_date(c.getString(8));
                    u[i].setMsg_table(c.getString(9));
                    u[i].setStatus(c.getString(10));
                    u[i].setImgURL(c.getString(11));
                    u[i].setLast_online(c.getString(12));
                    c.moveToNext();
                    i++;
                }
            }
        } else {
            u = null;
        }
        c.close();
        return u;
    }

    public Message getLastMessage() {
        Message message = new Message();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE + " ORDER BY id DESC LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        c.move(1);
        int i = 0;
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_FROM_NAME)) != null) {
                Log.i("TEST", "Entered while and if block");
                message = new Message();
                message.setFrom_name(c.getString(1));
                message.setTo_name(c.getString(2));
                message.setMsg(c.getString(3));
                message.setSent_recd(c.getString(4));
                message.setDatetime(c.getString(5));
                message.setImgURL(c.getString(6));
                message.setStatus(c.getString(7));
                message.setMsg_type(c.getString(8));
                c.moveToNext();
                i++;
            }
        }
        c.close();
        return message;
    }

    public Message getLastMessage(String email) {
        Message message = null;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE " + COLUMN_TO_NAME + "!=\"" + email + "\" ORDER BY id DESC LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        c.move(1);
        int i = 0;
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_TO_NAME)) != null) {
                Log.i("TEST", "Entered while and if block");
                message = new Message();
                message.setFrom_name(c.getString(1));
                message.setTo_name(c.getString(2));
                message.setMsg(c.getString(3));
                message.setSent_recd(c.getString(4));
                message.setDatetime(c.getString(5));
                message.setImgURL(c.getString(6));
                message.setStatus(c.getString(7));
                message.setMsg_type(c.getString(8));
                c.moveToNext();
                i++;
            }
        }
        c.close();
        return message;
    }

    static <T> T[] joinArrayGeneric(T[]... arrays) {
        int length = 0;
        for (T[] array : arrays) {
            length += array.length;
        }

        //T[] result = new T[length];
        final T[] result = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), length);

        int offset = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }

    public long addMessageOffline(String msg) {
        String arr[] = new String[3];
        int i = 0;
        for (String str : msg.split(",")) {
            arr[i] = str;
            i++;
        }
        Log.i("TEST", "Entered DBHandler addMessageOffline");
        SQLiteDatabase db;
        ContentValues values = new ContentValues();
        values.put(COLUMN_FROM_NAME_OFFLINE, arr[0]);
        values.put(COLUMN_MSG_OFFLINE, arr[1]);
        values.put(COLUMN_DATE_TIME_OFFLINE, arr[2]);
        db = getWritableDatabase();
        long reg_status = db.insert(TABLE_OFFLINE, null, values);
        return reg_status;
    }

    public String getMessagesOffline() {
        String result = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_OFFLINE;
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        Log.w("DBHandler", "len of query: " + len);
        c.move(1);
        if (len > 0) {
            result = "";
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_FROM_NAME_OFFLINE)) != null) {
                    result = result + c.getString(1) + ",";
                    result = result + c.getString(2) + ",";
                    result = result + c.getString(3) + ",";
                    c.moveToNext();
                }
            }
        }
        c.close();
        if (!result.equals(""))
            return result.substring(0, result.length() - 1);
        else
            return result;
    }

    public long addHost(String type, int port, String ip, String status) {
        SQLiteDatabase db;
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOST_TYPE, type);
        values.put(COLUMN_PORT, port);
        values.put(COLUMN_IPADDRESS, ip);
        values.put(COLUMN_STATUS_HOST, status);
        db = getWritableDatabase();
        long reg_status = db.insert(TABLE_HOST, null, values);
        return reg_status;
    }

    public String getHost() {
        String result = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HOST + " ORDER BY " + COLUMN_ID_HOST + " DESC LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        c.move(1);
        if (len > 0) {
            result = "";
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_HOST_TYPE)) != null) {
                    result = c.getString(1);
                    c.moveToNext();
                }
            }
        }
        c.close();
        return result;
    }

    public String getPort() {
        String result = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HOST + " ORDER BY " + COLUMN_ID_HOST + " DESC LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        c.move(1);
        if (len > 0) {
            result = "";
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_HOST_TYPE)) != null) {
                    result = c.getString(2);
                    c.moveToNext();
                }
            }
        }
        c.close();
        return result;
    }

    public String getIPAddress() {
        String result = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HOST + " ORDER BY " + COLUMN_ID_HOST + " DESC LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        c.move(1);
        if (len > 0) {
            result = "";
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_HOST_TYPE)) != null) {
                    result = c.getString(3);
                    c.moveToNext();
                }
            }
        }
        c.close();
        return result;
    }

    public String getConnectionStatus() {
        String result = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HOST + " ORDER BY " + COLUMN_ID_HOST + " DESC LIMIT 1";
        Cursor c = db.rawQuery(query, null);
        int len = c.getCount();
        c.move(1);
        if (len > 0) {
            result = "";
            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex(COLUMN_HOST_TYPE)) != null) {
                    result = c.getString(4);
                    c.moveToNext();
                }
            }
        }
        c.close();
        return result;
    }

    public void setConnectionStatus(String status) {
        long res=-1;
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE "+TABLE_HOST+" SET "+COLUMN_STATUS_HOST+"=\""+status+"\"";
        db.execSQL(query);
    }

    public void truncateTable(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+TABLE_OFFLINE;
        db.execSQL(query);
    }
}