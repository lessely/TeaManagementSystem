package com.example.zdx.studentces.Util;

/**
 * 保存茶叶信息的实体类
 */
public class tea {
    private String teaname;
    private String teanum;
    private String teaprice;//价格
    private int teastock;
    private int teastate;

    public tea(String teaname, String teanum, String teaprice,int teastock) {
        this.teaname = teaname;
        this.teanum = teanum;
        this.teaprice = teaprice;
        this.teastock=teastock;
    }

    public String getTeaname() {
        return teaname;
    }

    public void setTeaname(String teaname) {
        this.teaname = teaname;
    }

    public String getTeanum() {
        return teanum;
    }

    public void setTeanum(String teanum) {
        this.teanum = teanum;
    }

    public String getTeaprice() {
        return teaprice;
    }

    public void setTeaprice(String teaprice) {
        this.teaprice = teaprice;
    }

    public String getTeastock() {
        return Integer.toString(teastock);
    }

    public void setTeastock(int teastock) {
        this.teastock = teastock;
    }

    public int getTeastate() {
        return teastate;
    }

    public void setTeastate(int teastate) {
        this.teastate = teastate;
    }







}

