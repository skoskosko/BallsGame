package com.example.skosko.ballsgame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Skosko on 23.4.2017.
 */

public class MyBroadCastReviever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
        }
    }
}