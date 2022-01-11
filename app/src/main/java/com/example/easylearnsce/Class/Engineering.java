package com.example.easylearnsce.Class;

public class Engineering {
    private String Engineeringname;
    private int Photo;
    public Engineering() { }
    public Engineering(String engineeringname, int photo) {
        this.Engineeringname = engineeringname;
        this.Photo = photo;
    }
    public int getPhoto() { return Photo; }
    public String getEngineeringname() { return Engineeringname; }
    public void setEngineeringname(String engineeringname) {  this.Engineeringname = engineeringname; }
    public void setPhoto(int photo) {  this.Photo = photo; }
}

