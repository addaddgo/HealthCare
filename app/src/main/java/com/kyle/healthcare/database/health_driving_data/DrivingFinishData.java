package com.kyle.healthcare.database.health_driving_data;

import org.litepal.crud.DataSupport;

public class DrivingFinishData extends DataSupport {

    //the time when start driving
    private int id;
    private int endCurrent;
    private int averageSpeech;
    private int totalDistance;
    private int averageHeartRate;
    private int averageFatigue;
    private int averageBloodPressure;
    private int averageBloodFat;
    private int averageTemperature;
}
