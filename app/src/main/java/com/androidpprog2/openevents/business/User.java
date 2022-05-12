package com.androidpprog2.openevents.business;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    //Integer id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String image;
    //List<String> friends;
    //List<String> events;

    public User(String name, String last_name, String email, String password, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
    }
    public User(String name, String last_name, String email, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.image = image;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
