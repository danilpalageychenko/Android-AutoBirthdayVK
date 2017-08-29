package com.danil.auto_birthday_vk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danil.auto_birthday_vk.Congratulations;
import com.danil.auto_birthday_vk.R;
import com.danil.auto_birthday_vk.model.engine.CongratulationsRecordEngine;
import com.danil.auto_birthday_vk.model.wrappers.CongratulationsRecordWrapper;

import static android.widget.Toast.LENGTH_LONG;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    /*private long m_nId = -1;*/
    private Congratulations m_congratulations = null;

    private EditText m_gratsEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setTitle("Редактирование поздравления");

        Button btnAdd = (Button) findViewById(R.id.addButtonComposeActivity);
        btnAdd.setOnClickListener(this);

        Button btnRemove = (Button) findViewById(R.id.removeButtonComposeActivity);
        btnRemove.setOnClickListener(this);

        Button btnBack = (Button) findViewById(R.id.backButton);
        btnBack.setOnClickListener(this);

        m_gratsEditText = (EditText) findViewById(R.id.gratsEditTextComposeActivity);


        Intent intent = getIntent();
        long nId = -1;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                nId = bundle.getLong(EXTRA_KEY_ID, -1);
            }
        }

        if (nId != -1) {
            btnAdd.setText("Обновить");

            btnBack.setVisibility(View.GONE);

            btnRemove.setVisibility(View.VISIBLE);

            CongratulationsRecordEngine engine = new CongratulationsRecordEngine(this);
            m_congratulations = engine.getItemById(nId);
            m_gratsEditText.setText(m_congratulations.getGrats());
        }
    }

    @Override
    public void onClick(View v) {
        CongratulationsRecordEngine engine = new CongratulationsRecordEngine(this);
        switch (v.getId()) {
            case R.id.addButtonComposeActivity:
                if (m_congratulations == null) {
                    m_congratulations = new Congratulations(m_gratsEditText.getText().toString());

                    CongratulationsRecordWrapper CRW = new CongratulationsRecordWrapper(this);
                    CRW.insertItem(m_congratulations);



                } else {
                    m_congratulations.setGrats(m_gratsEditText.getText().toString());

                }

                if (m_congratulations.getId() != -1) {
                    engine.updateItem(m_congratulations);
                } else {
                    if (m_gratsEditText.getText().toString().equals("")) {
                        Toast.makeText(this, "Нельзя создать пустое поздравление!", LENGTH_LONG).show();

                    } else {
                       // engine.insertItem(m_congratulations);
                    }
                }
                break;

            case R.id.backButton:
                finish();
                break;


            case R.id.removeButtonComposeActivity:

                engine.deleteItem(m_congratulations);


        }
        finish();
    }
}
