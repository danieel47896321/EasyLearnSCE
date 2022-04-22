package com.example.easylearnsce.Client.Class;

public class YouTubeMessage {
    private String FullName;
    private String Image;
    private String Message;
    private String Date;
    public YouTubeMessage(){}
    public YouTubeMessage(String fullName, String image, String message, String date) {
        FullName = fullName;
        Image = image;
        Message = message;
        Date = date;
    }
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
