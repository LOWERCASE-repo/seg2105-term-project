package com.example.seg2105termproject;

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
public abstract class User {

    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public int getId() { return id; }

    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract UserType getType();

    public boolean equals(Object other){
        if (other == null || this.getClass() != other.getClass()){
            return false;
        }

        User user = (User) other;
        boolean b = this.id == user.id &&
                this.getType().equals(user.getType());

        if (this.username == null){
            b = b && user.username == null;
        } else {
            b = b && this.username.equals(user.username);
        }

        if (this.password == null){
            b = b && user.password == null;
        } else {
            b = b && this.password.equals(user.password);
        }

        return b;
    }
}
