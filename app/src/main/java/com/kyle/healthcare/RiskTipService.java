package com.kyle.healthcare;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;

public class RiskTipService extends Service {

    private final static int HEART_RATE_TOO_HIGH = 1;
    private final static int FATIGUE_RATE_TOO_HIGH = 2;
    private final static int DRIVING_RECORD_UNUSUAL = 4;

    //Tip thread
    private HandlerThread handlerThread;
    private Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        this.handlerThread = new HandlerThread("RiskTipService");
        this.handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
            }
        };
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new TipBinder();
    }


    //getHealthInformation
    private class TipBinder extends Binder{
        public void postDangerInformation(int... dangerousInformation){
            Message message = handler.obtainMessage();
            message.what = 1;
            Bundle bundle = new Bundle();
            bundle.putIntArray("information",dangerousInformation);
            handler.sendMessage(message);
        }
    }
}
