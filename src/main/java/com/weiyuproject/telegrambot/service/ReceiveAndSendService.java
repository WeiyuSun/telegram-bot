package com.weiyuproject.telegrambot.service;

import com.weiyuproject.telegrambot.entity.Subscriber;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface ReceiveAndSendService {
    void sendMessage(Object message);
    void sendDailyMessage();
    void sendDailyMessage(List<Subscriber> subscribers);
}
