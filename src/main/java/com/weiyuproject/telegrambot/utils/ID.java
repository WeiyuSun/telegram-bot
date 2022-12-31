package com.weiyuproject.telegrambot.utils;

import java.util.UUID;

public class ID {
    public static String getSendMessageID(){
        return UUID.randomUUID().toString();
    }
}
