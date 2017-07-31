package com.chatengine.chatengine;

public class Message {

    private String from_name,to_name,msg,sent_recd,datetime,imgURL,status,msg_type;

    public Message(String from_name, String to_name, String msg, String sent_recd, String datetime, String imgURL, String status, String msg_type) {
        this.from_name = from_name;
        this.to_name = to_name;
        this.msg = msg;
        this.sent_recd = sent_recd;
        this.datetime = datetime;
        this.imgURL = imgURL;
        this.status = status;
        this.msg_type = msg_type;
    }

    public Message() {
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSent_recd() {
        return sent_recd;
    }

    public void setSent_recd(String sent_recd) {
        this.sent_recd = sent_recd;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
