package com.kyle.healthcare;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.kyle.healthcare.controller_data.DrivingData;

public interface UIInterface {
    void replaceFragmentInFragment(int fragmentID);
    void updateDrivingFragment(DrivingData drivingData);
    int getVisibleFragmentAddress();
    void updateHealthFragment(int heartRate,int fatigue);
    void stopHealthFragmentUpdate();
    void setTitle(int title);
    void setNavigationVisibility(int visibility);
    ActionBar getBar();
}
