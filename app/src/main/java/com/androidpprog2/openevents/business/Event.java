package com.androidpprog2.openevents.business;

import java.util.Date;

public class Event {
    int id;
    String name;
    int ownerId;
    String date;
    String image;
    String location;
    String description;
    Date eventStart;
    Date eventEnd;

    // TEMPORAL PRUEBAS RECYCLERVIEW
    public Event(int id, String name, int ownerId, String date, String image, String location,
                 String description, Date eventStart, Date eventEnd) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.date = date;
        this.image = image;
        this.location = location;
        this.description = description;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
