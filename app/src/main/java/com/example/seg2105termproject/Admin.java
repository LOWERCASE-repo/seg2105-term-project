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
public class Admin extends User{

    public Admin (int id, String username, String password) { super(id, username, password); }
    public Admin (String username, String password){
        super(username, password);
    }

    public UserType getType(){
        return UserType.ADMIN;
    }
}
