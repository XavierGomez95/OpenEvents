package com.androidpprog2.openevents.business;

import java.io.Serializable;

public class Assistance implements Serializable {
    private Integer fieldCount;
    private Integer affectedRows;
    private Integer insertId;
    private String info;
    private Integer serverStatus;
    private Integer warningStatus;

    public Assistance(Integer fieldCount, Integer affectedRows, Integer insertId, String info, Integer serverStatus, Integer warningStatus) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.info = info;
        this.serverStatus = serverStatus;
        this.warningStatus = warningStatus;
    }

}
