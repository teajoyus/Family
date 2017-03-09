package com.example.entry;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class User extends BmobUser {
    private String relate;
    @Override
    public String getTableName() {
        return "User_test";
    }
    public String getRelate() {
        return relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    @Override
    public String toString() {
        return "User{" +"objectId"+super.getObjectId()+"username="+super.getUsername()+"}";
    }
}
