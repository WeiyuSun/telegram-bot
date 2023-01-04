package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.DailyMessageService;
import com.weiyuproject.telegrambot.service.MessageService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.SubscriberService;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

@Service
public class ReceiveAndSendServiceImpl extends TelegramLongPollingBot implements ReceiveAndSendService {
    @Value("${telegram.config.botUsername}")
    private String botUsername;
    @Value("${telegram.config.accessToken}")
    private String accessToken;
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private DailyMessageService dailyMessageService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            messageService.processMessageFromTelegram(update.getMessage());
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send message to all subscribers
     */
    public void sendDailyMessage() {
        Map<Long, Subscriber> subscriberList = subscriberService.getSubscriberList();
        subscriberList.forEach((chatID, subscriber) -> {
            List<String> todayMessages = dailyMessageService.getDailyMessages(subscriber);

            for (String message : todayMessages) {
                sendMessage(ToUserUtils.getTextMessage(chatID, message));
            }
        });
    }

    /**
     * send message to given subscribers
     */
    public void sendDailyMessage(List<Subscriber> subscribers) {
        for (Subscriber subscriber : subscribers) {
            List<String> todayMessages = dailyMessageService.getDailyMessages(subscriber);

            for (String message : todayMessages) {
                sendMessage(ToUserUtils.getTextMessage(subscriber.getId(), message));
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return accessToken;
    }

    @PostConstruct
    public void botRegistration() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
