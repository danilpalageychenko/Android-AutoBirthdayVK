package com.danil.auto_birthday_vk.tools_and_constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**Класс для хранения настроек приложения в стандартном хранилище данных.
 */

public class AppSettings {

    private static final String KEY_BOOLEAN_IS_FIRST_START = "KEY_BOOLEAN_IS_FIRST_START";

    private SharedPreferences m_SharedPreferences = null;

    // getDefaultSharedPreferences возвращает экземпляр класса SharedPreferences,
    // из которого можно получить соответствующую настройку с помощью ряда методов

    public AppSettings(Context context){
        m_SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //Приложение запушено впервые
    public boolean getIsFirstStart(){
        return m_SharedPreferences.getBoolean(KEY_BOOLEAN_IS_FIRST_START, true);
    }

    public void setIsFirstStart(boolean bIsFirstStart){
        SharedPreferences.Editor editor = m_SharedPreferences.edit();
        editor.putBoolean(KEY_BOOLEAN_IS_FIRST_START, bIsFirstStart);
        editor.commit();
    }
}
