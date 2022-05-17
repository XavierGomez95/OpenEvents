package com.androidpprog2.openevents.api;

import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUser {
    private static APIUser apiUser;
    private Retrofit retrofit;
    private OpenEventsAPI service;

    public APIUser() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/v2/").addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(OpenEventsAPI.class);

    }

    public static APIUser getInstance() {
        if (apiUser == null) {
            apiUser = new APIUser();
        }
        return apiUser;

    }

    public void addUser(User user, Callback<User> callback) {
        this.service.addUser(user).enqueue(callback);
    }

    public void loginUser(User user, Callback<Token> callback) {
        this.service.addLogin(user).enqueue(callback);
    }

    public void getListUsers(String token,Callback<List<User>> callback) {
        this.service.getListUsers(token).enqueue(callback);
    }

    public void getUser(Callback<User> callback, int id) {
        this.service.getUser(id).enqueue(callback);
    }

    public void getUserSearch(String search, Callback<List<User>> callback) {
        this.service.getUserSearch(search).enqueue(callback);
    }

    public void getUserStats(Integer id, Callback<User> callback) {
        this.service.getUserStats(id).enqueue(callback);
    }

    public void editUser(User user, Callback<User> callback) {
        this.service.editUser(user).enqueue(callback);
    }

    public void deleteUser(Callback<User> callback) {
        this.service.deleteUser().enqueue(callback);
    }


}
