package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.family.R;
import com.example.tools.Tools;
import com.king.photo.util.BitmapCache;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class GridAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public GridAdapter( List<String> list,Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        if(convertView==null){
             imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            int i = Tools.dip2px(context,90);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(i,i));
        }else{
            imageView = (ImageView)convertView;
        }
        Log.i("12","tea>>>p"+position);
        ImageLoader.getInstance().displayImage(list.get(position),imageView);
//        imageView.setImageResource(R.drawable.touxiang);
        return imageView;
    }
}
