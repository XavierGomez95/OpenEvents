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
     * POST CREATE REGISTER USER
     * @param user user registered input
     * @param callback User registered api
     */
    public void addUser(User user, Callback<User> callback) {
        this.service.addUser(user).enqueue(callback);
    }

    /**
     * POST LOGIN USER
     * @param user user email and password
     * @param callback Token
     */
    public void loginUser(User user, Callback<Token> callback) {
        this.service.addLogin(user).enqueue(callback);
    }

    /**
     * GET ALL USERS
     * @param token authorization header with a Bearer Token value.
     * @param callback list all users
     */
    public void getListUsers(String token, Callback<List<User>> callback) {
        this.service.getListUsers(token).enqueue(callback);
    }

    /**
     * GET USER DETAILS BY ID
     * @param token authorization header with a Bearer Token value.
     * @param id user
     * @param callback user
     */
    public void getUser(String token, int id, Callback<User> callback) {
        this.service.getUser(token, id).enqueue(callback);
    }

    /**
     * GET USER LIST BY STRING SEARCH
     * @param token authorization header with a Bearer Token value.
     * @param search string keyword
     * @param callback List users with the keyword in the email
     */
    public void getUserSearch(String token, String search, Callback<List<User>> callback) {
        this.service.getUserSearch(token, search).enqueue(callback);
    }

    /**
     * GET USER STATS BY ID
     * @param token authorization header with a Bearer Token value.
     * @param id user
     * @param callback Stats user
     */
    public void getUserStats(String token, Integer id, Callback<Stats> callback) {
        this.service.getUserStats(token, id).enqueue(callback);
    }

    /**
     * PUT CURRENT USER
     * @param token authorization header with a Bearer Token value.
     * @param user edited user from inputs
     * @param callback edited User from api
     */
    public void editUser(String token, User user, Callback<User> callback) {
        this.service.editUser(token, user).enqueue(callback);
    }

    /**
     * DELETE CURRENT USER
     * @param token authorization header with a Bearer Token value.
     * @param callback User
     */
    public void deleteUser(String token, Callback<User> callback) {
        this.service.deleteUser(token).enqueue(callback);
    }
    //FRIENDS:

    /**
     * POST FRIEND REQUEST
     * @param token authorization header with a Bearer Token value.
     * @param id friend id
     * @param callback Friend Request
     */
    public void addFriendRequest(String token, int id, Callback<FriendRequest> callback) {
        this.service.addFriendRequest(token, id).enqueue(callback);
    }

    /**
     * GET ALL FRIENDS BY USER ID
     * @param token authorization header with a Bearer Token value.
     * @param id current user id
     * @param callback List users
     */
    public void getFriends(String token, int id, Callback<List<User>> callback) {
        this.service.getFriends(token, id).enqueue(callback);
    }

    /**
     * GET ALL FRIEND REQUESTS BY CURRENT USER
     * @param token authorization header with a Bearer Token value.
     * @param listCallback list uses
     */
    public void getFriendRequests(String token, Callback<List<User>> listCallback) {
        this.service.getFriendRequests(token).enqueue(listCallback);
    }

    /**
     * POST FRIEND REQUEST ACCEPTED
     * @param token authorization header with a Bearer Token value.
     * @param id user friend id
     * @param callback Friend Request
     */
    public void acceptFriend(String token, int id, Callback<FriendRequest> callback) {
        this.service.acceptFriend(token, id).enqueue(callback);
    }

    /**
     * DELETE FRIEND REQUEST
     * @param token authorization header with a Bearer Token value.
     * @param id User friend id
     * @param callback FriendRequest
     */
    public void declineFriend(String token, int id, Callback<FriendRequest> callback) {
        this.service.declineFriend(token, id).enqueue(callback);
    }
}
