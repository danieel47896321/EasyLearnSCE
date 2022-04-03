package com.example.easylearnsce.Class;

public class Lecture {
    private String LectureName;
    private String Url;
    private Lecture(){}
    public Lecture(String lectureName, String url) {
        LectureName = lectureName;
        Url = url;
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
