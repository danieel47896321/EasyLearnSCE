package com.example.easylearnsce.Class;
import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable {
    private String Uid = "Uid";
    private String Email = "test@email.com";
    private String FullName = "FullName";
    private String FirstName = "FirstName";
    private String LastName = "LastName";
    private String Gender = "Male";
    private String Type = "Student";
    private String Permission = "Low";
    private String Day = Calendar.YEAR+"";
    private String Month = Calendar.MONTH+"";
    private String Year = Calendar.DAY_OF_MONTH+"";
    private String BirthDay = Day + "/" + Month + "/" + Year;
    private String Image = "Image";
    private String City = "באר שבע";
    private String Engineering = "null";
    private String Course = "null";
    private String Lecture = "null";
    public User(String FirstName, String LastName, String Email) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
    }
    public User() {}
    public User(User user) {
        this.Uid = user.getUid();
        this.Email = user.getEmail();
        this.FirstName = user.getFirstName();
        this.LastName = user.getLastName();
        this.Gender = user.getGender();
        this.Type = user.getType();
        this.Image = user.getImage();
        this.City = user.getCity();
    }
    //getters
    public String getEmail() { return Email; }
    public String getFirstName() { return FirstName; }
    public String getLastName() { return LastName; }
    public String getImage() { return Image; }
    public String getGender() { return Gender; }
    public String getType() { return Type; }
    public String getCity() { return City; }
    public String getUid() { return Uid; }
    public String getEngineering() { return Engineering; }
    public String getCourse() { return Course; }
    public String getLecture() { return Lecture; }
    public String getFullName() { return FullName; }
    public String getDay() { return Day; }
    public String getMonth() { return Month; }
    public String getYear() { return Year; }
    public String getBirthDay() { return BirthDay; }
    public String getPermission() { return Permission; }
    //setters
    public void setPermission(String permission) { Permission = permission; }
    public void setBirthDay(String birthDay) { BirthDay = birthDay; }
    public void setYear(String year) { Year = year; }
    public void setMonth(String month) { Month = month; }
    public void setDay(String day) { Day = day; }
    public void setEmail(String email) { Email = email; }
    public void setFirstName(String firstName) { this.FirstName = firstName; }
    public void setLastName(String lastName) { LastName = lastName; }
    public void setImage(String image) { Image = image; }
    public void setGender(String gender) { Gender = gender; }
    public void setType(String type) { Type = type; }
    public void setUid(String uid) { Uid = uid; }
    public void setCity(String city) { City = city; }
    public void setEngineering(String engineering) { Engineering = engineering; }
    public void setCourse(String course) { Course = course; }
    public void setLecture(String lecture) { Lecture = lecture; }
    public void setFullName(String fullName) { FullName = fullName; }
}
