package com.example.easylearnsce.Class;

public class Select {
    private String Engineeringname;
    private int Photo;
    public Select() { }
    public Select(String engineeringname, int photo) {
        this.Engineeringname = engineeringname;
        this.Photo = photo;
    }
    public int getPhoto() { return Photo; }
    public String getEngineeringname() { return Engineeringname; }
    public void setEngineeringname(String engineeringname) {  this.Engineeringname = engineeringname; }
    public void setPhoto(int photo) {  this.Photo = photo; }
}

