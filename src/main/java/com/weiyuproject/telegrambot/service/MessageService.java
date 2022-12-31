package com.weiyuproject.telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageService {

    void processMessageFromTelegram(Message message);
    void sendDailyMessage();
}
