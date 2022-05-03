package com.androidpprog2.openevents.business;

import java.io.Serializable;

public class Token implements Serializable {
    private String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
