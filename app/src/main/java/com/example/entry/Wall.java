package com.example.entry;

import cn.bmob.v3.BmobObject;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class Wall extends BmobObject{
    private String userId;
    private String userName;
    private String url;
    private  String tiem;
    private  String content;
    private int commentNum;
    private int dianzanNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTiem() {
        return tiem;
    }

    public void setTiem(String tiem) {
        this.tiem = tiem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getDianzanNum() {
        return dianzanNum;
    }

    public void setDianzanNum(int dianzanNum) {
        this.dianzanNum = dianzanNum;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", url='" + url + '\'' +
                ", tiem='" + tiem + '\'' +
                ", content='" + content + '\'' +
                ", commentNum=" + commentNum +
                ", dianzanNum=" + dianzanNum +
                '}';
    }
}
