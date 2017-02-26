package com.example.tools;

import android.content.Context;
import android.widget.Toast;

import com.example.family.R;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class Tools {
    public static void showToast(Context c,String content){
        Toast.makeText(c, content,Toast.LENGTH_SHORT).show();
    }
}
