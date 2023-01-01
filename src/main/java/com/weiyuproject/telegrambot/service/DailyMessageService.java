package com.weiyuproject.telegrambot.service;

import com.weiyuproject.telegrambot.entity.Subscriber;

public interface DailyMessageService {
     String getDailyMessage(Subscriber subscriber);
}
