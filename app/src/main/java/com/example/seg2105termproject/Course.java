package com.example.seg2105termproject;

import java.time.LocalTime;
import java.time.DayOfWeek;

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
    private String name;
    private String code;
    private DayOfWeek[] days;
    private LocalTime[] startTimes;
    private LocalTime[] endTimes;
    private String description;
    private int capacity;

    public Course (int id, String name, String code){
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Course (int id, String name, String code, Instructor instructor){
        this.id = id;
        this.name = name;
        this.code = code;
        this.instructor = instructor;
    }

    public Course (int id, String name, String code, Instructor instructor, DayOfWeek[] days,
                   LocalTime[] startTimes, LocalTime[] endTimes, String description,
                   int capacity){
        if (days.length != startTimes.length || days.length != endTimes.length){
            throw new IllegalArgumentException("Course time arrays are not the same length.");
        }

        this.id = id;
        this.name = name;
        this.code = code;
        this.instructor = instructor;
        this.days = days;
        this.startTimes = startTimes;
        this.endTimes = endTimes;
        this.description = description;
        this.capacity = capacity;
    }

    public Course (String name, String code){
        this.name = name;
        this.code = code;
    }

    public int getId() { return id; }

    public String getName(){
        return this.name;
    }

    public String getCode(){
        return this.code;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public DayOfWeek[] getDays() { return days; }

    public LocalTime[] getStartTimes() { return startTimes; }

    public LocalTime[] getEndTimes() { return endTimes; }

    public String getDescription() { return description; }

    public int getCapacity() { return capacity; }

    public void setId(int id) { this.id = id; }

    public void setName(String name){
        this.name = name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setTimes(DayOfWeek[] days, LocalTime[] startTimes, LocalTime[] endTimes){
        // Check if the arrays are the same length.
        if (days.length != startTimes.length || days.length != endTimes.length){
            throw new IllegalArgumentException("Course time arrays are not the same length.");
        }

        this.days = days;
        this.startTimes = startTimes;
        this.endTimes = endTimes;
    }

    public void setDescription(String description) { this.description = description; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
}
