package com.weiyuproject.telegrambot.utils;

public class DateUtils {
    public static final String MONDAY = "Mo";
    public static final String TUESDAY = "Tu";
    public static final String WEDNESDAY = "We";
    public static final String THURSDAY = "Th";
    public static final String FRIDAY = "Fr";
    public static final String SATURDAY = "Sa";
    public static final String SUNDAY = "Su";

    public static String getWeekOfDay(int dayOfWeek){
        if(dayOfWeek == 1)
            return MONDAY;
        if(dayOfWeek == 2)
            return TUESDAY;
        if(dayOfWeek == 3)
            return WEDNESDAY;
        if(dayOfWeek == 4)
            return THURSDAY;
        if(dayOfWeek == 5)
            return FRIDAY;
        if(dayOfWeek == 6)
            return SATURDAY;
        if (dayOfWeek == 7)
            return SUNDAY;
        return null;
    }

}
