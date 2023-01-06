package com.weiyuproject.telegrambot.utils;

import com.weiyuproject.telegrambot.entity.Subscriber;

public class TelegramCommands {


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

    public final static String CALLBACK_CHANGE_HR = "callback_change_hr";
    public final static String CALLBACK_CHANGE_MIN = "callback_change_min";
    public final static String CALLBACK_SCHEDULE_TYPE = "callback_schedule_type";
    public final static String CALLBACK_CHANGE_DATE = "callback_change_date";
    public final static String CALLBACK_SCHEDULE_CONFIRM = "/callback_schedule_confirm";
    public final static String CALLBACK_SCHEDULE_CANCEL = "/callback_schedule_cancel";

    public static Boolean isTextCommand(String text) {
        return text.equals(SUBSCRIBE) || text.equals(UNSUBSCRIBE) || text.equals(START) || text.equals(HELP) || text.equals(WEATHER) || text.equals(MUTE_WEATHER) ||
                text.equals(QUOTE) || text.equals(MUTE_QUOTE) || text.equals(NEWS) || text.equals(MUTE_NEWS) || text.equals(SCHEDULE);
    }

}
