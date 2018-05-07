package com.rit.integration.gateway.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by nirbo on 12/6/2015.
 */
public class EventTime {

    private int second;
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;


    public EventTime() {
    }

    public EventTime(int second, int minute, int hour, int day, int month, int year) {
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public EventTime(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        setYear(instance.get(Calendar.YEAR));
        setMonth(instance.get(Calendar.MONTH) + 1);
        setDay(instance.get(Calendar.DAY_OF_MONTH));

        setHour(instance.get(Calendar.HOUR_OF_DAY));
        setMinute(instance.get(Calendar.MINUTE));
        setSecond(instance.get(Calendar.SECOND));


    }


    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }


    public void setSecond(int second) {
        this.second = second;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "EventTime{" +
                "second=" + second +
                ", minute=" + minute +
                ", hour=" + hour +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }

    //2010-5-20   140A 0514
    //10:34:16 = 0A 22 10
    //140A05140A2210
    public String toHexString() {
        StringBuilder result = new StringBuilder();
        String year = yearToHex();
        String month = monthToHex(getMonth());
        String date = dayToHex(getDay());
        String hour = hourToHex(getHour());
        String minute = minuteToHex(getMinute());
        String second = secondToHex(getSecond());
        return result.append(year).append(month).append(date).append(hour).append(minute).append(second).toString();

    }


    public String secondToHex(int second) {
        return intToHex(second);
    }

    public String minuteToHex(int minute) {
        return intToHex(minute);
    }


    public String hourToHex(int hour) {
        return intToHex(hour);
    }

    public String dayToHex(int day) {
        return intToHex(day);
    }

    public String monthToHex(int month) {
        return intToHex(month);
    }


    public String yearToHex() {
        /*String yearStr = String.valueOf(this.getYear());
        String firstPart = yearStr.substring(0, 2);
        String secondPart = yearStr.substring(2, 4);
        String firstDateHex = Integer.toTrimHexStringWithEndBit(Integer.valueOf(firstPart)).toUpperCase();
        Integer intSecondPart = Integer.valueOf(secondPart);
        String secondDateHex = intToHex(intSecondPart);*/
        String year = yearFirstPartToHex() + yearSecondPartToHex();
        return year;
    }


    public String yearSecondPartToHex() {
        String yearStr = String.valueOf(this.getYear());
        String secondPart = yearStr.substring(2, 4);
        return intToHex( Integer.valueOf(secondPart));

    }


    public String yearFirstPartToHex() {
        String yearStr = String.valueOf(this.getYear());
        String firstPart = yearStr.substring(0, 2);
        return intToHex(Integer.valueOf(firstPart));

    }


    private String intToHex(int input) {
        Integer inValue = Integer.valueOf(input);
        String result = Integer.toHexString(inValue).toUpperCase();
        if (inValue < 16) {
            result = "0" + result;
        }
        return result;
    }
}
