package com.kyle.healthcare;

import android.app.Service;
import android.support.v4.app.Fragment;

import com.kyle.healthcare.controller_data.DrivingData;

public interface UIInterface {
    void replaceFragmentInFragment(Fragment fragment);
    void updateDrivingFragment(DrivingData drivingData);
    int getVisibleFragmentAddress();
    void updateHealthFragment(int heartRate,int fatigue);
    void stopHealthFragmentUpdate();
}
