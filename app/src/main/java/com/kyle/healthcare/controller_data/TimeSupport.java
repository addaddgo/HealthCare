package com.kyle.healthcare.controller_data;


import android.icu.util.Calendar;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/*
 * 将系统给定的时间做出处理
 * 本类只有一个实例。不能再次创建。在app开始的时候就会出现
 *
 * 1：给出当前的年月日。
 * 2:根据某一年，给出月份的数组（比如说当前是 2019.3.4，选择2019年，给出的）。
 * 3:根据某一个月份和年份，给出天数的数组
 * 4:如果存在A activity 和 b activity 需要用到不同的时间，可以创建一个子的时间类来规定属于他自己的时间。
 * 这个属于他自己的时间是绑定于对应的活动。就算一个活动关闭了，属于它的时间依然存在。
 */
public class TimeSupport {

    //全局唯一
    public static TimeSupport timeSupport = new TimeSupport();

    //主时间
    private PersonalTime mainTime;

    //日历
    private Calendar calendar;

    //年份
    private int years[];

    //各个分区的时间
    private ArrayList<PersonalTime> othersTime;

    private TimeSupport(){
        this.mainTime = new PersonalTime();
        this.mainTime.position = 1;
        this.calendar = Calendar.getInstance();
        this.othersTime = new ArrayList<PersonalTime>();
        refreshDate();
        this.years = new int[100];
        for (int i = 0; i < years.length; i++) {
            this.years[i] = 1920 + i;
        }
    }

    //根据时间设定日期
    private void refreshDate(){
        long time = System.currentTimeMillis();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        this.mainTime.currentTime = calendar.get(Calendar.MILLISECONDS_IN_DAY);
        compressDate(year,month,day);
    }

    //将年月日打包在PersonalTime的date中 year保存在前十六位，month 保存在中间8位 day保存在后八位
    private void compressDate(int year,int month,int day){
            this.mainTime.data+=year;
            this.mainTime.data = this.mainTime.data << 8;
            this.mainTime.data+=month;
            this.mainTime.data = this.mainTime.data << 8;
            this.mainTime.data+=day;
    }

    //获取当前的年
    public int getYear(){
        return this.mainTime.data >> 16;
    }
    //获取当前的月
    public int getMonth(){
        return (this.mainTime.data & 0xffff) >> 8;
    }
    //当前的日期
    public int getDay(){
        return this.mainTime.data & 0xff;
    }

    public int[] getYears() {
        return years;
    }

    //获得对应的年下的月份数组
    public int[] getMonthInYearByCurrentTime(int currentYear,int currentMonth){
        int theYear = getYear();
        if(currentYear < theYear){
            return new int[]{1,2,3,4,5,6,7,8,9,10,11,12};
        }else if(currentYear == theYear){
            if(currentMonth > 12){
                return null;
            }
            int[] month = new int[currentMonth];
            for (int i = 0; i < currentMonth; i++) {
                month[i] = i+1;
            }
            return month;
        }
        return null;
    }
    //获得对应的年和月下的天数数组
    public int[] getDayInMonthAndYearByCurrentTime(int currentYear,int currentMonth,int currentDay){
        int theYear = getYear();
        int theMonth = getMonth();
        if(currentYear <= theYear && currentYear >= 0 && currentMonth < theMonth){//这个月之前
            if(currentMonth > 12 || currentMonth < 1){
                return null;
            }else if(currentMonth == 2){//二月
                if((currentYear % 4 == 0)&&(currentYear % 400 ==0 || currentYear % 100 != 0)){//如果是闰年
                    int days[] = new int[29];
                    for (int i = 0; i < 29; i++) {
                        days[i] = i + 1;
                     }
                     return days;
                }else{
                    int days[] = new int[28];
                    for (int i = 0; i < 28; i++) {
                        days[i] = i + 1;
                    }
                    return days;
                }
            }else{
                int day31[] = new int[]{1,3,5,7,8,10,12};
                if(findPositionBinarySearch(day31,currentMonth) == -1){
                    int[] days = new int[30];
                    for(int i =0;i < 30;i++){
                        days[i] = i+1;
                    }
                    return days;
                }else {
                    int[] days = new int[31];
                    for (int i = 0; i <31 ; i++) {
                        days[i] = i +1;
                    }
                    return days;
                }
            }
        }else if(currentMonth == theMonth && currentYear == theYear){
            int limitLength = getDayInMonthAndYear(currentYear,currentMonth).length;
            if(currentDay <= limitLength && currentDay >0){
                int days[] = new int[currentDay];
                for (int i = 0; i < currentDay; i++) {
                    days[i] = i + 1;
                }
                return days;
            }else{
                return null;
            }
        }
        return null;
    }

    //无当前时间限制给出当前对应的天数
    public int[] getDayInMonthAndYear(int currentYear,int currentMonth){
        if(currentYear >= 0){
            if(currentMonth > 12 || currentMonth < 1){
                return null;
            }else if(currentMonth == 2){//二月
                if((currentYear % 4 == 0)&&(currentYear % 400 ==0 || currentYear % 100 != 0)){//如果是闰年
                    int days[] = new int[29];
                    for (int i = 0; i < 29; i++) {
                        days[i] = i + 1;
                    }
                    return days;
                }else{
                    int days[] = new int[28];
                    for (int i = 0; i < 28; i++) {
                        days[i] = i + 1;
                    }
                    return days;
                }

            }else{
                int day31[] = new int[]{1,3,5,7,8,10,12};
                if(findPositionBinarySearch(day31,currentMonth) == -1){
                    int[] days = new int[30];
                    for(int i =0;i < 30;i++){
                        days[i] = i+1;
                    }
                    return days;
                }else {
                    int[] days = new int[31];
                    for (int i = 0; i <31 ; i++) {
                        days[i] = i +1;
                    }
                    return days;
                }
            }
        }
        return null;
    }

    //保存时间信息
    private class PersonalTime extends DataSupport {

        //分区标识
        private int position;
        //当前日期
        private int data;
        //当前时间(秒),从早上到晚上的秒数
        private int currentTime;
        PersonalTime(){
            this.position = 0;
            this.data = 0;
            this.currentTime = 0;
        }
    }

    public static int findPositionBinarySearch(int[] data,int number){
        int starPointer = 0;
        int endPointer = data.length - 1;
        return binarySearch(starPointer,endPointer,data,number);
    }
    //整型二分查找：在一定的区间内
    public static int binarySearch(int starPointer,int endPointer,int[] data,int number){
        if(endPointer - starPointer > 1){
            int center = (int)(endPointer + starPointer) / 2;
            if(data[center] > number){
                return binarySearch(starPointer,center,data,number);
            }else{
                if(data[center] == number){
                    return  center;
                }else{
                    return binarySearch(center,endPointer,data,number);
                }
            }
        }else{
            if(data[starPointer] == number){
                return starPointer;
            }else if(data[endPointer] == number){
                return endPointer;
            }else{
                return -1;
            }
        }
    }
}
