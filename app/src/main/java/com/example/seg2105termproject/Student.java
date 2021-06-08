package com.example.seg2105termproject;

public class Student extends User{

    public Student (String username, String password){
        super(username, password);
    }

    public UserType getType(){
        return UserType.STUDENT;
    }
}
