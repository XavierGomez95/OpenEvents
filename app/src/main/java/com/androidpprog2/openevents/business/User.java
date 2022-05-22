package com.androidpprog2.openevents.business;

import java.io.Serializable;

/**
 * USER CLASS
 */
public class User implements Serializable {
    private Integer id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String image;

    /**
     * Overload constructor 3.
     */
    public User(String name, String last_name, String email, String password, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    /**
     * Overload constructor 2.
     */
    public User(String name, String last_name, String email, String image) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.image = image;
    }

    /**
     * Overload constructor 1.
     */
    public User(String name, String last_name, String email) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
    }

    /**
     * Constructor.
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     *
     * @return the current user id.
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return the current user name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the current user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return the current user last name.
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     *
     * @return the current user image.
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param email set an email to the current user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param name set a name to the current user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param last_name set a last name to the current user.
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
