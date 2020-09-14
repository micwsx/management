package com.micwsx.project.advertise.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil {

    public static boolean isToday(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = simpleDateFormat.format(date);
        return formatDate.equals(simpleDateFormat.format(new Date()));
    }

    public static boolean isYesterday(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatDate.equals(simpleDateFormat.format(calendar.getTime()));
    }

    public static Date getDate(int year,int month,int day,int hour,int minute,int secend){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year, month-1, day, hour, minute, secend);
        return calendar.getTime();
    }

    public static Date changeNowDate(int year,int month,int day,int hour,int minute,int secend){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, secend);
        return calendar.getTime();
    }

    public static String format(Date date,String pattern){
        SimpleDateFormat format=new SimpleDateFormat(pattern);
        String result = format.format(date);
        return result;
    }

    public static Date getDate(String date,String pattern){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    public static void main(String[] args) {

        Date date=getDate(2020, 7, 22, 10, 33, 1);
        System.out.println(format(date,"yyyy-MM-dd hh:mm:ss"));

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2020, 6, 13);
//        System.out.println(isToday(calendar.getTime()));
//        System.out.println(isYesterday(calendar.getTime()));
    }
}
