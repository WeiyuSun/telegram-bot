package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.MessageService;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import jakarta.annotation.PostConstruct;
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
public class MessageServiceImpl extends TelegramLongPollingBot implements MessageService {
    private static Map<Long, Subscriber> subcribeList = new HashMap<>();
    @Value("${telegram.config.botUsername}")
    private String botUsername;
    @Value("${telegram.config.accessToken}")
    private String accessToken;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("update: " + update);
        if (update.hasMessage()) {
            processMessageFromTelegram(update.getMessage());
        }
    }

    @Override
    public void processMessageFromTelegram(Message message) {
        String text = message.getText();
        if (subcribeList.containsKey(message.getChatId())) {
            System.out.println("this is subscriber");
            processMessageFroSubscriber(message);
        } else {
            System.out.println("this is not subscriber");
            processMessageForUnsubscribeUser(message);
        }
    }

    private void processMessageFroSubscriber(Message message) {
        if (message.hasText()) {
            processTextMessage(message);
        }
    }

    private void processMessageForUnsubscribeUser(Message message) {
        if (TelegramCommands.SUBSCRIBE.equals(message.getText()) || TelegramCommands.START.equals(message.getText())) {
            processTextMessage(message);
        } else {
            sendMessage(ToUserUtils.getTextMessage(message.getChatId(), "\uD83D\uDE41 You haven't subscribed to me yet, click here -> /subscribe to get fully functions\uD83D\uDE09"));
        }
    }


    private void processTextMessage(Message message) {
        String text = message.getText();
        switch (text) {
            case TelegramCommands.START:
                sendMessage(ToUserUtils.getTextMessage(message.getChatId(), "Welcome to here. I am a robot that is used to send daily messages to provide convince for your life. If you want more features, I sincerely invite you to become my subscriber.\nClick here -> /subscribe to become subscriber in few steps"));
                break;
            case TelegramCommands.SUBSCRIBE:
                Long userID = message.getChatId();

                if (subcribeList.containsKey(userID)) {
                    sendMessage(ToUserUtils.getTextMessage(userID, "You're already a subscriber. It's my pleasure to serve you\uD83E\uDD70"));
                } else {
                    Subscriber subscriber = new Subscriber();
                    subscriber.setId(userID);
                    subcribeList.put(userID, subscriber);
                    sendMessage(ToUserUtils.getTextMessage(userID, "Congratulation, You have successfully subscribed to the daily Message.\nUse /help to to edit the content of your daily message"));
                }
                break;
            case TelegramCommands.HELP:
                sendMessage(ToUserUtils.getTextMessage(message.getChatId(), "This is for /help command"));
                break;
        }
    }

    private void processLocationMessage(Message message) {

    }

    // @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void sendDailyMessage() {
        System.out.println("pushing");
        System.out.println(subcribeList);
        subcribeList.forEach((chatID, subscriber) -> {
            sendMessage(ToUserUtils.getTextMessage(chatID, "hi, user"));
        });
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
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
