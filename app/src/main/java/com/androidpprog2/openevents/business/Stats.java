package com.androidpprog2.openevents.business;

import java.io.Serializable;

public class Stats implements Serializable {
    private String avg_score;
    private Integer num_comments;
    private String percentage_commenters_below;

    public String getAvg_score() {
        return avg_score;
    }

    public Integer getNum_comments() {
        return num_comments;
    }

    public String getPercentage_commenters_below() {
        return percentage_commenters_below;
    }
}
