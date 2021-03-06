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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 */
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";
    @ViewInject(value = R.id.main_lv)
    private ListView lv;
    private List<Wall> list;
    private WallAdapter adapter;
    private Handler handler;
    private static final int INIT_WALL = 1;
    private static final int FLASH_WALL = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHandler();
        initData();
    }

    private void initData() {
        BmobQuery<Wall> query = new BmobQuery<Wall>();
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<Wall>() {
            @Override
            public void done(List<Wall> object, BmobException e) {
                if(e==null){
                   // toast("查询成功：共"+object.size()+"条数据。");
                   list = object;
                    handler.sendEmptyMessage(INIT_WALL);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void initHandler() {
        handler = new Handler(){


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //初始化图片墙
                if(msg.what==INIT_WALL){
                    Log.i(TAG,"tea>>>main list="+list);
                    adapter = new WallAdapter(list,getActivity());
                    lv.setAdapter(adapter);
                }
            }
        };
    }

    private void inittestData() {
        list = new ArrayList<Wall>();
        Random r = new Random();
        for(int i  = 0;i<30;i++){
            Wall w = new Wall();
            w.setUserName("王大妈");
            w.setContent("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
            w.setCommentNum(20);
            w.setDianzanNum(21);
            int size = r.nextInt(9);
            StringBuffer sb = new StringBuffer();
            for (int j = 0;j<size;j++){
                sb.append("23");
                sb.append("@");
            }
            if(sb.length()>0){
                sb.delete(sb.length()-1,sb.length());
              //  w.setUrl(sb.toString());
            }
            list.add(w);
        }
        handler.sendEmptyMessage(INIT_WALL);
    }
}
