package com.example.im;

import android.util.Log;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by LiangCha on 2017/3/8.
 */
public class MyMessageHandler extends BmobIMMessageHandler {
    public  MyMessageHandler(){
        Log.i("IM","created MyMessageHandler");
    }
    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用
        Log.i("IM","onMessageReceive");
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
        Log.i("IM","onOfflineReceive");
    }
}
