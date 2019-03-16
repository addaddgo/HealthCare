package com.kyle.healthcare.controller_data;
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

   public FragmentAddressBook(){
        this.fragmentVisibleRecord = new boolean[6];
        this.fragmentVisibleRecord[0] = true;
        for (int i = 1; i <this.fragmentVisibleRecord.length ; i++) {
            this.fragmentVisibleRecord[i] = false;
        }
        this.currentVisibleFragment = 0;
    }

    public boolean isVisible(int address){
        return fragmentVisibleRecord[address - 10];
    }

    public void setVisible(int address){
        this.fragmentVisibleRecord[address - 10] = true;
        this.fragmentVisibleRecord[this.currentVisibleFragment] = false;
        this.currentVisibleFragment = address;
    }

    public int getCurrentVisibleFragment(){
        return this.currentVisibleFragment;
    }

}
