package com.androidpprog2.openevents.api;


import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenEventsAPI {

    //-------------USERS--------------
    @POST("users")
    Call<User> addUser(@Body User user);

    @POST("users/login")
    Call<Token> addLogin(@Body User user);

    @GET("users")
    Call<List<User>> getListUsers(@Header("Authorization") String token);

    @GET("users/{id}")
    Call<User> getUser(@Path("id") Integer id);

    @GET("users/search")
    Call<List<User>> getUserSearch(@Header("Authorization") String token, @Query("s") String userS);

    @GET("users/{id}/statistics")
    Call<User> getUserStats(@Path("id") Integer id);

    @PUT("users")
    Call<User> editUser(@Body User user);

    @DELETE("users")
    Call<User> deleteUser();

    @GET("users/{id}/events")
    Call<List<Event>> getUserEvents(@Path("id") Integer id);

    @GET("users/{id}/events/future")
    Call<List<Event>> getUserFutureEvents(@Path("id") Integer id);

    @GET("users/{id}/events/finished")
    Call<List<Event>> getUserFinishedEvents(@Path("id") Integer id);

    @GET("users/{id}/events/current")
    Call<List<Event>> getUserCurrentEvents(@Path("id") Integer id);

    @GET("users/{id}/assistances")
    Call<List<Event>> getAssistAllEvents(@Path("id") Integer id);

    @GET("users/{id}/assistances/future")
    Call<List<Event>> getAssistFutureEvents(@Path("id") Integer id);

    @GET("users/{id}/assistances/finished")
    Call<List<Event>> getAssistFinishedEvents(@Path("id") Integer id);

    @GET("users/{id}/friends")
    Call<List<User>> getFriends(@Path("id") Integer id);

    //-------------EVENTS--------------
    @POST("events")
    Call<Event> addEvent(@Header("Authorization") String token, @Body Event event);

    @GET("events")
    Call<List<Event>> getListEvents();

    @GET("events/{id}")
    Call<Event> getEventId(@Path("id") Integer id);

    @GET("events/best")
    Call<List<Event>> getEventsBest();

    @GET("events/search")
    Call<List<Event>> getEventsSearch(@Query("location") String loc, @Query("keyword") String kw, @Query("date") String date);

    @PUT("events/{id}")
    Call<Event> editEvent(@Body Event event);

    @DELETE("events/{id}")
    Call<Event> deleteEvent(@Path("id") Integer id);

    @GET("events/{id}/assistances")
    Call<List<User>> getAssistances(@Path("id") Integer id);

    @GET("events/{event_id}/assistances/{user_id}")
    Call<Object> get1Assistance(@Path("event_id") Integer event_id, @Path("user_id") Integer user_id);

    @POST("events/{id}/assistances")
    Call<Object> addAssistance(@Path("id") Integer id);

    @PUT("events/{id}/assistances")
    Call<Object> editAssistance(@Path("id") Integer id);

    @DELETE("events/{id}/assistances")
    Call<Object> deleteAssistance(@Path("id") Integer id);

    //-------------ASSISTANCES--------------
}
