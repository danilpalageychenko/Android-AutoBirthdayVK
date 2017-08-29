package com.danil.auto_birthday_vk;

import android.content.ContentValues;
import android.database.Cursor;

import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;

public class Congratulations {

    private long m_nId = -1;
    private String m_strCongratulations = "";


    public Congratulations(String m_strCongratulations) {
        this.m_nId = -1;
        this.m_strCongratulations = m_strCongratulations;

    }

    public Congratulations(Cursor cursor) {
        this.m_nId = cursor.getLong(cursor.getColumnIndex(DBConstants.TABLE_CONGRATULATIONS_FIELD_ID));
        this.m_strCongratulations = cursor.getString(cursor.getColumnIndex(DBConstants.TABLE_CONGRATULATIONS_FIELD_CONGRATULATIONS));
    }


    public long getId() {
        return m_nId;
    }

    public void setId(long m_nId) {
        this.m_nId = m_nId;
    }

    public String getGrats() {
        return m_strCongratulations;
    }

    public void setGrats(String m_strName) {
        this.m_strCongratulations = m_strName;
    }


    @Override
    public String toString() {
        return "" + m_strCongratulations + "\n";
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBConstants.TABLE_CONGRATULATIONS_FIELD_CONGRATULATIONS, m_strCongratulations);

        return values;
    }
}


