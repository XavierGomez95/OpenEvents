package com.androidpprog2.openevents.business;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    int id;
    String name;
    String last_name;
    String email;
    String password;
    String image;
    List<String> friends;
    List<String> events;

    public User(String name, String last_name, String email, String password, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
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
