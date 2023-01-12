package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;
import com.weiyuproject.telegrambot.robot.DailyMessageBot;
import com.weiyuproject.telegrambot.service.*;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

@Service
public class ReceiveAndSendServiceImpl implements ReceiveAndSendService {
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CallbackQueryService callbackQueryService;
    @Autowired
    private DailyMessageService dailyMessageService;
    @Autowired
    private DailyMessageBot dailyMessageBot;

    @Override
    public void processUpdateFromTelegram(Update update) {
        System.out.println("get update: " + update);
        if (update.hasMessage()) {
            messageService.processMessageFromTelegram(update.getMessage());
//            subscriberService.getSubscriber(update.getMessage().getChatId()).setUserState(UserStateUtil.OK);
        } else if (update.hasCallbackQuery()) {
            callbackQueryService.processCallbackQuery(update.getCallbackQuery());
        }
    }

    public void sendMessageToTelegram(Object sendMessage) {
        try {
            if (sendMessage instanceof SendMessage)
                dailyMessageBot.execute((SendMessage) sendMessage);
            else if (sendMessage instanceof EditMessageReplyMarkup)
                dailyMessageBot.execute((EditMessageReplyMarkup) sendMessage);
            else if(sendMessage instanceof EditMessageText)
                dailyMessageBot.execute((EditMessageText) sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send message to all subscribers
     */
//    public void sendDailyMessageToTelegram() {
//        Map<Long, Subscriber> subscriberList = subscriberService.getSubscriberList();
//        subscriberList.forEach((chatID, subscriber) -> {
//            List<String> todayMessages = dailyMessageService.getDailyMessages(subscriber);
//
//            for (String message : todayMessages) {
//                sendMessageToTelegram(ToUserUtils.getTextMessage(chatID, message));
//            }
//        });
//    }

    /**
     * send message to given subscribers
     */
    public void sendDailyMessageToTelegram(List<SubscriberEntity> subscribers) {
        for (SubscriberEntity subscriber : subscribers) {
            List<String> todayMessages = dailyMessageService.getDailyMessages(subscriber);

            for (String message : todayMessages) {
                sendMessageToTelegram(ToUserUtils.getTextMessage(subscriber.getUserID(), message));
            }
        }
    }
}
