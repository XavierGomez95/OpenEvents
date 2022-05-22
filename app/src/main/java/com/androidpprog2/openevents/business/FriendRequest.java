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

    // TODO: COMENTAR LOS GETTERS

    /**
     *
     * @return
     */
    public Integer getFieldCount() {
        return fieldCount;
    }

    /**
     *
     * @return
     */
    public Integer getAffectedRows() {
        return affectedRows;
    }

    /**
     *
     * @return
     */
    public String getInfo() {
        return info;
    }

    /**
     *
     * @return
     */
    public Integer getServerStatus() {
        return serverStatus;
    }

    /**
     *
     * @return
     */
    public Integer getWarningStatus() {
        return warningStatus;
    }
}
