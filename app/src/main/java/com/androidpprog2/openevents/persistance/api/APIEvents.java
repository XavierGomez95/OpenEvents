package com.androidpprog2.openevents.persistance.api;

import com.androidpprog2.openevents.business.AssistanceRequest;
import com.androidpprog2.openevents.business.Event;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API EVENTS CLASS
 */
public class APIEvents {
    private static APIEvents apiEvents;
    private Retrofit retrofit;
    private OpenEventsAPI service;

    /**
     * APIEvents constructor.
     */
    public APIEvents() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/v2/").addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(OpenEventsAPI.class);

    }

    /**
     * Static instance of APIEvents.
     *
     * @return a new instance if has not been created yet.
     */
    public static APIEvents getInstance() {
        if (apiEvents == null) {
            apiEvents = new APIEvents();
        }
        return apiEvents;

    }

    /**
     * POST EVENT
     *
     * @param token    authorization header with a Bearer Token value.
     * @param event    object with all the attributes.
     * @param callback Event
     */
    public void addEvent(String token, Event event, Callback<Event> callback) {
        this.service.addEvent(token, event).enqueue(callback);
    }

    /**
     * GET EVENT BY ID
     *
     * @param id       id event
     * @param callback Event
     */
    public void getEventId(Integer id, Callback<Event> callback) {
        this.service.getEventId(id).enqueue(callback);
    }

    /**
     * GET ALL EVENTS
     *
     * @param token    of the currently logged in user
     * @param callback list events
     */
    public void getListEvents(String token, Callback<List<Event>> callback) {
        this.service.getListEvents(token).enqueue(callback);
    }

    /**
     * GET BEST EVENTS
     *
     * @param callback list events
     */
    public void getEventsBest(String token, Callback<List<Event>> callback) {
        this.service.getEventsBest(token).enqueue(callback);
    }

    /**
     * GET EVENTS BY
     *
     * @param token    of the currently logged in user
     * @param location string
     * @param keyword  string
     * @param date     string
     * @param callback List events
     */
    public void getEventsSearch(String token, String location, String keyword, String date, Callback<List<Event>> callback) {
        this.service.getEventsSearch(token, location, keyword, date).enqueue(callback);
    }

    /**
     * PUT EVENT
     *
     * @param token    of the currently logged in user
     * @param event    edited
     * @param callback Assistance Request
     */
    public void editEvent(String token, Integer id, Event event, Callback<Event> callback) {
        this.service.editEvent(token, id, event).enqueue(callback);
    }

    /**
     * DELETE EVENT
     *
     * @param token    of the currently logged in user
     * @param id       of the event
     * @param callback Event
     */
    public void deleteEvent(String token, Integer id, Callback<Event> callback) {
        this.service.deleteEvent(token, id).enqueue(callback);
    }

    /**
     * POST ASSISTANCE TO AN EVENT
     *
     * @param token    of the currently logged in user
     * @param id       of the event
     * @param callback Assistance Request
     */
    public void addEventAssistance(String token, int id, Callback<AssistanceRequest> callback) {
        this.service.addAssistance(token, id).enqueue(callback);
    }

    /**
     * DELETE ASSISTANCE TO AN EVENT
     *
     * @param token    of the currently logged in user
     * @param id       of the event
     * @param callback Assistance Request
     */
    public void deleteEventAssistance(String token, int id, Callback<AssistanceRequest> callback) {
        this.service.deleteAssistance(token, id).enqueue(callback);
    }

    /**
     * GET MY EVENTS
     *
     * @param token    of the currently logged in user
     * @param id       of the user
     * @param callback List events
     */
    public void getMyEvents(String token, Integer id, Callback<List<Event>> callback) {
        this.service.getUserEvents(token, id).enqueue(callback);
    }

}
