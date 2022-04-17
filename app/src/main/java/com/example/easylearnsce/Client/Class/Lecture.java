package com.example.easylearnsce.Client.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Lecture implements Serializable {
    private String LectureName;
    private String Url;
    private ArrayList<Topic> Topics;
    private Lecture(){ Topics = new ArrayList<>();}
    public Lecture(String lectureName, String url) {
        Topics = new ArrayList<>();
        LectureName = lectureName;
        Url = url;
    }
    public void AddTopic(Topic lectureTopic){ Topics.add(lectureTopic); }
    public ArrayList<Topic> getTopics() {
        return Topics;
    }
    public void setTopics(ArrayList<Topic> topics) {
        this.Topics = topics;
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
