package com.example.seg2105termproject;

public class Instructor extends User{

    public Instructor (String username, String password){
        super(username, password);
    }

    public UserType getType(){
        return UserType.INSTRUCTOR;
    }
}
