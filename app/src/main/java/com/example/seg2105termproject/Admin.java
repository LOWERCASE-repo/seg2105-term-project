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
public class Admin extends User{

    public Admin (int id, String username, String password) { super(id, username, password); }
    public Admin (String username, String password){
        super(username, password);
    }

    public UserType getType(){
        return UserType.ADMIN;
    }

    // Placeholders for now:
    // boolean return type could be replaced with exceptions and proper handling.

    public boolean createCourse(String courseName, String courseCode){
        throw new UnsupportedOperationException();
    }

    public boolean editCourseCode(String oldCode, String newCode){
        throw new UnsupportedOperationException();
    }

    public boolean editCourseName(String courseCode, String newName){
        throw new UnsupportedOperationException();
    }

    public boolean deleteCourse(String courseCode){
        throw new UnsupportedOperationException();
    }

    public boolean deleteUser(String username){
        throw new UnsupportedOperationException();
    }
}
