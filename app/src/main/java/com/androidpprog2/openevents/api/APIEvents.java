package com.androidpprog2.openevents.api;

import com.androidpprog2.openevents.business.Assistance;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

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

    public void getEventsSearch(@Header("Authorization") String token, String location, String keyword, String date, Callback<List<Event>> callback) {
        this.service.getEventsSearch(location, keyword, date).enqueue(callback);
    }

    public void editEvent(String token, Event event, Callback<Event> callback) {
        this.service.editEvent(token, event).enqueue(callback);
    }

    public void deleteEvent(String token, Integer id, Callback<Event> callback) {
        this.service.deleteEvent(token, id).enqueue(callback);
    }

    public void addEventAssistance(String token, int id, Callback<Assistance> callback) {
        this.service.addAssistance(token, id).enqueue(callback);
    }

    public void deleteEventAssistance(String token, int id, Callback<Assistance> callback) {
        this.service.deleteAssistance(token, id).enqueue(callback);
    }


}
