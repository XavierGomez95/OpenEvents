package com.androidpprog2.openevents.business;

public class FriendRequest {
    private Integer fieldCount;
    private Integer affectedRows;
    private String info;
    private Integer serverStatus;
    private Integer warningStatus;

    public FriendRequest(Integer fieldCount, Integer affectedRows, String info, Integer serverStatus, Integer warningStatus) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.info = info;
        this.serverStatus = serverStatus;
        this.warningStatus = warningStatus;
    }

    public Integer getFieldCount() {
        return fieldCount;
    }

    public Integer getAffectedRows() {
        return affectedRows;
    }

    public String getInfo() {
        return info;
    }

    public Integer getServerStatus() {
        return serverStatus;
    }

    public Integer getWarningStatus() {
        return warningStatus;
    }
}
