package com.example.seg2105termproject;

public class Course {

    private int id;
    private String courseName;
    private String courseCode;

    public Course (int id, String name, String code){
        this.id = id;
        this.courseName = name;
        this.courseCode = code;
    }

    public Course (String name, String code){
        this.courseName = name;
        this.courseCode = code;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public void setCourseName(String name){
        this.courseName = name;
    }

    public void setCourseCode(String code){
        this.courseCode = code;
    }
}
