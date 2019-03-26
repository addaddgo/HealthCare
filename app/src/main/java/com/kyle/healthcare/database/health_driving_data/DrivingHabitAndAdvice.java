package com.kyle.healthcare.database.health_driving_data;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;

public class DrivingHabitAndAdvice extends DataSupport {



    private ArrayList<String> habit = new ArrayList<>();
    private ArrayList<String> advice = new ArrayList<>();


    public ArrayList<String> getAdvice() {
        return advice;
    }

    public ArrayList<String> getHabit() {
        return habit;
    }
}
