package com.example.easylearnsce.Client.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Lecture implements Serializable {
    private String LectureName;
    private String Url;
    private ArrayList<LectureTopic> lectureTopics;
    private Lecture(){lectureTopics = new ArrayList<>();}
    public Lecture(String lectureName, String url) {
        lectureTopics = new ArrayList<>();
        LectureName = lectureName;
        Url = url;
    }
    public void AddTopic(LectureTopic lectureTopic){ lectureTopics.add(lectureTopic); }
    public ArrayList<LectureTopic> getLectureTopics() {
        return lectureTopics;
    }
    public void setLectureTopics(ArrayList<LectureTopic> lectureTopics) {
        this.lectureTopics = lectureTopics;
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
