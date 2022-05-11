package com.androidpprog2.openevents.view;

public class AppState {
    private static AppState singleInstance;

    private boolean isLogOut;

    private AppState() {
    }

    public static AppState getSingleInstance() {
        if (singleInstance == null) {
            singleInstance = new AppState();
        }
        return singleInstance;
    }

    public boolean isLogOut() {
        return isLogOut;
    }

    public void setLogOut(boolean isLogOut) {
        this.isLogOut = isLogOut;
    }
}
