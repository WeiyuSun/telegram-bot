package com.weiyuproject.telegrambot.utils;

import com.weiyuproject.telegrambot.object.dto.UserDto;

public class UserStateUtil {
    private UserStateUtil(){}
    public static int OK = 0;
    public static int WAITING_LOCATION_MESSAGE = 10001;
    public static int WAITING_SCHEDULE_NAME = 10002;
    public static int WAITING_SCHEDULE_DATE = 10003;
    public static int WAITING_SCHEDULE_TIME = 10004;

    public static void processUserState(UserDto userDTO){

    }

    public static boolean isStateValid(Integer state) {
        return UserStateUtil.WAITING_SCHEDULE_NAME == state || UserStateUtil.OK == state
                || UserStateUtil.WAITING_LOCATION_MESSAGE == state;
    }
}
