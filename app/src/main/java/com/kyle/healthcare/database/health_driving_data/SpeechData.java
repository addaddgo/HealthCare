package com.kyle.healthcare.database.health_driving_data;

import org.litepal.crud.DataSupport;


public class SpeechData extends DataSupport {

    //assigned the time
    private long id;
    private int speech;
    public SpeechData(long id, int speech){
        this.speech = speech;
        this.id = id;
    }

    public int getSpeech() {
        return speech;
    }

    public void setSpeech(int speech) {
        this.speech = speech;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
