package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.DailyMessageService;
import com.weiyuproject.telegrambot.service.TelegramMessageService;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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
public class TelegramMessageServiceImpl extends TelegramLongPollingBot implements TelegramMessageService {
    private static Map<Long, Subscriber> subcribeList = new HashMap<>();
    @Value("${telegram.config.botUsername}")
    private String botUsername;
    @Value("${telegram.config.accessToken}")
    private String accessToken;

    @Autowired
    private DailyMessageService dailyMessageService;

    @Autowired
    private OpenWeatherApi openWeatherApi;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("update: " + update);
        if (update.hasMessage()) {
            processMessageFromTelegram(update.getMessage());
        }
    }

    @Override
    public void processMessageFromTelegram(Message message) {
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
        } else if (message.hasLocation()) {
            processLocationMessage(message);
        }
    }

    private void processMessageForUnsubscribeUser(Message message) {
        if (message.hasLocation()) {
            processLocationMessage(message);
        } else if (TelegramCommands.START.equals(message.getText()) || TelegramCommands.SUBSCRIBE.equals(message.getText())) {
            processTextMessage(message);
        } else {
            sendMessage(ToUserUtils.getTextMessage(message.getChatId(), "\uD83D\uDE41 You haven't subscribed to me yet, click here -> /subscribe to get fully functions\uD83D\uDE09"));
        }
    }


    private void processTextMessage(Message message) {
        String text = message.getText();
        Long userID = message.getChatId();
        switch (text) {

            case TelegramCommands.START:
                sendMessage(ToUserUtils.getRequestLocationButtonMessage(userID, "Share Location", "🎉Welcome to here."));
                sendMessage(ToUserUtils.getTextMessage(userID, "I am a robot that is used to send daily messages to provide convince for your life."));
                sendMessage(ToUserUtils.getTextMessage(userID, "If you want more features, I sincerely invite you to become my subscriber."));
                sendMessage(ToUserUtils.getTextMessage(userID, "My service is based on your location. So, to subcribe me, please share your location"));
                break;

            case TelegramCommands.SUBSCRIBE:
                if (subcribeList.containsKey(userID)) {
                    sendMessage(ToUserUtils.getTextMessage(userID, "You're already a subscriber. It's my pleasure to serve you\uD83E\uDD70"));
                } else {
                    sendMessage(ToUserUtils.getRequestLocationButtonMessage(userID, "Share Location", "To subscribe me and get all functions , please share your location with me."));
                }
                break;

            case TelegramCommands.UNSUBSCRIBE:
                subcribeList.remove(userID);
                sendMessage(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Subscription cancelled"));
                break;

            case TelegramCommands.HELP:
                sendMessage(ToUserUtils.getTextMessage(message.getChatId(), "This is for help command"));
                break;

            case TelegramCommands.WEATHER:
                Subscriber subscriber = subcribeList.get(message.getChatId());
                sendMessage(ToUserUtils.getTextMessage(userID, "🎉Weather service activated"));
                subscriber.setWeatherService(true);
                break;

            case TelegramCommands.MUTE_WEATHER:
                subcribeList.get(userID).setWeatherService(false);
                sendMessage(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Weather service closed"));
                break;
        }
    }

    private void processLocationMessage(Message message) {
        Long userID = message.getChatId();
        Subscriber user = subcribeList.get(userID);

        String feedbackMessage = "🎉Thanks!! Your location updated";
        if (user == null) {
            feedbackMessage = "🎉You have successfully subscribed to the daily Message.\n\uD83D\uDDD2Check the command menu to edit your daily message";
            user = new Subscriber();
            user.setId(userID);
            subcribeList.put(userID, user);
        }

        user.setLongitude(message.getLocation().getLongitude());
        user.setLatitude(message.getLocation().getLatitude());
        user.setCity(openWeatherApi.getCity(user.getLatitude(), user.getLongitude()));

        sendMessage(ToUserUtils.getTextMessage(userID, feedbackMessage));
    }

    public void sendDailyMessage() {
        System.out.println("pushing");
        System.out.println(subcribeList);
        subcribeList.forEach((chatID, subscriber) -> {
            sendMessage(ToUserUtils.getTextMessage(chatID, dailyMessageService.getDailyMessage(subscriber)));
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
