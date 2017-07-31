package com.chatengine.chatengine;

public class User {

    private String firstname,lastname,email,mobile,dob,password,gender,joined_date,msg_table,status,imgURL,last_online;

    public User(String firstname, String lastname, String email, String password, String dob, String mobile, String gender, String joined_date, String msg_table, String status, String imgURL, String last_online) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.password = password;
        this.gender = gender;
        this.joined_date = joined_date;
        this.msg_table = msg_table;
        this.status = status;
        this.imgURL = imgURL;
        this.last_online = last_online;
    }

    public User() {
    }

    public String getJoined_date() {
        return joined_date;
    }

    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }

    public String getMsg_table() {
        return msg_table;
    }

    public void setMsg_table(String msg_table) {
        this.msg_table = msg_table;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getLast_online() {
        return last_online;
    }

    public void setLast_online(String last_online) {
        this.last_online = last_online;
    }
}
