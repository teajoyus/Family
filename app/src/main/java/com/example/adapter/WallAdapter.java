package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.entry.Wall;
import com.example.family.R;
import com.example.tools.Tools;
import com.example.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class WallAdapter extends BaseAdapter {
    private static final String TAG ="WallAdapter" ;
    private List<Wall> list;
    private Context context;

    public WallAdapter(List<Wall> list, Context context) {
        this.list = list;
        this.context = context;
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
        ViewHolder holder = null;
        if (convertView == null) {
          convertView = LayoutInflater.from(context).inflate(R.layout.main_lv_view,parent,false);
            holder = new ViewHolder();
            holder.comment = (TextView) convertView.findViewById(R.id.main_item_comment);
            holder.content  = (TextView) convertView.findViewById(R.id.main_item_content);
            holder.name  = (TextView) convertView.findViewById(R.id.main_item_name);
            holder.time  = (TextView) convertView.findViewById(R.id.main_item_time);
            holder.dianzan  = (TextView) convertView.findViewById(R.id.main_item_dianzan);
            holder.content  = (TextView) convertView.findViewById(R.id.main_item_content);
            holder.gridView  = (MyGridView) convertView.findViewById(R.id.main_item_grid);
            convertView.setTag(holder);
        }else{
            holder  = (ViewHolder) convertView.getTag();
        }
        holder.content.setText(list.get(position).getContent());
        holder.name.setText(list.get(position).getUserName());
        holder.time.setText(list.get(position).getCreatedAt());
      holder.comment.setText("评论 ");
        holder.comment.append(Tools.textColorSpaned("#4087bf",list.get(position).getCommentNum()+""));
        holder.dianzan.setText("点赞 ");
        holder.dianzan.append(Tools.textColorSpaned("#4087bf",list.get(position).getDianzanNum()+""));
        if(list.get(position).getUrl()!=null&&list.get(position).getUrl().size()>0) {

            int i = list.get(position).getUrl().size()/3;
            if(list.get(position).getUrl().size()%3!=0){
                i++;
            }
            ViewGroup.LayoutParams parems = holder.gridView.getLayoutParams();
            parems.height = i*Tools.dip2px(context,100);
            holder.gridView.setLayoutParams(parems);
            Log.i(TAG,"tea>>>parems.height："+parems.height);
            Log.i(TAG,"tea>>>"+list.get(position).getUrl());
            holder.gridView.setAdapter(new GridAdapter(list.get(position).getUrl(), context));
        }
        return convertView;
    }
    private static class ViewHolder{
        TextView name;
        TextView content;
        TextView time;
        TextView dianzan;
        TextView comment;
        MyGridView gridView;


    }
}
