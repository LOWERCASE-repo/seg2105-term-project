package com.example.seg2105termproject;
/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <@uottawa.ca> 
 *              Jerry S       <@uottawa.ca>
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
}
