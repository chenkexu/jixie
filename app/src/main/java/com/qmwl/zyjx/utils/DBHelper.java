package com.qmwl.zyjx.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static int DBVERSION = 1;
    private static String NAME = "zyjx.db";

    public DBHelper(Context paramContext) {
        super(paramContext, NAME, null, DBVERSION);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("create table shoppinglist (id integer primary key,productid varchar(50),trademarkimage varchar(50),name varchar(50),producticon varchar(50),number integer,model varchar(50))");
        paramSQLiteDatabase.execSQL("create table hx_userdata (id integer primary key,user_id varchar(50),user_name varchar(50),user_image varchar(70))");

        Log.i("TAA", "创建数据库");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {

    }
}