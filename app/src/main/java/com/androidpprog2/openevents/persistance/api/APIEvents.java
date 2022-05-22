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
     *
     *
     * @param token authorization header with a Bearer Token value.
     * @param event object with all the attributes.
     * @param callback
     */
    public void addEvent(String token, Event event, Callback<Event> callback) {
        this.service.addEvent(token, event).enqueue(callback);
    }

    /**
     *
     *
     * @param id
     * @param callback
     */
    public void getEventId(Integer id, Callback<Event> callback) {
        this.service.getEventId(id).enqueue(callback);
    }

    /**
     *
     *
     * @param token of the currently logged in user
     * @param callback
     */
    public void getListEvents(String token, Callback<List<Event>> callback) {
        this.service.getListEvents(token).enqueue(callback);
    }

    /**
     *
     *
     * @param callback
     */
    public void getEventsBest(Callback<List<Event>> callback) {
        this.service.getEventsBest().enqueue(callback);
    }

    /**
     *
     *
     * @param token of the currently logged in user
     * @param location
     * @param keyword
     * @param date
     * @param callback
     */
    public void getEventsSearch(String token, String location, String keyword, String date, Callback<List<Event>> callback) {
        this.service.getEventsSearch(token, location, keyword, date).enqueue(callback);
    }

    /**
     *
     *
     * @param token of the currently logged in user
     * @param event
     * @param callback
     */
    public void editEvent(String token, Event event, Callback<Event> callback) {
        this.service.editEvent(token, event).enqueue(callback);
    }

    /**
     *
     *
     * @param token of the currently logged in user
     * @param id
     * @param callback
     */
    public void deleteEvent(String token, Integer id, Callback<Event> callback) {
        this.service.deleteEvent(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token of the currently logged in user
     * @param id
     * @param callback
     */
    public void addEventAssistance(String token, int id, Callback<AssistanceRequest> callback) {
        this.service.addAssistance(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token of the currently logged in user
     * @param id
     * @param callback
     */
    public void deleteEventAssistance(String token, int id, Callback<AssistanceRequest> callback) {
        this.service.deleteAssistance(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token of the currently logged in user
     * @param id
     * @param callback
     */
    public void getMyEvents(String token, Integer id, Callback<List<Event>> callback) {
        this.service.getUserEvents(token, id).enqueue(callback);
    }

}
