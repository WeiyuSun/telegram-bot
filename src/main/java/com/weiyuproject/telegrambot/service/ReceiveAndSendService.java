package com.weiyuproject.telegrambot.service;

import com.weiyuproject.telegrambot.object.entity.UserEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface ReceiveAndSendService {

    void processUpdateFromTelegram(Update update);
    void sendMessageToTelegram(Object message);
    void sendDailyMessageToTelegram(List<UserEntity> users);
}
