package com.danil.auto_birthday_vk.model.wrappers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.danil.auto_birthday_vk.db.DBHelper;

public class BaseDBWrapper {

    private DBHelper m_DbHelper = null;
    private String m_strTableName = "";

    public BaseDBWrapper(Context context, String strTableName){
        m_DbHelper = new DBHelper(context);
        m_strTableName = strTableName;
    }

    protected SQLiteDatabase getReadable(){
        return m_DbHelper.getReadableDatabase();
    }

    protected SQLiteDatabase getWritable(){
        return m_DbHelper.getWritableDatabase();
    }

    protected String getTableName(){
        return m_strTableName;
    }

}