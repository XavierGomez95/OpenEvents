package com.androidpprog2.openevents.business;

import java.io.Serializable;
import java.util.Date;

public class
Event implements Serializable {
    private Integer id;
    private String name;
    private String image;
    private String location;
    private String description;
    private String eventStart_date;
    private String eventEnd_date;
    private Integer n_participators;
    private String type;
    private Integer owner_id;
    private String date;


    public Event(String name, String image, String location,
                 String description, String eventStart_date, String eventEnd_date, int n_participators, String type, int owner_id, String date) {
        this.name = name;
        this.image = image;
        this.location = location;
        this.description = description;
        this.eventStart_date = eventStart_date;
        this.eventEnd_date = eventEnd_date;
        this.n_participators = n_participators;
        this.type = type;
        this.owner_id = owner_id;
        this.date = date;

    }

    public Event(String name, String image, String location,
                 String description, String eventStart_date, String eventEnd_date, int n_participators, String type) {
        this.name = name;
        this.image = image;
        this.location = location;
        this.description = description;
        this.eventStart_date = eventStart_date;
        this.eventEnd_date = eventEnd_date;
        this.n_participators = n_participators;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
