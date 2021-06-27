package com.example.seg2105termproject;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.Arrays;

/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <sraad062@uottawa.ca>
 *              Jerry S       <jsoon029@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 * 
*/
public class Course {

    private int id;
    private String name;
    private String code;
    private Instructor instructor;
    private DayOfWeek[] days;
    private LocalTime[] startTimes;
    private LocalTime[] endTimes;
    private String description;
    private int capacity;

    public static final String TIME_ARRAYS_LENGTH_NOT_EQ = "Course time arrays are not the same length.";

    public Course (int id, String name, String code){
        this.id = id;
        this.name = name;
        this.code = code;
        this.instructor = null;
        this.days = new DayOfWeek[0];
        this.startTimes = new LocalTime[0];
        this.endTimes = new LocalTime[0];
        this.description = null;
        this.capacity = 0;
    }

    public Course (int id, String name, String code, Instructor instructor){
        this.id = id;
        this.name = name;
        this.code = code;
        this.instructor = instructor;
        this.days = new DayOfWeek[0];
        this.startTimes = new LocalTime[0];
        this.endTimes = new LocalTime[0];
        this.description = null;
        this.capacity = 0;
    }

    /**
     * Complete constructor for the Course class.
     * @param id
     * @param name
     * @param code
     * @param instructor
     * @param days
     * @param startTimes
     * @param endTimes
     * @param description
     * @param capacity
     * @throws IllegalArgumentException if the time arrays are not the same length.
     * @throws NullPointerException if any time array is null.
     */
    public Course (int id, String name, String code, Instructor instructor, DayOfWeek[] days,
                   LocalTime[] startTimes, LocalTime[] endTimes, String description,
                   int capacity) throws IllegalArgumentException, NullPointerException{

        if (days == null || startTimes == null || endTimes == null) throw new NullPointerException();
        if (days.length != startTimes.length || days.length != endTimes.length) throw new IllegalArgumentException(TIME_ARRAYS_LENGTH_NOT_EQ);

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

    /**
     * The complete constructor, minus the course id.
     * @param name
     * @param code
     * @param instructor
     * @param days
     * @param startTimes
     * @param endTimes
     * @param description
     * @param capacity
     * @throws IllegalArgumentException if the time arrays are not the same length.
     * @throws NullPointerException if any time array is null.
     */
    public Course (String name, String code, Instructor instructor, DayOfWeek[] days,
                   LocalTime[] startTimes, LocalTime[] endTimes, String description,
                   int capacity) throws IllegalArgumentException, NullPointerException{

        if (days == null || startTimes == null || endTimes == null) throw new NullPointerException();
        if (days.length != startTimes.length || days.length != endTimes.length) throw new IllegalArgumentException(TIME_ARRAYS_LENGTH_NOT_EQ);

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
        this.instructor = null;
        this.days = new DayOfWeek[0];
        this.startTimes = new LocalTime[0];
        this.endTimes = new LocalTime[0];
        this.description = null;
        this.capacity = 0;
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
        if (instructor == null){
            this.days = new DayOfWeek[0];
            this.startTimes = new LocalTime[0];
            this.endTimes = new LocalTime[0];
            this.description = null;
            this.capacity = 0;
        }
        this.instructor = instructor;
    }

    public void setTimes(DayOfWeek[] days, LocalTime[] startTimes, LocalTime[] endTimes) throws IllegalArgumentException, NullPointerException{
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

    public boolean equals(Object other){
        if (other == null || !(other instanceof Course)){
            return false;
        }

        Course course = (Course) other;
        Boolean b = this.capacity == course.capacity;

        if (instructor == null){
            b = course.instructor == null && b;
        } else {
            b = this.instructor.equals(course.instructor) && b;
        }

        if (description == null){
            b = course.description == null && b;
        } else {
            b = this.description.equals(course.description) && b;
        }

        return b &&
                Arrays.equals(this.days, course.days) &&
                Arrays.equals(this.startTimes, course.startTimes) &&
                Arrays.equals(this.endTimes, course.endTimes);
    }
}
