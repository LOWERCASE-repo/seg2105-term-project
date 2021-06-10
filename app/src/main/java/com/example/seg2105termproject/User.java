package com.example.seg2105termproject;

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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Placeholders
    // boolean could be replaced with exceptions and proper handling.
    // Methods could be kept here or moved to MainActivity.

    public boolean signUp(String username, String password){
        throw new UnsupportedOperationException();
    }

    public boolean signIn(String username, String password){
        throw new UnsupportedOperationException();
    }

    public abstract UserType getType();
}
