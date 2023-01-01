package com.weiyuproject.telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramMessageService {

    void processMessageFromTelegram(Message message);
    void sendDailyMessage();
}
