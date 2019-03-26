package com.kyle.healthcare.database.health_driving_data;

import org.litepal.crud.DataSupport;

//完整的一次驾驶所需要的数据
public class DrivingFinishData extends DataSupport {

    //the time when start driving
    private long id;
    public double endCurrent;
    public double totalDistance;


    public int SpeechNumber;
    public int HeartRateNumber;
    public int FatigueNumber;
    public int BloodPressureNumber;
    public int BloodFatNumber;
    public int TemperatureNumber;

    public double SpeechAll;
    public double HeartRateAll;
    public double FatigueAll;
    public double BloodPressureAll;
    public double BloodFatAll;
    public double TemperatureAll;


    public DrivingFinishData(long id) {
        this.id = id;
    }
}
