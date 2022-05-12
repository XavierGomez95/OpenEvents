package com.androidpprog2.openevents.business;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class Token implements Serializable {
    private String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }


    public static String getToken(Context c) {
        SharedPreferences sharedPreferences = c.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("token", "Error, information does not exist.");
        return "Bearer "+accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
