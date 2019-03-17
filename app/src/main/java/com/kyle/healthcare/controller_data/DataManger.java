package com.kyle.healthcare.controller_data;

import android.util.Log;

import com.kyle.healthcare.fragment_package.DrivingFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataManger implements DataDealInterface{


    private ArrayList<Integer> heartRateArray = new ArrayList<Integer>();
    private ArrayList<Integer> fatigueRateArray = new ArrayList<Integer>();


    @Override
    public int getLengthOfHeartRateArray() {
        return this.heartRateArray.size();
    }

    @Override
    public int getHeartRate() {
    if(this.heartRateArray.size() != 0){
            Integer integer = this.heartRateArray.get(0);
            this.heartRateArray.remove(0);
            return integer;
        }
        return 0;
    }

    @Override
    public int getLengthOfFatigueRateArray() {
        return this.fatigueRateArray.size();
    }

    @Override
    public int getFatigueRate() {
        if(this.fatigueRateArray.size() != 0){
            Integer integer = this.fatigueRateArray.get(0);
            this.fatigueRateArray.remove(0);
            return integer;
        }
        return 0;
    }

    //resolve the blue-tooth-data and add the latest heart-rate and fatigue-rate to arrays
    @Override
    public void addBlueToothData(String string) {
        String[] unitsUndivided = string.split(",");
        int[] units = new int[4];
        for (int i = 0; i < units.length; i++) {
            int a = unitsUndivided[i].length() - 1;
            //subSequence,without the first one,if start number = last number.return ""
            units[i] = Integer.parseInt(unitsUndivided[i].subSequence(1,unitsUndivided[i].length()).toString());
        }
        Integer heartRate = units[1];
        Integer fatigueRate = calculateFatigue(units[0],units[1],units[2],units[3]);
        this.fatigueRateArray.add(fatigueRate);
        this.heartRateArray.add(heartRate);
        Log.i("BlueToothThread","resolve");
    }

    //calculate degree of fatigue
    private int calculateFatigue(int temperature,int heartRate ,int bloodPressure,int bloodFat){
        return (int)Math.pow(((temperature - 10)*(temperature - 10) + (heartRate - 10) * (heartRate - 10)+ (bloodPressure - 10) * (bloodPressure - 10) + (bloodFat - 10) * (bloodFat - 10)) / 4.0,0.5);
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




    //Unusual information
    private final  static int fatigueUnusual = 1;
    private final static int drivingRecordUnusual = 2;
    private final static int heartRateUnusual = 0;

    //situation, if it is usual, currentSituation = -1;
    private int currentSituation;


    //analyze data and give the situation
    public int analyzeSituation(){
        if (this.fatigueRateArray.size() != 0 && this.fatigueRateArray.get(0) > 10){
            return fatigueUnusual;
        }
        return -1;
    }

}
