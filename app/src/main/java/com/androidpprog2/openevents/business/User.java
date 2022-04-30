package com.androidpprog2.openevents.business;

import java.util.List;

public class User {
    int id;
    String name;
    String lastName;
    String email;
    String password;
    String image;
    List<String> friends;
    List<String> events;

    public User(String name, String lastName, String email, String password, String image) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.image = image;
    }
}
