package com.example.easylearnsce.Class;

public class Tag {
    private String TagName;
    private int Photo;
    public Tag() { }
    public Tag(String tagName, int photo) {
        this.TagName = tagName;
        this.Photo = photo;
    }
    public int getPhoto() { return Photo; }
    public String getTagName() { return TagName; }
    public void setTagName(String tagName) {  this.TagName = tagName; }
    public void setPhoto(int photo) {  this.Photo = photo; }
}

