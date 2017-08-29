package com.danil.auto_birthday_vk.model.engine;


import android.content.Context;

import com.danil.auto_birthday_vk.UserInfo;
import com.danil.auto_birthday_vk.model.wrappers.BannedFriendsWrapper;

import java.util.ArrayList;

public class BannedFriendsEngine {

    private Context m_context;

    public BannedFriendsEngine(Context context) {
        m_context = context;
    }

    public ArrayList<UserInfo> getAll() {
        BannedFriendsWrapper wrapper = new BannedFriendsWrapper(m_context);
        return wrapper.getAll();
    }

    public UserInfo getItemById(long nId) {
        BannedFriendsWrapper wrapper = new BannedFriendsWrapper(m_context);
        return wrapper.getItemById(nId);
    }


    public void deleteItem(UserInfo item) {
        BannedFriendsWrapper wrapper = new BannedFriendsWrapper(m_context);
        wrapper.deleteItem(item);
    }


}


