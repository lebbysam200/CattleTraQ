package com.example.batendi.cattletraq;

import com.firebase.client.Firebase;

/**
 * Created by batendi on 3/17/16.
 */
public class User {


    private String name;
    private String username;
    String password;
    boolean online;

    public User(){
        this.online = false;
    }
    public User(String name, String username,String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.online = false;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

