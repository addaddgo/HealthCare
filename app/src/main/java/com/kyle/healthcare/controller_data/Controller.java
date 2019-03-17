package com.kyle.healthcare.controller_data;

import android.util.Log;

import com.kyle.healthcare.UIInterface;

public class Controller {

    private DataDealInterface dataDealInterface;
    private UIInterface UIInterface;
    private String currentString;


    public Controller(UIInterface UIInterface){
        this.UIInterface = UIInterface;
        this.dataDealInterface = new DataManger();
    }

    //get blueTooth's data
    public void postBlueToothData(String string){
        Log.i("BlueToothThread",string);
        this.currentString = string;
        this.dataDealInterface.addBlueToothData(string);
        updateFragment();
    }

    /*
       Driving record display
     */

    //get longitude and latitude
    public  void postXY(float X,float Y){
        this.dataDealInterface.addDrivingData(X,Y);
        updateFragment();
    }

    // update the fragment which is visible
    private void updateFragment(){
        switch (this.UIInterface.getVisibleFragmentAddress()){
            case FragmentAddressBook.DRIVING:
                this.UIInterface.updateDrivingFragment(this.dataDealInterface.getLatestDrivingInformation());
                Log.i("Controller","update Driving");
                break;
            case FragmentAddressBook.HEALTH:
                Log.i("Controller","update Health");
                this.UIInterface.updateHealthFragment(dataDealInterface.getHeartRate(),dataDealInterface.getFatigueRate());
                break;
        }
    }
}
