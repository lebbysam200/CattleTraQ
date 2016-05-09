package com.example.batendi.cattletraq;

import com.firebase.client.Firebase;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by batendi on 3/17/16.
 */
public class User {


    public String name;
    private String username;
    public String password;
    boolean online;
    public static String onlineUser;
    public static String onlineUserType;

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

