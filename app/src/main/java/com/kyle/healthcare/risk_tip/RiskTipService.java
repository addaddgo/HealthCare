package com.kyle.healthcare.risk_tip;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.view.Window;

public class RiskTipService extends Service {


    //Tip thread
    private final  static int INTERVAL = 300000;
    private HandlerThread handlerThread;
    private Handler handler;
    private long lastTipTime;
    @Override
    public void onCreate() {
        super.onCreate();
        this.handlerThread = new HandlerThread("RiskTipService");
        this.handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                long currentTime = System.currentTimeMillis();
                if(currentTime - lastTipTime > INTERVAL){
                    if(callback != null){
                        callback.TipBefore();
                    }
                    lastTipTime = currentTime;
                    Intent intent = new Intent(getApplication(),RiskTipActivityActivity.class);
                    intent.putExtra("TIP",msg.arg1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new TipBinder();
    }

    private Callback callback;
    //getHealthInformation
    public class TipBinder extends Binder{
        public void postDangerInformation(int situation){
            Message message = handler.obtainMessage();
            message.what = 1;
            message.arg1 = situation;
            handler.sendMessage(message);
        }

        public void setCallback(Callback callback1){
            callback  = callback1;
        }
    }


    public interface Callback{
        void TipBefore();
    }
}
