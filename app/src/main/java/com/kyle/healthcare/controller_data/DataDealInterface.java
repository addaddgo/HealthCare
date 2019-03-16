package com.kyle.healthcare.controller_data;

import com.kyle.healthcare.fragment_package.DrivingFragment;

public interface DataDealInterface {

    //get the first of heartRateArray
    int getHeartRate();//心率
    //get the first of fatigueRateArray
    int getFatigueRate();

    //添加数据
    void addBlueToothData(String string);//蓝牙
    void addDrivingData(float X,float Y);



    //最新的行驶数据
    DrivingData getLatestDrivingInformation();
}
