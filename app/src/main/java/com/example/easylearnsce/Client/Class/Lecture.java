package com.example.easylearnsce.Client.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Lecture implements Serializable {
    private String LectureName;
    private String Url;
    private String Type;
    private int Number;
    private ArrayList<Topic> topics;
    private Lecture(){ }
    public Lecture(String lectureName, String url, int number, String type) {
        topics = new ArrayList<>();
        LectureName = lectureName;
        Url = url;
        Number = number;
        Type = type;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public int getNumber() {
        return Number;
    }
    public void setNumber(int number) {
        Number = number;
    }
    public String getLectureName() {
        return LectureName;
    }
    public void setLectureName(String lectureName) {
        LectureName = lectureName;
    }
    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }
}
