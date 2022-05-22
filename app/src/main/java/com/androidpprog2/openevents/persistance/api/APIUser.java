package com.androidpprog2.openevents.persistance.api;

import com.androidpprog2.openevents.business.FriendRequest;
import com.androidpprog2.openevents.business.Stats;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * API USER CLASS
 */
public class APIUser {
    private static APIUser apiUser;
    private Retrofit retrofit;
    private OpenEventsAPI service;

    /**
     * APIUser constructor.
     */
    public APIUser() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/v2/").addConverterFactory(GsonConverterFactory.create())
                .build();
        this.service = retrofit.create(OpenEventsAPI.class);

    }

    /**
     * Static instance of APIUser.
     *
     * @return a new instance if has not been created yet.
     */
    public static APIUser getInstance() {
        if (apiUser == null) {
            apiUser = new APIUser();
        }
        return apiUser;
    }


    /**
     *
     *
     * @param user
     * @param callback
     */
    public void addUser(User user, Callback<User> callback) {
        this.service.addUser(user).enqueue(callback);
    }

    /**
     *
     *
     * @param user
     * @param callback
     */
    public void loginUser(User user, Callback<Token> callback) {
        this.service.addLogin(user).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param callback
     */
    public void getListUsers(String token, Callback<List<User>> callback) {
        this.service.getListUsers(token).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param id
     * @param callback
     */
    public void getUser(String token, int id, Callback<User> callback) {
        this.service.getUser(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param search
     * @param callback
     */
    public void getUserSearch(String token, String search, Callback<List<User>> callback) {
        this.service.getUserSearch(token, search).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param id
     * @param callback
     */
    public void getUserStats(String token, Integer id, Callback<Stats> callback) {
        this.service.getUserStats(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param user
     * @param callback
     */
    public void editUser(String token, User user, Callback<User> callback) {
        this.service.editUser(token, user).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param callback
     */
    public void deleteUser(String token, Callback<User> callback) {
        this.service.deleteUser(token).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param id
     * @param callback
     */
    public void addFriendRequest(String token, int id, Callback<FriendRequest> callback) {
        this.service.addFriendRequest(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param id
     * @param callback
     */
    public void getFriends(String token, int id, Callback<List<User>> callback) {
        this.service.getFriends(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param listCallback
     */
    public void getFriendRequests(String token, Callback<List<User>> listCallback) {
        this.service.getFriendRequests(token).enqueue(listCallback);
    }

    /**
     *
     *
     * @param token
     * @param id
     * @param callback
     */
    public void acceptFriend(String token, int id, Callback<FriendRequest> callback) {
        this.service.acceptFriend(token, id).enqueue(callback);
    }

    /**
     *
     *
     * @param token
     * @param id
     * @param callback
     */
    public void declineFriend(String token, int id, Callback<FriendRequest> callback) {
        this.service.declineFriend(token, id).enqueue(callback);
    }
}
