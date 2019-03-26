package com.kyle.healthcare.database.health_driving_data;

import org.litepal.crud.DataSupport;

public class DrivingData extends DataSupport {

    //assigned the time
    private int id;
    private int speech;
    public DrivingData(int speech) {
        this.speech = speech;
    }

    public int getSpeech() {
        return speech;
    }

    public void setSpeech(int speech) {
        this.speech = speech;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
