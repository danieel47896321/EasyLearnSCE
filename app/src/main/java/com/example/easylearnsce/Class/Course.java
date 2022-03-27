package com.example.easylearnsce.Class;

public class Course {
    private String Course_Name = "name";
    private String Course_Teacher_name = "teacher";
    private String Course_Year = "year";
    private String Course_Semester = "semester";
    private String Course_Engineering = "Tag";

    public Course() { }
    public Course(String course_Name, String course_Teacher_name, String course_Year, String course_Semester, String course_Engineering) {
        Course_Name = course_Name;
        Course_Teacher_name = course_Teacher_name;
        Course_Year = course_Year;
        Course_Semester = course_Semester;
        Course_Engineering = course_Engineering;
    }
    public void setCourse_Name(String course_Name) { Course_Name = course_Name; }
    public void setCourse_Teacher_name(String course_Teacher_name) { Course_Teacher_name = course_Teacher_name; }
    public void setCourse_Year(String course_Year) { Course_Year = course_Year; }
    public void setCourse_Semester(String course_Semester) { Course_Semester = course_Semester; }
    public void setCourse_Engineering(String course_Engineering) { Course_Engineering = course_Engineering; }
    public String getCourse_Name() { return Course_Name; }
    public String getCourse_Teacher_name() { return Course_Teacher_name; }
    public String getCourse_Year() { return Course_Year; }
    public String getCourse_Semester() { return Course_Semester; }
    public String getCourse_Engineering() { return Course_Engineering; }
}

