package com.example.seg2105termproject;

public class Student extends User{

    public Student (int id, String username, String password) { super(id, username, password); }
    public Student (String username, String password){
        super(username, password);
    }

    public UserType getType(){
        return UserType.STUDENT;
    }
}
