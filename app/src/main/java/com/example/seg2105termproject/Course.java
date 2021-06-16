package com.example.seg2105termproject;
/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <@uottawa.ca> 
 *              Jerry S       <jsoon029@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 * 
*/
public class Course {

    private Instructor instructor;
    private int id;
    private String courseName;
    private String courseCode;

    public Course (int id, String name, String code){
        this.id = id;
        this.courseName = name;
        this.courseCode = code;
    }

    public Course (int id, String name, String code, Instructor instructor){
        this.id = id;
        this.courseName = name;
        this.courseCode = code;
        this.instructor = instructor;
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

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Instructor getInstructor() {
        return instructor;
    }
}
