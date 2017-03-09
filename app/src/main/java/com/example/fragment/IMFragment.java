package com.example.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.adapter.WallAdapter;
import com.example.entry.Wall;
import com.example.family.R;
import com.example.tools.RunTime;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 */
public class IMFragment extends Fragment {
    public static final String TAG = "IMFragment";
    BmobIMConversation conversation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        x.view().inject(this,view);
        connectIM();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
private  void connectIM(){
    BmobIM.connect(RunTime.user.getObjectId(), new ConnectListener() {
        @Override
        public void done(String uid, BmobException e) {

            if (e == null) {
                Log.i(TAG,"connect success uid:"+uid);
                connectToInfo();
            } else {
                Log.e(TAG,e.getErrorCode() + "/" + e.getMessage());
            }
        }
    });
    //调用setOnConnectStatusChangeListener方法即可监听到当前长链接的连接状态
//    BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
//        @Override
//        public void onChange(ConnectionStatus status) {
//            Log.i(TAG,"" + status.getMsg());
//        }
//    });

    //在聊天页面的onCreate方法中，通过如下方法创建新的会话实例,这个obtain方法才是真正创建一个管理消息发送的会话
//    conversation=BmobIMConversation.obtain(BmobIMClient.getInstance(),(BmobIMConversation)getBundle().getSerializable("c"));

}

    private void connectToInfo(){
        BmobIMUserInfo info = new BmobIMUserInfo();
        info.setUserId("0b246ceb93");
        info.setName("123");
        info.setAvatar("12");
        //启动一个会话，设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        conversation = BmobIM.getInstance().startPrivateConversation(info,false,null);
        send();
        /*
        //如果需要更新用户资料，开发者只需要传新的info进去就可以了
        conversation =  BmobIM.getInstance().startPrivateConversation( info, new ConversationListener() {
            @Override
            public void done(BmobIMConversation c, BmobException e) {
                if(e==null){
                    //在此跳转到聊天页面
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("c", c);
                    Log.i(TAG,"call  send()");

                    //startActivity(ChatActivity.class, bundle, false);
                }else{
                    Log.i(TAG,e.getMessage()+"("+e.getErrorCode()+")");
                }
            }
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        send();
        */
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BmobIM.getInstance().disConnect();

    }

    @Override
    public void onStop() {
        super.onStop();
       // BmobIM.getInstance().disConnect();
    }
    private  void send(){
        BmobIMTextMessage msg =new BmobIMTextMessage();
        msg.setContent("123");
    //可随意设置额外信息
        Map<String,Object> map =new HashMap<>();
        map.put("level", "1");
        msg.setExtraMap(map);
        if(conversation!=null) {
            conversation.sendMessage(msg, new MessageSendListener() {
                @Override
                public void onStart(BmobIMMessage msg) {
                    super.onStart(msg);
                    Log.i(TAG,"onStart:msg:"+msg);
                    //   scrollToBottom();
                    //  adapter.addMessage(msg);
                    //   adapter.notifyDataSetChanged();
                }

                @Override
                public void done(BmobIMMessage msg, BmobException e) {
                    //  scrollToBottom();
                    //  adapter.notifyDataSetChanged();
                    //  edit_msg.setText("");
                if (e != null) {
                    Log.i(TAG,"done:e.getMessage:"+e.getMessage());
                }else{
                    Log.i(TAG,"conversation=null:");
                }
                }
            });
        }else{
            Log.i(TAG,"done:msg:"+msg);
        }
    }
}
