package com.example.yogaapp;

public class User {

    public String email;
    public String completed, completed2;

    public User(){
    }

    public User(String email){
        this.email = email;
    }

    public User (String completed, String completed2) {
        this.completed = completed;
        this.completed2 = completed2;
    }
}
