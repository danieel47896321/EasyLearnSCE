package com.example.easylearnsce.Client.Class;

import java.io.Serializable;

public class YouTubeMessage implements Serializable {
    private String FullName;
    private String Image;
    private String Message;
    private String Date;
    private String Uid;
    public YouTubeMessage(){}
    public YouTubeMessage(String fullName, String image, String message, String date, String uid) {
        FullName = fullName;
        Image = image;
        Message = message;
        Date = date;
        Uid = uid;
    }
    public String getUid() { return Uid; }
    public void setUid(String uid) { Uid = uid; }
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public String getFullName() {
        return FullName;
    }
    public void setFullName(String fullName) {
        FullName = fullName;
    }
    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        Image = image;
    }
    public String getMessage() {
        return Message;
    }
    public void setMessage(String message) {
        Message = message;
    }
}
