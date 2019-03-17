package com.kyle.healthcare.controller_data;

import com.kyle.healthcare.fragment_package.DrivingFragment;

public interface DataDealInterface {

    //bluetooth-data

    int getLengthOfHeartRateArray();
    //get the first of heartRateArray
    int getHeartRate();//心率

    int getLengthOfFatigueRateArray();
    //get the first of fatigueRateArray
    int getFatigueRate();

    //latest bluetooth-data
    void addBlueToothData(String string);



    //latest driving record
    DrivingData getLatestDrivingInformation();
    //add latest driving record
    void addDrivingData(float X,float Y);


    //unusual
    int analyzeSituation();
}
