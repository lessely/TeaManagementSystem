package com.example.zdx.studentces.Util;

/**
 * 保存用户信息的实体类
 */
public class user {
    private String username;
    private String userid;
    private String userpasswd;

    public user(String username, String userid, String userpasswd) {
        this.username = username;
        this.userid = userid;
        this.userpasswd = userpasswd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpasswd() {
        return userpasswd;
    }

    public void setUserpasswd(String userpasswd) {
        this.userpasswd = userpasswd;
    }
}

