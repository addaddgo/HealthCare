package com.kyle.healthcare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kyle.healthcare.base_package.BaseActivity;
import com.kyle.healthcare.bluetooth.BluetoothChatService;
import com.kyle.healthcare.bluetooth.Constants;
import com.kyle.healthcare.controller_data.Controller;
import com.kyle.healthcare.controller_data.DrivingData;
import com.kyle.healthcare.controller_data.FragmentAddressBook;
import com.kyle.healthcare.fragment_package.CenterFragment;
import com.kyle.healthcare.fragment_package.DrivingFragment;
import com.kyle.healthcare.fragment_package.HealthFragment;
import com.kyle.healthcare.fragment_package.HomepageFragment;
import com.kyle.healthcare.risk_tip.RiskTipActivityActivity;
import com.kyle.healthcare.risk_tip.RiskTipService;

public class MainActivity extends BaseActivity implements UIInterface {

    private static final int REQUEST_ENABLE_BT = 3;

    private String mConnectedDeviceName = null;

    BluetoothChatService mChatService;
    // TODO: 2019/3/11 1)四个Fragment
    // TODO: 2019/3/11 2)异常判断及异常Fragment（响铃、倒计时、短信权限）
    // TODO: 2019/3/11 3)健康数据数据库
    // TODO: 2019/3/11 4)本地偏好设置(是否响铃，是否短信提示)
    // TODO: 2019/3/11 5)注册界面更新

    private HomepageFragment homepageFragment;
    private HealthFragment healthFragment;
    private DrivingFragment drivingFragment;
    private CenterFragment centerFragment;
    private TextView toolbarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_homepage:
                    toolbarTitle.setText(R.string.title_homepage);
                    replaceFragment(homepageFragment);
                    return true;

                case R.id.navigation_health:
                    toolbarTitle.setText(R.string.title_health);
                    replaceFragment(healthFragment);
                    fragmentAddressBook.setVisible(FragmentAddressBook.HEALTH);
                    return true;

                case R.id.navigation_driving:
                    toolbarTitle.setText("行驶记录");
                    replaceFragment(drivingFragment);
                    fragmentAddressBook.setVisible(FragmentAddressBook.DRIVING);
                    return true;

                case R.id.navigation_center:
                    toolbarTitle.setText(R.string.title_center);
                    replaceFragment(centerFragment);
                    return true;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_frag, fragment);
        transaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homepageFragment = new HomepageFragment();
        healthFragment = new HealthFragment();
        drivingFragment = new DrivingFragment();
        centerFragment = new CenterFragment();
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.title_homepage);
        replaceFragment(homepageFragment);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "蓝牙设备不可用", Toast.LENGTH_LONG).show();
            finish();
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            mChatService = new BluetoothChatService(this, mHandler);
        }

        //deal with data
        this.fragmentAddressBook = FragmentAddressBook.fragmentAddressBook;
        this.controller = new Controller(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    // TODO: 2019/3/14 接受数据
                    //Toast.makeText(MainActivity.this, readMessage, Toast.LENGTH_SHORT).show();
                            Log.i("BlueToothThread","getMessage");
                            controller.postXY(0,0);
                            Log.i("BlueToothThread","getHealthMessage");
                            controller.postBlueToothData(readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    Toast.makeText(MainActivity.this, "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                    case FragmentAddressBook.HEALTH:
                        controller.postXY(0,0);
                        Log.i("BlueToothThread","getHealthMessage");
                        controller.postBlueToothData("T"+ (int)(Math.random()*20)+"," + "T"+ (int)(Math.random()*20) + ","+"T"+ (int)(Math.random()*20)+","+"T"+ (int)(Math.random()*20)+",");
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    mChatService = new BluetoothChatService(this, mHandler);
                } else {
                    Toast.makeText(this, "蓝牙未成功开启，应用退出",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    /*
       update UI
     */
    private Controller controller;

    // manager visibility of fragment
    private FragmentAddressBook fragmentAddressBook;


    @Override
    public int getVisibleFragmentAddress() {
        return this.fragmentAddressBook.getCurrentVisibleFragment();
    }

    @Override
    public void replaceFragmentInFragment(Fragment fragment) {
        replaceFragment(fragment);
    }

    @Override
    public void updateDrivingFragment(DrivingData drivingData) {
        this.drivingFragment.update(String.valueOf(drivingData.totalTime),String.valueOf(drivingData.totalDistance),String.valueOf(drivingData.averageSpeech));
    }

    @Override
    public void updateHealthFragment(int heartRate,int fatigue) {
        this.healthFragment.addNewData(heartRate,fatigue);
    }

    @Override
    public void stopHealthFragmentUpdate() {
        this.healthFragment.onPause();
    }

    //BlueToothThread is sending message in the disguise of bluetooth

    //start test;
    public void startTest(){
        new BlueToothThread().start();
    }

    class BlueToothThread extends Thread{
        @Override
        public void run() {
            super.run();
            Log.i("BlueToothThread","start");
            try{
                for (int i = 0; i < 100; i++) {
                    Message message = new Message();
                    message.what = FragmentAddressBook.HEALTH;
                    mHandler.sendMessage(message);
                    Thread.sleep(200);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Log.d("BlueToothThread", "end");
        }
    }

}
