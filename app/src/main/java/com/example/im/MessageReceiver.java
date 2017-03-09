package com.example.im;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.bmob.newim.event.MessageEvent;

/**
 * Created by LiangCha on 2017/3/8.
 */
public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent != null) {
            final MessageEvent event = (MessageEvent) intent.getSerializableExtra("event");
            //开发者可以在这里发应用通知
            Log.i("IM","onReceive event"+event.toString());
        }
    }
}