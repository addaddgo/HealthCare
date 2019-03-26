package com.kyle.healthcare.controller_data;

import android.graphics.ColorSpace;
import android.util.Log;
import android.widget.ListAdapter;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.kyle.healthcare.R;
import com.kyle.healthcare.bluetooth.Constants;
import com.kyle.healthcare.database.health_driving_data.DrivingFinishData;
import com.kyle.healthcare.database.health_driving_data.DrivingHabitAndAdvice;
import com.kyle.healthcare.database.health_driving_data.HealthData;
import com.kyle.healthcare.database.health_driving_data.SpeechData;
import com.kyle.healthcare.fragment_package.DrivingFragment;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.litepal.util.Const;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataManger implements DataDealInterface{

    //实时数据保存
    private ArrayList<HealthData> healthData;
    private ArrayList<SpeechData> speechData;
    private DrivingFinishData finishData;

    private long startTime;
    //get data from database
    private DataManger(){
        Connector.getDatabase();
        drivingHabitAndAdvice = DataSupport.findFirst(DrivingHabitAndAdvice.class);
        if(drivingHabitAndAdvice == null){
            drivingHabitAndAdvice = new DrivingHabitAndAdvice();
        }
        healthData = new ArrayList<>();
        speechData = new ArrayList<>();
        startTime = System.currentTimeMillis();
        finishData = new DrivingFinishData(startTime);
    }

    private ArrayList<Integer> heartRateArray = new ArrayList<Integer>();
    private ArrayList<Integer> fatigueRateArray = new ArrayList<Integer>();

    public static DataManger dataManger = new DataManger();
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
        this.healthData.add(new HealthData(System.currentTimeMillis(),units[0],units[1],units[2],units[3],fatigueRate));
        Log.i("BlueToothThread","resolve");
    }

    //calculate degree of fatigue
    private int calculateFatigue(int temperature,int heartRate ,int bloodPressure,int bloodFat){
        this.currentFatigue = (int)Math.pow(((temperature - 10)*(temperature - 10) + (heartRate - 10) * (heartRate - 10)+ (bloodPressure - 10) * (bloodPressure - 10) + (bloodFat - 10) * (bloodFat - 10)) / 4.0,0.5);
        return this.currentFatigue;
    }

    //Unusual information
    private final  static int fatigueUnusual = 1;
    private final static int drivingRecordUnusual = 2;
    private final static int heartRateUnusual = 0;

    //situation, if it is usual, currentSituation = -1;
    private int currentSituation;


    //analyze data and give the situation
    public int analyzeSituation(){
        if (this.fatigueRateArray.size() != 0 && this.fatigueRateArray.get(0) > Constants.HEART_RATE_UNUSUAL){
            return fatigueUnusual;
        }
        return -1;
    }


    //data save

    public class ViewDataHolder{
        private ArrayList<Integer> arrayList;
        private int[] current;
        private int all;
        private int number;
        public ArrayList<Integer> getArrayList() {
            return arrayList;
        }

        public int[] getCurrent() {
            return current;
        }

        public int getAll() {
            return all;
        }

        public int getNumber() {
            return number;
        }
    }

    private ViewDataHolder heartViewHolder;
    private ViewDataHolder fatigueViewHolder;


    //view-data holder
    public void setHeartRateViewDataEnd(ArrayList<Integer> integers,int[] current,int all,int number){
        if(this.heartViewHolder == null){
            this.heartViewHolder = new ViewDataHolder();
            this.heartViewHolder.arrayList = integers;
            this.heartViewHolder.current = new int[7];
            System.arraycopy(current,0,this.heartViewHolder.current,0,7);
            this.heartViewHolder.all = all;
            this.heartViewHolder.number = number;
        }else{
            this.heartViewHolder.all = all;
            this.heartViewHolder.number = number;
            this.heartViewHolder.arrayList = integers;
            System.arraycopy(current,0,this.heartViewHolder.current,0,7);
        }
    }

    public void setFatigueRateViewDataEnd(ArrayList<Integer> integers,int[] current){
        if(this.fatigueViewHolder == null){
            this.fatigueViewHolder = new ViewDataHolder();
            this.fatigueViewHolder.arrayList = integers;
            this.fatigueViewHolder.current = new int[10];
            System.arraycopy(current,0,this.fatigueViewHolder.current,0,10);
        }else{
            this.fatigueViewHolder.arrayList = integers;
            System.arraycopy(current,0,this.fatigueViewHolder.current,0,10);
        }
    }

    //get data

    public ViewDataHolder getHeartViewHolder() {
        return heartViewHolder;
    }

    public ViewDataHolder getFatigueViewHolder() {
        return fatigueViewHolder;
    }

    //driving record

    private DrivingData latestDrivingData;
    private LatLng lastLatLng;
    private long lastTime;
    @Override
    public void addDrivingData(LatLng newLatLng) {
        if(this.latestDrivingData == null){
            this.latestDrivingData = new DrivingData();
        }else{
            long currentTime = System.currentTimeMillis();
           latestDrivingData.totalTime = (int)((currentTime - startTime) / 60000);
           double distance = Math.abs(DistanceUtil.getDistance(lastLatLng,newLatLng));
           latestDrivingData.totalDistance += (int)(distance / 1000);
           lastLatLng = newLatLng;
           latestDrivingData.averageSpeech = (int)(distance /((currentTime - lastTime) / 360000));
           this.speechData.add(new SpeechData(System.currentTimeMillis(),latestDrivingData.averageSpeech));
           lastTime = currentTime;
        }
    }

    @Override
    public DrivingData getLatestDrivingInformation() {
        if(this.latestDrivingData == null){
            return null;
        }else{
            return this.latestDrivingData;
        }
    }


    //homepageFragment
    public final static int GIF_NO_CHANGE = 0;
    private int currentGif = R.drawable.sleep_1;
    private int currentFatigue;

    public int getCurrentGifId(){
        int gif;
            if(this.currentFatigue > 17) {
                gif =  R.drawable.sleep_3;
            }else if(this.currentFatigue > 14){
                gif =  R.drawable.sleep_2;
            }else{
                gif =  R.drawable.sleep_1;
            }
            if(gif == currentGif){
                return GIF_NO_CHANGE;
            }else{
                this.currentFatigue = gif;
                return this.currentGif;
            }
    }


    //DrivingHabitFragment
    private DrivingHabitAndAdvice drivingHabitAndAdvice;

    private final static String FATIGUE_DRIVING = "疲劳驾驶";
    private final static String FATIGUE_DRIVING_ADVICE = "请注意休息";

    private final static String OVER_SPEED = "经常超速";
    private final static String OVER_SPEED_ADVICE = "请根据路段选择驾驶速度";

    private final static String OFFENCE_SLAM_THE_BRAKES_ON = "经常急刹车";
    private final static String OFFENCE_SLAM_THE_BRAKES_ON_ADVICE = "经常急刹车";

    private final static String FREE_PARKING = "随意停车";
    private final static String FREE_PARKING_ADVICE = "请找到合适的停车位";

    public ArrayList<String> getStringHabit() {
        return drivingHabitAndAdvice.getHabit();
    }

    public ArrayList<String> getStringsAdvice() {
        return drivingHabitAndAdvice.getAdvice();
    }

    //update the bad driving habit,if there is a new change with drivingHabitAndAdvice,return false
    public boolean updateHabitAndStringsAdvice(){
        return false;
    }



    public void saveThatDriving(){
        this.finishData.totalDistance = this.latestDrivingData.totalDistance;
        this.finishData.endCurrent = System.currentTimeMillis();
        this.drivingHabitAndAdvice.save();
    }

    //开启线程保存数据
    private void saveCurrentData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(HealthData health : healthData){
                    finishData.BloodFatAll += health.getBloodFat();
                    finishData.FatigueAll +=health.getFatigue();
                    finishData.BloodPressureAll += health.getBloodPressure();
                    finishData.TemperatureAll += health.getTemperature();
                    finishData.HeartRateAll +=health.getHeartRate();
                    finishData.BloodFatNumber++;
                    finishData.BloodPressureNumber++;
                    finishData.FatigueNumber++;
                    finishData.HeartRateNumber++;
                    finishData.TemperatureNumber++;
                    health.save();
                    healthData.remove(health);
                }
                for(SpeechData driving : speechData){
                    finishData.SpeechAll += driving.getSpeech();
                    finishData.SpeechNumber++;
                    driving.save();
                    speechData.remove(driving);
                }
            }
        }).start();
    }
}
