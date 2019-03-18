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

    public final static int frag_id_homepage = 1;
    public final static int frag_id_health = 2;
    public final static int frag_id_driving = 3;
    public final static int frag_id_center = 4;
    public final static int frag_id_heart_rate = 5;
    public final static int frag_id_fatigue_rate = 6;
    public final static int frag_id_driving_habit = 7;
    public final static int frag_id_history_log = 8;
    public final static int frag_id_settings = 9;

    public static FragmentAddressBook fragmentAddressBook = new FragmentAddressBook();
   private FragmentAddressBook(){
        this.fragmentVisibleRecord = new boolean[15];
        this.fragmentVisibleRecord[0] = true;
        for (int i = 1; i <this.fragmentVisibleRecord.length ; i++) {
            this.fragmentVisibleRecord[i] = false;
        }
        this.currentVisibleFragment = 10;
    }

    public boolean isVisible(int address){
        return fragmentVisibleRecord[address];
    }

    public void setVisible(int address){
        this.fragmentVisibleRecord[address] = true;
        this.fragmentVisibleRecord[this.currentVisibleFragment] = false;
        this.currentVisibleFragment = address;
        Log.i("Address",String.valueOf(address));
    }

    public int getCurrentVisibleFragment(){
        Log.i("Address","crurrent"+String.valueOf(currentVisibleFragment));
        return this.currentVisibleFragment;
   }

}
