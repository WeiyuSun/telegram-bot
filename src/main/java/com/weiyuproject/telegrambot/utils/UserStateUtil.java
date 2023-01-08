package com.weiyuproject.telegrambot.utils;

import com.weiyuproject.telegrambot.entity.Subscriber;

public class UserStateUtil {
    private UserStateUtil(){}
    public static int OK = 0;
    public static int WAITING_LOCATION_MESSAGE = 10001;
    public static int WAITING_EVENT_NAME = 10002;
    public static int WAITING_EVENT_DATE = 10003;
    public static int WAITING_EVENT_TIME = 10004;

    public static void processUserState(Subscriber subscriber){

    }
}
