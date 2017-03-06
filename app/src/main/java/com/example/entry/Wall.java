package com.example.entry;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by LiangCha on 2017/2/26.
 */
public class Wall extends BmobObject{
    private String userId;
    private String userName;
    private List<String> url;
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

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
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
                ", url=" + url +
                ", content='" + content + '\'' +
                ", commentNum=" + commentNum +
                ", dianzanNum=" + dianzanNum +
                '}';
    }
}
