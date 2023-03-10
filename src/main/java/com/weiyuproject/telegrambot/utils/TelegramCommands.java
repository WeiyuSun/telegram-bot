package com.weiyuproject.telegrambot.utils;

public class TelegramCommands {
    private TelegramCommands() {
    }
    public final static String UNSUBSCRIBE = "/unsubscribe";
    public final static String START = "/start";
    public final static String HELP = "/help";

    public final static String UPDATE_LOCATION = "/update_location";
    public final static String WEATHER = "/weather";
    public final static String MUTE_WEATHER = "/mute_weather";
    public final static String QUOTE = "/quote";
    public final static String MUTE_QUOTE = "/mute_quote";
    public final static String NEWS = "/news";
    public final static String MUTE_NEWS = "/mute_news";
    public final static String SCHEDULE = "/schedule";
    public final static String DROP_SCHEDULE = "/drop_schedule";

    public final static String SCHEDULE_LIST = "/schedule_list";
    public final static String CANCEL_SCHEDULE = "/cancel_schedule";
    public final static String EDIT_SCHEDULE = "/edit_schedule";
    public final static String CALLBACK_CHANGE_HR = "/1";
    public final static String CALLBACK_CHANGE_MIN = "/2";
    public final static String CALLBACK_CANCEL = "/3";
    public final static String CALLBACK_CHANGE_MONTH = "/4";
    public final static String CALLBACK_CONFIRM_DATE = "/5";
    public final static String CALLBACK_CONFIRM_WEEK = "/6";
    public final static String CALLBACK_SCHEDULE_TYPE = "/7";
    public final static String CALLBACK_CHANGE_DAY = "/8";
    public final static String CALLBACK_SCHEDULE_CONFIRM = "/9";
    public final static String CALLBACK_SCHEDULE_CANCEL = "/10";
    public final static String CALLBACK_EDIT_ONETIME_SCHEDULE = "/11";
    public final static String CALLBACK_EDIT_WEEKLY_SCHEDULE = "/12";
    public final static String CALLBACK_EDIT_ANNIVERSARY = "/13";
    public final static String CALLBACK_DROP_ONETIME_SCHEDULE = "/14";
    public final static String CALLBACK_DROP_WEEKLY_SCHEDULE = "/15";
    public final static String CALLBACK_DROP_ANNIVERSARY = "/16";

    public static Boolean isTextCommand(String text) {
        return text.equals(UNSUBSCRIBE) || text.equals(START) || text.equals(HELP) || text.equals(WEATHER) || text.equals(MUTE_WEATHER) ||
                text.equals(QUOTE) || text.equals(MUTE_QUOTE) || text.equals(SCHEDULE) || text.equals(DROP_SCHEDULE);
    }
}
