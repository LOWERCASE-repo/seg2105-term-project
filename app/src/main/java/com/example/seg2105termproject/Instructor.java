package com.example.seg2105termproject;
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
public class Instructor extends User{

    public Instructor (int id, String username, String password) { super(id, username, password); }
    public Instructor (String username, String password){
        super(username, password);
    }

    public UserType getType(){
        return UserType.INSTRUCTOR;
    }

    public boolean equals(Object obj){
        if (obj == null || !(obj instanceof Instructor)){
            return false;
        } else {
            Instructor other = (Instructor) obj;

            // All instructors should have a non-null username.
            return (this.getUsername().equals(other.getUsername()));
        }
    }
}
