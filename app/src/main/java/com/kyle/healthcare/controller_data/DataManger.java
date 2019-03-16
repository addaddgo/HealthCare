package com.kyle.healthcare.controller_data;

import android.util.Log;

import com.kyle.healthcare.fragment_package.DrivingFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataManger implements DataDealInterface{


    private ArrayList<Integer> heartRateArray = new ArrayList<Integer>();
    private ArrayList<Integer> fatigueRateArray = new ArrayList<Integer>();


    /*
        蓝牙数据
     */
    //解析数据
    @Override
    public int getHeartRate() {
        return 0;
    }

    @Override
    public int getFatigueRate() {
        return 0;
    }

    //resolve the blue-tooth-data and add the latest heart-rate and fatigue-rate to arrays
    @Override
    public void addBlueToothData(String string) {

    }



    /*
      地图,需要根据当前的坐标和之前的所以数据，给出最新的DrivingData
     */

    private DrivingData latestDrivingData;


    @Override
    public void addDrivingData(float X, float Y) {
        Log.i("DataManager","getXY");
        this.latestDrivingData = new DrivingData();
        this.latestDrivingData.averageSpeech = (int)(Math.random() * 200);
        this.latestDrivingData.totalDistance = (int)(Math.random() * 200);
        this.latestDrivingData.totalTime = (int)(Math.random() * 200);
    }

    @Override
    public DrivingData getLatestDrivingInformation() {
        return this.latestDrivingData;
    }

}
