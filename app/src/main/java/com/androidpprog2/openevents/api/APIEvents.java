package com.androidpprog2.openevents.api;

import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIEvents {
    private static APIEvents apiEvents;
    private Retrofit retrofit;
    private OpenEventsAPI service;

    public APIEvents() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/v2/").addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(OpenEventsAPI.class);

    }

    public static APIEvents getInstance() {
        if (apiEvents == null) {
            apiEvents = new APIEvents();
        }
        return apiEvents;

    }

    public void addEvent(String token, Event event, Callback<Event> callback) {
        this.service.addEvent(token, event).enqueue(callback);
    }

    public void getEventId(Integer id, Callback<Event> callback) {
        this.service.getEventId(id).enqueue(callback);
    }

    public void getListEvents(Callback<List<Event>> callback) {
        this.service.getListEvents().enqueue(callback);
    }

    public void getEventsBest(Callback<List<Event>> callback) {
        this.service.getEventsBest().enqueue(callback);
    }

    public void getEventsSearch(String location, String keyword, String date, Callback<List<Event>> callback) {
        this.service.getEventsSearch(location, keyword, date).enqueue(callback);
    }

    public void editEvent(Event event, Callback<Event> callback) {
        this.service.editEvent(event).enqueue(callback);
    }

    public void deleteEvent(Integer id, Callback<Event> callback) {
        this.service.deleteEvent(id).enqueue(callback);
    }


}
