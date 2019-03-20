package com.kyle.healthcare.controller_data;

public class HistoryCondition {

    private String date;
    private String situation;

    public HistoryCondition(String date, String situation) {
        this.date = date;
        this.situation = situation;
    }

    public String getDate() {
        return date;
    }

    public String getSituation() {
        return situation;
    }

}
