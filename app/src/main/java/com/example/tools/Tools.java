package com.example.tools;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import com.example.family.R;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class Tools {
    public static void showToast(Context c,String content){
        Toast.makeText(c, content,Toast.LENGTH_SHORT).show();
    }
    /**
     * 修改textview的字体颜色
     * @param color
     * @param source
     * @return
     */
    public static Spanned textColorSpaned(String color, String source){
        String test =  "<font color= '"+color+"'>"+source+"</font>";
        return Html.fromHtml(test);
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
