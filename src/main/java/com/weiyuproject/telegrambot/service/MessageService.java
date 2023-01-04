package com.weiyuproject.telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageService {
    void processMessageFromTelegram(Message message);
}
