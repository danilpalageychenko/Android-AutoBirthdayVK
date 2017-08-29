package com.danil.auto_birthday_vk.model.engine;

import android.content.Context;

import com.danil.auto_birthday_vk.Congratulations;
import com.danil.auto_birthday_vk.model.wrappers.CongratulationsRecordWrapper;

import java.util.ArrayList;

public class CongratulationsRecordEngine {
    private Context m_context = null;

    public CongratulationsRecordEngine(Context context) {
        m_context = context;
    }

    public ArrayList<Congratulations> getAll() {
        CongratulationsRecordWrapper wrapper = new CongratulationsRecordWrapper(m_context);
        return wrapper.getAll();
    }

    public Congratulations getItemById(long nId) {
        CongratulationsRecordWrapper wrapper = new CongratulationsRecordWrapper(m_context);
        return wrapper.getItemById(nId);
    }

    public void insertItem(Congratulations item) {
        CongratulationsRecordWrapper wrapper = new CongratulationsRecordWrapper(m_context);
        wrapper.insertItem(item);
    }

    public void updateItem(Congratulations item) {
        CongratulationsRecordWrapper wrapper = new CongratulationsRecordWrapper(m_context);
        wrapper.updateItem(item);
    }

    public void deleteItem(Congratulations item) {
        CongratulationsRecordWrapper wrapper = new CongratulationsRecordWrapper(m_context);
        wrapper.deleteItem(item);
    }

    public void deleteAll() {
        CongratulationsRecordWrapper wrapper = new CongratulationsRecordWrapper(m_context);
        wrapper.deleteAll();
    }

}
