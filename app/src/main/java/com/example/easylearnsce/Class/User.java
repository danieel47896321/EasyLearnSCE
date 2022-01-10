package com.example.easylearnsce.Class;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Base64;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class User implements Serializable {
    private String Uid = "Uid";
    private String Email = "test@email.com";
    private String FullName = "FullName";
    private String Firstname = "FirstName";
    private String Lastname = "LastName";
    private String Gender = "Male";
    private String Type = "Student";
    private String Age = "18";
    private String Image = "Image";
    private String City = "באר שבע";
    private String Engineering = "null";
    private String Course = "null";
    private String Lecture = "null";
    public User(String firstname, String lastname, String email) {
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
    }
    public User() {}
    public User(User user) {
        this.Uid = user.getUid();
        this.Email = user.getEmail();
        this.Firstname = user.getFirstname();
        this.Lastname = user.getLastname();
        this.Gender = user.getGender();
        this.Type = user.getType();
        this.Image = user.getImage();
        this.City = user.getCity();
    }
    //getters
    public String getEmail() { return Email; }
    public String getFirstname() { return Firstname; }
    public String getLastname() { return Lastname; }
    public String getImage() { return Image; }
    public String getGender() { return Gender; }
    public String getType() { return Type; }
    public String getAge() { return Age; }
    public String getCity() { return City; }
    public String getUid() { return Uid; }
    public String getEngineering() { return Engineering; }
    public String getCourse() { return Course; }
    public String getLecture() { return Lecture; }
    public String getFullName() { return FullName; }
    //setters
    public void setEmail(String email) { Email = email; }
    public void setFirstname(String firstname) { Firstname = firstname; }
    public void setLastname(String lastname) { Lastname = lastname; }
    public void setImage(String image) { Image = image; }
    public void setGender(String gender) { Gender = gender; }
    public void setType(String type) { Type = type; }
    public void setAge(String age) { Age = age; }
    public void setUid(String uid) { Uid = uid; }
    public void setCity(String city) { City = city; }
    public void setEngineering(String engineering) { Engineering = engineering; }
    public void setCourse(String course) { Course = course; }
    public void setLecture(String lecture) { Lecture = lecture; }
    public void setFullName(String fullName) { FullName = fullName; }
}
