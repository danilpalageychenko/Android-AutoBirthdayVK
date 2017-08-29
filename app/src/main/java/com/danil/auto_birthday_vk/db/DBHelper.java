package com.danil.auto_birthday_vk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;

/**
 * Класс реализущий доступ к базе данных
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * Конструктор класса. Для открытия БД используется Context.
     * @param context
     */
    public DBHelper(Context context) {
        /*В качестве параметров в конструктор родителя дополнительно передаем
        * имя базы данных (их может быть не одна) и ее (базы данных) версию.*/
        super(context, "db_example", null, 1);
    }

    /**
     * Функция вызываемая системой, в случае обращения к базе данных
     * которой не существует.
     * В данной функции происходит построение базы данных.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBConstants.TABLE_FRIENDS +
                " (" + DBConstants.TABLE_FRIENDS_FIELD_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DBConstants.TABLE_FRIENDS_FIELD_NAME + " TEXT NOT NULL, " +
                DBConstants.TABLE_FRIENDS_FIELD_SNAME + " TEXT NOT NULL, " +
                DBConstants.TABLE_FRIENDS_FIELD_BDATE + " TEXT NOT NULL); ");

        db.execSQL("CREATE TABLE " + DBConstants.TABLE_CONGRATULATIONS +
                " (" + DBConstants.TABLE_CONGRATULATIONS_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DBConstants.TABLE_CONGRATULATIONS_FIELD_CONGRATULATIONS + " TEXT NOT NULL); ");

        db.execSQL("CREATE TABLE " + DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY + " ("
                + DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID + " TEXT NOT NULL, "
                + DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_NAME + " TEXT NOT NULL, "
                + DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_PHOTO + " BLOB, " +
                DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_YEAR + " TEXT NOT NULL); ");

    }

    /**
     * Функция вызываемая системой, в случае обращения к базе данных
     * которая устарела.
     * В данной функции происходит обновление базы данных до текущей версии.
     * @param db существующая БД
     * @param oldVersion версия БД которая существует
     * @param currentVersion запращиваемая версия БД
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {

    }
}