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

    public void addEvent(Event event, Callback<Event> callback) {
        this.service.addEvent(event).enqueue(callback);
    }

    public void loginUser(User user, Callback<Token> callback) {
        this.service.addLogin(user).enqueue(callback);
    }

    public void getListUsers(Callback<List<User>> callback) {
        this.service.getListUsers().enqueue(callback);
    }

    public void getUser(Callback<User> callback, int id) {
        this.service.getUser(id).enqueue(callback);
    }

    public void editUser(User user, Callback<User> callback) {
        this.service.editUser(user).enqueue(callback);
    }


}
