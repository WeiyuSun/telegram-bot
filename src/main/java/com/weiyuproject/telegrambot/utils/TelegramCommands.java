package com.weiyuproject.telegrambot.utils;

public class TelegramCommands {
    private TelegramCommands(){}


    public final static String SUBSCRIBE = "/subscribe";
    public final static String UNSUBSCRIBE = "/unsubscribe";
    public final static String START = "/start";
    public final static String HELP = "/help";
    public final static String WEATHER = "/weather";
    public final static String MUTE_WEATHER = "/mute_weather";
    public final static String QUOTE = "/quote";
    public final static String MUTE_QUOTE = "/mute_quote";
    public final static String NEWS = "/news";
    public final static String MUTE_NEWS = "/mute_news";
    public final static String SCHEDULE = "/schedule";
    public final static String SCHEDULE_LIST = "/schedule_list";
    public final static String CANCEL_SCHEDULE = "/cancel_schedule";
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

    public static Boolean isTextCommand(String text) {
        return text.equals(SUBSCRIBE) || text.equals(UNSUBSCRIBE) || text.equals(START) || text.equals(HELP) || text.equals(WEATHER) || text.equals(MUTE_WEATHER) ||
                text.equals(QUOTE) || text.equals(MUTE_QUOTE) || text.equals(NEWS) || text.equals(MUTE_NEWS) || text.equals(SCHEDULE);
    }

}
