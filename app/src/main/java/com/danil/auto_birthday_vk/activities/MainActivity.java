package com.danil.auto_birthday_vk.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.danil.auto_birthday_vk.R;
import com.danil.auto_birthday_vk.db.DBHelper;
import com.danil.auto_birthday_vk.services.ServiceStartUpReceiver;
import com.danil.auto_birthday_vk.tools_and_constants.AppSettings;
import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;
import com.crashlytics.android.Crashlytics;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isResumed = false;
    static MainActivity mainActivity;
    int smile = 0x1F60A;
    int cake = 0x1F382;
    int cap = 0x1F389;
    int kiss = 0x1F61A;
    int kiss_with_heart = 0x1F618;
    int flowers = 0x1F490;
    int watermelon = 0x1F349;
    int teeth = 0x1F62C;


    public void showLogin() {
        startActivityForResult(new Intent(this, LoginInActivity.class), 1);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {

        new Thread(new Runnable() {
            @Override
            public void run() {


                VKRequest request = VKApi.friends().get(VKParameters.from(
                        VKApiConst.FIELDS, "photo_100,online,bdate",
                        VKApiConst.NAME_CASE, "nom",
                        "order", "hints"));

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        try {
                            parseJson(response.json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                if (requestCode == 1) {
                    if (data == null) {
                        return;
                    }
                    String backpressed = data.getStringExtra("backpressed");
                    if (backpressed.equals("1")) finish();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
        if (!VKSdk.isLoggedIn()) {
            showLogin();
        }

    }

    @Override
    protected void onPause() {
        isResumed = false;
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());

        View buttonToUserActivity = findViewById(R.id.button_friends);
        buttonToUserActivity.setOnClickListener(this);

        View buttonToCongratulationsActivity = findViewById(R.id.button_congratulations);
        buttonToCongratulationsActivity.setOnClickListener(this);

        View buttonToSettingActivity = findViewById(R.id.button_settings);
        buttonToSettingActivity.setOnClickListener(this);

        mainActivity = this;

        AppSettings appSettings = new AppSettings(this);
        if (appSettings.getIsFirstStart()) {

            //Если первый раз - устанавливаем флаг в false, что бы не допустить повторного вызова метода
            appSettings.setIsFirstStart(false);

            //Создаем класс-помощника работы с БД

            DBHelper dbHelper = new DBHelper(this);
            //Получаем базу данных для записи
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //Создаем класс для передачи данных в БД (в формате ключь-значение).
            ContentValues values = new ContentValues();
            //Добавляем данные в объект
            //В качестве ключа используем имена столбцов.
            values.put(DBConstants.TABLE_CONGRATULATIONS_FIELD_CONGRATULATIONS, "С днем рождения!\n" + getEmijoByUnicode(cake) +
                    "Счастья, здоровья, долгих лет!!!" + getEmijoByUnicode(smile));
            //Записываем их в БД
            db.insert(DBConstants.TABLE_CONGRATULATIONS, null, values);

            values.clear();
            values.put(DBConstants.TABLE_CONGRATULATIONS_FIELD_CONGRATULATIONS, "С днюхай Братан!\n" +
                    "Добра, уюта и тепла!!!" + getEmijoByUnicode(kiss) + getEmijoByUnicode(kiss));
            db.insert(DBConstants.TABLE_CONGRATULATIONS, null, values);




            //После окончания работы с БД - ОБЯЗАТЕЛЬНО ЗАКРЫВАЕМ БД!
            db.close();
            //После закрытия БД - ОБЯЗАТЕЛЬНО ЗАКРЫВАЕМ КЛАСС-ПОМОЩНИК!
            dbHelper.close();

        }

        AlarmManager alarmManager = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, ServiceStartUpReceiver.class);

        PendingIntent pending = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        long t = System.currentTimeMillis() + 60000;
        long time_update = 60000;
        alarmManager.setRepeating(AlarmManager.RTC, t, time_update, pending);


    }

    public String getEmijoByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_friends:
                Intent intent2 = new Intent(this, FriendsActivity.class);
                startActivity(intent2);
                break;
            case R.id.button_congratulations:
                Intent intent3 = new Intent(this, CongratulationsActivity.class);
                startActivity(intent3);
                break;
            case R.id.button_settings:
                Intent intent4 = new Intent(this, AboutActivity.class);
                startActivity(intent4);
                break;
        }
    }

    private void parseJson(JSONObject json) throws JSONException {

        try {
            JSONArray allFriends = json.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < allFriends.length(); i++) {
                JSONObject friend;
                friend = allFriends.getJSONObject(i);
                int id = friend.getInt("id");
                String name = friend.getString("first_name");
                String lastname = friend.getString("last_name");

                String bdate = friend.optString("bdate", null);

                if (bdate == null) {
                    continue;
                }


                SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
                Date dateObj = curFormater.parse(bdate);
                SimpleDateFormat postFormater = new SimpleDateFormat("d MMMM");

                String newDateStr = postFormater.format(dateObj);

                bdate = newDateStr;


                DBHelper dbHelper = new DBHelper(this);

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String strSelect = DBConstants.TABLE_FRIENDS_FIELD_USER_ID + "=?";
                String[] arrAgg = new String[]{Integer.toString(id)};
                Cursor cursor = db.query(DBConstants.TABLE_FRIENDS, null, strSelect, arrAgg, null, null, null);

                if (cursor != null) {
                    try {
                        if (cursor.getCount() == 1) {
                            continue;
                        }
                    } finally {
                        cursor.close();
                    }
                }


                ContentValues values = new ContentValues();

                values.put(DBConstants.TABLE_FRIENDS_FIELD_NAME, name);
                values.put(DBConstants.TABLE_FRIENDS_FIELD_SNAME, lastname);
                values.put(DBConstants.TABLE_FRIENDS_FIELD_USER_ID, id);
                values.put(DBConstants.TABLE_FRIENDS_FIELD_BDATE, bdate);


                db.insert(DBConstants.TABLE_FRIENDS, null, values);

                db.close();

                dbHelper.close();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}