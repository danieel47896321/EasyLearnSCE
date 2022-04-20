package com.example.easylearnsce.Client.Class;

import java.io.Serializable;

public class Course implements Serializable {
    private String CourseName = "name";
    private String TeacherName = "teacher";
    private String CourseYear = "year";
    private String CourseSemester = "semester";
    private String CourseEngineering = "Tag";
    private String Id = "1" ;
    public Course() { }
    public Course(String courseName, String teacherName, String courseYear, String courseSemester, String courseEngineering) {
        CourseName = courseName;
        TeacherName = teacherName;
        CourseYear = courseYear;
        CourseSemester = courseSemester;
        CourseEngineering = courseEngineering;
    }
    public void setCourseName(String courseName) { CourseName = courseName; }
    public void setTeacherName(String teacherName) { TeacherName = teacherName; }
    public void setCourseYear(String courseYear) { CourseYear = courseYear; }
    public void setCourseSemester(String courseSemester) { CourseSemester = courseSemester; }
    public void setCourseEngineering(String courseEngineering) { CourseEngineering = courseEngineering; }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getCourseName() { return CourseName; }
    public String getTeacherName() { return TeacherName; }
    public String getCourseYear() { return CourseYear; }
    public String getCourseSemester() { return CourseSemester; }
    public String getCourseEngineering() { return CourseEngineering; }
}

