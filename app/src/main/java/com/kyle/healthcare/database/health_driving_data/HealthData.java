package com.kyle.healthcare.database.health_driving_data;

import org.litepal.crud.DataSupport;

public class HealthData extends DataSupport {

    //assigned the time when it is received
    private long id;

    private int heartRate;
    private int temperature;
    private int bloodPressure;
    private int bloodFat;
    private int fatigue;

    public HealthData(long id, int heartRate, int temperature, int bloodPressure, int bloodFat,int fatigue) {
        this.id = id;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
        this.bloodFat = bloodFat;
        this.fatigue = fatigue;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getBloodFat() {
        return bloodFat;
    }

    public void setBloodFat(int bloodFat) {
        this.bloodFat = bloodFat;
    }


    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }
}
