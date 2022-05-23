package com.androidpprog2.openevents.business;

public class FriendRequest {
    private Integer fieldCount;
    private Integer affectedRows;
    private String info;
    private Integer serverStatus;
    private Integer warningStatus;

    /**
     * Constructor.
     */
    public FriendRequest(Integer fieldCount, Integer affectedRows, String info, Integer serverStatus, Integer warningStatus) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.info = info;
        this.serverStatus = serverStatus;
        this.warningStatus = warningStatus;
    }

}
