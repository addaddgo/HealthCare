package com.kyle.healthcare.base_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

public class BaseActivity extends AppCompatActivity {
    ForceOfflineReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    public void sendMsg(String  msgContent, String number) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));
        intent.putExtra("msg_content", msgContent);
        startActivity(intent);

        //method2
        //SmsManager.getDefault().sendTextMessage(number, null, msgContent, null, null);
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.kyle.healthcare.LOG_OUT");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putBoolean("remember_password", false);
            editor.apply();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("退出登录");
            builder.setMessage("确定是否退出？");
            builder.setCancelable(true);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();
                    Intent intent = new Intent(context, LogInActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
}
