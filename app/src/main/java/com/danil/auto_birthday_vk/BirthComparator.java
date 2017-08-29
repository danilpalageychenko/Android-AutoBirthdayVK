package com.danil.auto_birthday_vk;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

public class BirthComparator implements Comparator<UserInfo> {


    @Override
    public int compare(UserInfo o1, UserInfo o2) {



        String p1 = o1.getBdate();
        String q2 = o2.getBdate();

        DateFormat format = new SimpleDateFormat("d MMMM");


        Date date = null;
        try {
            date = format.parse(p1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date1 = null;
        try {
            date1 = format.parse(q2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dayMonth = new SimpleDateFormat("d MMMM");
        String currentDayAndMonth = dayMonth.format(new Date());
        Date current = null;
        try{
            current = format.parse(currentDayAndMonth);
        }catch (ParseException e){
            e.printStackTrace();
        }

        Calendar diff1 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        diff1.setTimeInMillis(date1.getTime() - current.getTime());
        String daysTillBday1 = getCoolTime(diff1);

        Calendar diff = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        diff.setTimeInMillis(date.getTime() - current.getTime());
        String daysTillBday = getCoolTime(diff);



        int n = Integer.parseInt(daysTillBday1);
        int x = Integer.parseInt(daysTillBday);



        if (x < n) {
            return -1;
        } else if (x > n) {
            return 1;
        } else {
            return 0;
        }
    }
    public String getCoolTime(Calendar time) {
        return (time.get(Calendar.DAY_OF_YEAR) - 1) + "";
    }
}