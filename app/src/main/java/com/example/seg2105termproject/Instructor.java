package com.example.seg2105termproject;

public class Instructor extends User{

    public Instructor (int id, String username, String password) { super(id, username, password); }
    public Instructor (String username, String password){
        super(username, password);
    }

    public UserType getType(){
        return UserType.INSTRUCTOR;
    }
}
