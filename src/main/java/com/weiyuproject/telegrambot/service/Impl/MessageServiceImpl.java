package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.MessageService;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

@Service
public class MessageServiceImpl extends TelegramLongPollingBot implements MessageService {
    Map<Long, Subscriber> subcribeList = new HashMap<>();
    @Value("${telegram.config.botUsername}")
    private String botUsername;
    @Value("${telegram.config.accessToken}")
    private String accessToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return accessToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage())
            processMessageFromTelegram(update.getMessage());
    }

    @PostConstruct
    public void botRegistration() {
        System.out.println("testing");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processMessageFromTelegram(Message message) {
        if(!subcribeList.containsKey(message.getChatId())){
            message.setText(TelegramCommands.START);
        }

        if (message.hasLocation()) {
            processLocationMessage(message);
        } else if (message.hasText()) {
            processTextMessage(message);
        } else {

        }
    }

    private void processTextMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        String text = message.getText();
        System.out.println(message.getMessageId());
        switch (text) {
            case TelegramCommands.START:
                sendMessage.setText("Welcome!!!");
                sendMessage.setChatId(message.getChatId());
                break;
            case TelegramCommands.SUBSCRIBE:

                // set one keyboard button to get user location

                break;

        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void processLocationMessage(Message locationMessage) {
        String targetUserID = locationMessage.getChatId().toString();
        if (locationMessage.hasText()) {
            String locationText = locationMessage.getText();

            switch (locationText) {
                case TelegramCommands.SHARE_LOCATION:
                    Subscriber newSubscriber = new Subscriber();
                    newSubscriber.setId(locationMessage.getChatId());
                    newSubscriber.setLatitude(locationMessage.getLocation().getLongitude());
                    newSubscriber.setLatitude(locationMessage.getLocation().getLatitude());
                    subcribeList.put(newSubscriber.getId(), newSubscriber);


                    break;
            }
        } else {

        }
    }

    // @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void sendDailyMessage() {
        System.out.println("pushing");
        System.out.println(subcribeList);
        subcribeList.forEach((chatID, subscriber) -> {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("hi, user");
            sendMessage.setChatId(chatID);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

}
