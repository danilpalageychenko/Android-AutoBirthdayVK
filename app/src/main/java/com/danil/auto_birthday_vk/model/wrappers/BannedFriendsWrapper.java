package com.danil.auto_birthday_vk.model.wrappers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.danil.auto_birthday_vk.UserInfo;
import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;

import java.util.ArrayList;

public class BannedFriendsWrapper extends BaseDBWrapper{

    public BannedFriendsWrapper(Context context) {
        super(context, DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY);
    }

    public ArrayList<UserInfo> getAll(){
        ArrayList<UserInfo> arrData = new ArrayList<>();
        SQLiteDatabase db = getReadable();
        Cursor cursor = db.query(getTableName(), null, null, null, null, null, DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID);
        if (cursor!=null){
            try{
                if (cursor.moveToFirst()){
                    do{
                        UserInfo userInfo = new UserInfo(cursor);
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

    public UserInfo getItemById(long nId){
        UserInfo userInfo = null;
        SQLiteDatabase db = getReadable();

        String strSelection = DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID + "=?";
        String[] arrSelectionArgs = {Long.toString(nId)};

        Cursor cursor = db.query(getTableName(), null,
                strSelection, arrSelectionArgs, null, null, null);
        if (cursor!=null){
            try{
                if (cursor.moveToFirst()){
                    userInfo = new UserInfo(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return userInfo;
    }

    public void deleteItem(UserInfo item){
        SQLiteDatabase db = getWritable();
        String strSelection = DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID + "=?";
        String[] arrSelectionArgs = {Long.toString(item.getId())};
        db.delete(getTableName(),strSelection,arrSelectionArgs);
        db.close();
    }
}
