package com.androidpprog2.openevents.business;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * TOKEN CLASS
 */
public class Token implements Serializable {
    private String accessToken;

    /**
     * Constructor.
     */
    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @param context of the activity that calls the method.
     * @return the token String needed to be used in the API calls.
     */
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "Error, information does not exist.");
        return "Bearer " + token;
    }

    /**
     *
     * @return the accessToken.
     */
    public String getAccessToken() {
        return accessToken;
    }
}
