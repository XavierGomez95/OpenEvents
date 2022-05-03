package com.androidpprog2.openevents.api;


import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenEventsAPI {
    //USERS
    @POST("users")
    Call<User> addUser(@Body User user);

    @POST("users/login")
    Call<Token> addLogin(@Body User user);

    @GET("users")
    Call<List<User>> getListUsers();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") Integer id);

    @GET("users/search")
    Call<List<User>> getUser(@Query("s") String userS);

    @GET("users/{id}/statistics")
    Call<User> getUserStats(@Path("id") Integer id);

    @PUT("users")
    Call<User> editUser(@Body User user);

    @DELETE("users")
    Call<User> deleteUser();


}
