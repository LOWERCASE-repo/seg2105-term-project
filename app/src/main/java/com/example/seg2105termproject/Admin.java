package com.example.seg2105termproject;

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
