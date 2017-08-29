package com.danil.auto_birthday_vk.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.danil.auto_birthday_vk.Congratulations;
import com.danil.auto_birthday_vk.R;
import com.danil.auto_birthday_vk.adapters.CongratulationsAdapter;
import com.danil.auto_birthday_vk.model.engine.CongratulationsRecordEngine;

import java.util.ArrayList;

public class CongratulationsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView m_ListView = null;
    private CongratulationsAdapter m_adapter = null;
    public ArrayList<Congratulations> m_arrData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);
        getSupportActionBar().setTitle("Поздравления");


        m_ListView = (ListView) findViewById(R.id.listViewCongratulationsActivity);
        m_adapter = new CongratulationsAdapter(m_arrData);
        m_ListView.setAdapter(m_adapter);
        m_ListView.setOnItemClickListener(this);
        View btnAdd = findViewById(R.id.addNewButtonCongratulationsActivity);
        btnAdd.setOnClickListener(this);

        View btnRemoveAll = findViewById(R.id.deleteAllButtonCongratulationsActivity);
        btnRemoveAll.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {

        CongratulationsRecordEngine engine = new CongratulationsRecordEngine(this);
        ArrayList<Congratulations> arrData = engine.getAll();

        m_arrData.clear();
        m_arrData.addAll(arrData);
        m_adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNewButtonCongratulationsActivity:
                Intent intent = new Intent(this, ComposeActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteAllButtonCongratulationsActivity:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Удаление всех поздравлений");
                builder.setMessage("Вы потеряете все поздравления!");

                builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        CongratulationsRecordEngine engine = new CongratulationsRecordEngine(CongratulationsActivity.this);
                        engine.deleteAll();
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
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentEdit = new Intent(this, ComposeActivity.class);
        intentEdit.putExtra(ComposeActivity.EXTRA_KEY_ID, id);
        startActivity(intentEdit);
    }
}

