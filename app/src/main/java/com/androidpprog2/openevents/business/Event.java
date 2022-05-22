package com.androidpprog2.openevents.business;

import java.io.Serializable;

/**
 * EVENT CLASS
 */
public class Event implements Serializable {
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

    /**
     * Constructor.
     *
     */
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


    /**
     *
     * @return events name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return image String name.
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @return events description.
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return events id.
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return events location.
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return events start date.
     */
    public String getEventStart_date() {
        return eventStart_date;
    }

    /**
     *
     * @return events end date.
     */
    public String getEventEnd_date() {
        return eventEnd_date;
    }

    /**
     *
     * @return number of participants of an event.
     */
    public Integer getN_participators() {
        return n_participators;
    }

    /**
     *
     * @return events type.
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return events owner id.
     */
    public Integer getOwner_id() {
        return owner_id;
    }

    /**
     *
     * @return events date.
     */
    public String getDate() {
        return date;
    }
}
