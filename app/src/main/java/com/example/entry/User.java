package com.example.entry;

import cn.bmob.v3.BmobObject;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class User extends BmobObject {
    private String count;
    private String password;
    private String relate;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRelate() {
        return relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }
}
