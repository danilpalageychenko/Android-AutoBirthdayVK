package com.danil.auto_birthday_vk.model.wrappers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.danil.auto_birthday_vk.Congratulations;
import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;

import java.util.ArrayList;

public class CongratulationsRecordWrapper extends BaseDBWrapper{

    public CongratulationsRecordWrapper(Context context) {
        super(context, DBConstants.TABLE_CONGRATULATIONS);
    }

    public ArrayList<Congratulations> getAll(){
        ArrayList<Congratulations> arrData = new ArrayList<>();
        SQLiteDatabase db = getReadable();
        Cursor cursor = db.query(getTableName(), null, null, null, null, null, DBConstants.TABLE_CONGRATULATIONS_FIELD_ID+" DESC");
        if (cursor!=null){
            try{
                if (cursor.moveToFirst()){
                    do{
                        Congratulations userInfo = new Congratulations(cursor);
                        arrData.add(userInfo);

                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return arrData;
    }

    public Congratulations getItemById(long nId){
        Congratulations userInfo = null;
        SQLiteDatabase db = getReadable();

        String strSelection = DBConstants.TABLE_CONGRATULATIONS_FIELD_ID + "=?";
        String[] arrSelectionArgs = {Long.toString(nId)};

        Cursor cursor = db.query(getTableName(), null,
                strSelection, arrSelectionArgs, null, null, null);
        if (cursor!=null){
            try{
                if (cursor.moveToFirst()){
                    userInfo = new Congratulations(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return userInfo;
    }

    public void insertItem(Congratulations item){
        SQLiteDatabase db = getWritable();
        ContentValues values = item.getContentValues();
        db.insert(getTableName(),null,values);
        db.close();
    }

    public void updateItem(Congratulations item){
        SQLiteDatabase db = getWritable();
        ContentValues values = item.getContentValues();
        String strSelection = DBConstants.TABLE_CONGRATULATIONS_FIELD_ID + "=?";
        String[] arrSelectionArgs = {Long.toString(item.getId())};
        db.update(getTableName(),values,strSelection, arrSelectionArgs);
        db.close();
    }

    public void deleteItem(Congratulations item){
        SQLiteDatabase db = getWritable();
        String strSelection = DBConstants.TABLE_CONGRATULATIONS_FIELD_ID + "=?";
        String[] arrSelectionArgs = {Long.toString(item.getId())};
        db.delete(getTableName(),strSelection,arrSelectionArgs);
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritable();
        db.delete(getTableName(),null,null);
        db.close();
    }

}


