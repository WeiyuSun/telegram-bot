package com.weiyuproject.telegrambot.service;

import com.weiyuproject.telegrambot.entity.Subscriber;

import java.util.List;

public interface DailyMessageService {
     List<String> getDailyMessages(Subscriber subscriber);
}
