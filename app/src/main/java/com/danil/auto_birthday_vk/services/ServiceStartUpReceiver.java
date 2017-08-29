package com.danil.auto_birthday_vk.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.danil.auto_birthday_vk.db.DBHelper;
import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ServiceStartUpReceiver extends BroadcastReceiver {

    static public final long MIN_15 = 900000; //miliseconds 900000 = 15min
    static public final long HOUR = MIN_15 * 4;

    SimpleDateFormat dayMonth = new SimpleDateFormat("d MMMM");
    String currentDayAndMonth = dayMonth.format(new Date());

    SimpleDateFormat year = new SimpleDateFormat("yyyy");
    String currentYear = year.format(new Date());

    final Random random = new Random();


    @Override
    public void onReceive(final Context context, Intent intent) {


        final DBHelper dbhelper = new DBHelper(context);
        final Cursor CR = getInformation(dbhelper);
        final Cursor CR2 = getCongratulations(dbhelper);
        CR2.moveToFirst();

        String[] array = new String[CR2.getCount()];
        int congr = 0;

        while (CR2.moveToNext()) {
            String uname = CR2.getString(0);
            array[congr] = uname;
            congr++;
        }


        CR.moveToFirst();



        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        do {

           if(!CR.equals(null)) {

                String bdate = CR.getString(1);
                final String id = CR.getString(0);
                final String name = CR.getString(2);
                final String sname = CR.getString(3);
                final String fullname = name + " " + sname;

                String select = DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID + " LIKE ? AND " +
                        DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_YEAR + " LIKE ? ";
                String[] argSelect = new String[]{id, currentYear};

                Cursor cur = db.query(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY, null, select, argSelect, null, null, null);

                if (cur == null || cur.getCount() > 0) {

                    if (cur != null) {
                        cur.close();
                    }
                    cur.close();
                    continue;
                }
                cur.close();

                if (bdate.equals(currentDayAndMonth)) {

                    VKRequest sendMsg = new VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID, id,
                            VKApiConst.MESSAGE, array[(random.nextInt(congr))]));


                    sendMsg.executeWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {

                            ContentValues values = new ContentValues();
                            values.put(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID, id);
                            values.put(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_YEAR, currentYear);
                            values.put(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_NAME, fullname);

                            db.insert(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY, null, values);

                            super.onComplete(response);
                        }

                    });
                }
           }

        } while (CR.moveToNext());

        CR.close();
        CR2.close();

        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ServiceStartUpReceiver.class);

        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        long t = System.currentTimeMillis() + HOUR;
        long time_update = HOUR;

        alarmManager.setRepeating(AlarmManager.RTC, t, time_update, pending);
    }

    public Cursor getInformation(DBHelper dbhelper) {

        SQLiteDatabase SQ = dbhelper.getReadableDatabase();
        String[] columns = {DBConstants.TABLE_FRIENDS_FIELD_USER_ID,
                DBConstants.TABLE_FRIENDS_FIELD_BDATE,
                DBConstants.TABLE_FRIENDS_FIELD_NAME,
                DBConstants.TABLE_FRIENDS_FIELD_SNAME};

        Cursor CR = SQ.query(DBConstants.TABLE_FRIENDS, columns, null, null, null, null, null);

        return CR;

    }

    public Cursor getCongratulations(DBHelper dbhelper) {


        SQLiteDatabase SQ = dbhelper.getReadableDatabase();
        String[] columns = {DBConstants.TABLE_CONGRATULATIONS_FIELD_CONGRATULATIONS};

        Cursor CR = SQ.query(DBConstants.TABLE_CONGRATULATIONS, columns, null, null, null, null, null);

        return CR;

    }

}