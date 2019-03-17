package com.kyle.healthcare.controller_data;

import android.icu.text.LocaleDisplayNames;
import android.util.Log;

/*
 * The public final static integer stand for the Fragment
 * usage:1 send the message to right address
 *    message.what = fragment's address
 */
public class FragmentAddressBook {

    private boolean[] fragmentVisibleRecord;
    private int currentVisibleFragment;
    //address
    public final static int HOME_PAGER = 10;
    public final static int HEALTH = 11;
    public final static int HEART_RATE = 12;
    public final static int FATIGUE = 13;
    public final static int DRIVING = 14;
    public final static int CENTER = 15;

    public static FragmentAddressBook fragmentAddressBook = new FragmentAddressBook();
   private FragmentAddressBook(){
        this.fragmentVisibleRecord = new boolean[6];
        this.fragmentVisibleRecord[0] = true;
        for (int i = 1; i <this.fragmentVisibleRecord.length ; i++) {
            this.fragmentVisibleRecord[i] = false;
        }
        this.currentVisibleFragment = 10;
    }

    public boolean isVisible(int address){
        return fragmentVisibleRecord[address - 10];
    }

    public void setVisible(int address){
        this.fragmentVisibleRecord[address - 10] = true;
        this.fragmentVisibleRecord[this.currentVisibleFragment - 10] = false;
        this.currentVisibleFragment = address;
        Log.i("Address",String.valueOf(address));
    }

    public int getCurrentVisibleFragment(){
        Log.i("Address","crurrent"+String.valueOf(currentVisibleFragment));
        return this.currentVisibleFragment;
   }

}
