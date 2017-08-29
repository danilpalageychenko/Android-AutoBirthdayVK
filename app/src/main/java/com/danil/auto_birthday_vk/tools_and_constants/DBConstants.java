package com.danil.auto_birthday_vk.tools_and_constants;

/**Класс для хранения констант для базы данных.
 * Содержит константы, для работы с БД.
 */

public class DBConstants {

    public static final String TABLE_CONGRATULATIONS = "Congratulations";
    public static final String TABLE_FRIENDS = "Friends";
    public static final String TABLE_FRIENDS_WHO_HAD_BIRTHDAY = "Birthdays";

    public static final String TABLE_CONGRATULATIONS_FIELD_ID = "_id";
    public static final String TABLE_CONGRATULATIONS_FIELD_CONGRATULATIONS = "_congratulations";


    public static final String TABLE_FRIENDS_FIELD_USER_ID = "_id";
    public static final String TABLE_FRIENDS_FIELD_NAME = "_name";
    public static final String TABLE_FRIENDS_FIELD_SNAME = "_sname";
    public static final String TABLE_FRIENDS_FIELD_BDATE = "_bdate";


    public static final String TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID = "_userId";
    public static final String TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_YEAR = "_year";
    public static final String TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_NAME = "_name";
    public static final String TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_PHOTO = "_photo";

}
