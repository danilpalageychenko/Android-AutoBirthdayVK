package com.danil.auto_birthday_vk;

import android.database.Cursor;

import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;

public class UserInfo {
    private int id;
    private String name;
    private String imageUrl;
    private boolean isOnline;
    private String bdate;



    public UserInfo(int id, String name, String imageUrl, boolean isOnline, String bdate) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isOnline = isOnline;
        this.bdate = bdate;

    }

    public UserInfo(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID));
        this.name = cursor.getString(cursor.getColumnIndex(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_NAME));
//        this.imageUrl = cursor.getString(cursor.getColumnIndex(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_PHOTO));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getBdate(){
        return bdate;
    }

    public void setBdate(String bdate){
        this.bdate = bdate;
    }

}
