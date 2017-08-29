package com.danil.auto_birthday_vk.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.danil.auto_birthday_vk.R;
import com.danil.auto_birthday_vk.UserInfo;
import com.danil.auto_birthday_vk.adapters.BannedFriendsAdapter;
import com.danil.auto_birthday_vk.model.engine.BannedFriendsEngine;

import java.util.ArrayList;

public class BannedFriends extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView m_ListView = null;
    private BannedFriendsAdapter m_adapter = null;
    public ArrayList<UserInfo> m_arrData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banned_friends);

        getSupportActionBar().setTitle("Список исключений");

        m_ListView = (ListView) findViewById(R.id.bannedFriendsListView);
        m_adapter = new BannedFriendsAdapter(m_arrData);
        m_ListView.setAdapter(m_adapter);
        m_ListView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {

        BannedFriendsEngine engine = new BannedFriendsEngine(this);
        ArrayList<UserInfo> arrData = engine.getAll();

        m_arrData.clear();
        m_arrData.addAll(arrData);
        m_adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Убрать друга из списка исключений?");
        builder.setMessage("Друг снова будет поздравляться.");

        builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                BannedFriendsEngine engine = new BannedFriendsEngine(BannedFriends.this);
                engine.deleteItem(m_arrData.get(position));
                updateList();

            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }
}
