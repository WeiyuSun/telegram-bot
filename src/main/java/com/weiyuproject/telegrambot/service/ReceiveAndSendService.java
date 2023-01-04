package com.weiyuproject.telegrambot.service;

import com.weiyuproject.telegrambot.entity.Subscriber;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface ReceiveAndSendService {
    void sendMessage(SendMessage message);

    void sendDailyMessage();
    void sendDailyMessage(List<Subscriber> subscribers);
}
