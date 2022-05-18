package com.androidpprog2.openevents.business;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getImage() {
        return image;
    }

    public Integer getId () {return id; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
