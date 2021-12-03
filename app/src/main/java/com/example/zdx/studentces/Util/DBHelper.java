package com.example.zdx.studentces.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;
    //创建管理员表
    public static final String CREATE_MANAGER = "create table " +
            "admin(id integer primary key , name text,password text)";
    //创建用户表
    public static final String CREATE_TEACHER = "create table " +
            "user(id integer primary key," +
            "name text,password text)";
    //创建茶叶表
    public static final String CREATE_STUDENT = "create table " +
            "tea(teanum text primary key," +
            "teaname text,teastock integer,teaprice text,teastate integer default(0))";


    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MANAGER);
        db.execSQL(CREATE_TEACHER);
        db.execSQL(CREATE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }


    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context, "TeaSystem.db", null, 2);
        }
        return instance;
    }

}
