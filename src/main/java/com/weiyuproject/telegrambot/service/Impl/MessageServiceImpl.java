package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.MessageService;
import com.weiyuproject.telegrambot.service.SubscriberService;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Value("${telegram.config.botUsername}")
    private String botUsername;
    @Value("${telegram.config.accessToken}")
    private String accessToken;
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private OpenWeatherApi openWeatherApi;
    @Autowired
    ReceiveAndSendServiceImpl receiveAndSendService;

    @Override
    public void processMessageFromTelegram(Message message) {
        if (subscriberService.contains(message.getChatId())) {
            processMessageFroSubscriber(message);
        } else {
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
            receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(message.getChatId(), "\uD83D\uDE41 You haven't subscribed to me yet, click here -> /subscribe to get fully functions\uD83D\uDE09"));
        }
    }


    private void processTextMessage(Message message) {
        String text = message.getText();
        Long userID = message.getChatId();
        switch (text) {

            case TelegramCommands.START:
//                receiveAndSendService.sendMessage(ToUserUtils.getRequestLocationButtonMessage(userID, "Share Location", "🎉Welcome to here."));
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "I am a robot that is used to send daily messages to provide convince for your life."));
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "If you want more features, I sincerely invite you to become my subscriber."));
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "My service is based on your location. So, to subcribe me, please share your location"));
                break;

            case TelegramCommands.SUBSCRIBE:
                if (subscriberService.contains(userID)) {
                    receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "You're already a subscriber. It's my pleasure to serve you\uD83E\uDD70"));
                } else {
//                    receiveAndSendService.sendMessage(ToUserUtils.getRequestLocationButtonMessage(userID, "Share Location", "To subscribe me and get all functions , please share your location with me."));
                    receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "To subscribe me and get all functions , please share your location with me."));
                }
                break;

            case TelegramCommands.UNSUBSCRIBE:
                subscriberService.removeSubscriber(userID);
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Subscription cancelled"));
                break;

            case TelegramCommands.HELP:
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(message.getChatId(), "This is for help command"));
                break;

            case TelegramCommands.WEATHER:
                Subscriber subscriber = subscriberService.getSubscriber(userID);
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "🎉Weather service activated"));
                subscriber.setWeatherService(true);
                break;

            case TelegramCommands.MUTE_WEATHER:
                subscriberService.getSubscriber(userID).setWeatherService(false);
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Weather service closed"));
                break;

            case TelegramCommands.QUOTE:
                subscriberService.getSubscriber(userID).setQuoteService(true);
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "🎉Quote service activated"));
                break;

            case TelegramCommands.MUTE_QUOTE:
                subscriberService.getSubscriber(userID).setQuoteService(false);
                receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Quote service closed"));
                break;
            case TelegramCommands.SCHEDULE:
                System.out.println("hello world");
//                SendMessage sendMessage = new SendMessage();
//                sendMessage.setText("select a number");
//                sendMessage.setChatId(userID);
//                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//                InlineKeyboardButton numberButton = new InlineKeyboardButton();
//                InlineKeyboardButton addButton = new InlineKeyboardButton();
//
//                numberButton.setText("1");
//                numberButton.setCallbackData("1");
//                addButton.setText("+1");
//                addButton.setCallbackData("/plusNumber 1");
//
//
//                List<InlineKeyboardButton> numberRow = new ArrayList<>();
//                List<InlineKeyboardButton> addRow = new ArrayList<>();
//
//                List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();
//                inlineRows.add(addRow);
//                inlineRows.add(numberRow);
//
//                inlineKeyboardMarkup.setKeyboard(inlineRows);
//                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//
//                receiveAndSendService.sendMessage(sendMessage);
                SendMessage sendMessage = new SendMessage(); // Create a message object object
                sendMessage.setChatId(userID);
                sendMessage.setText("select a number");
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();

                List<InlineKeyboardButton> addNumberRow = new ArrayList<>();
                InlineKeyboardButton addNumberButton = new InlineKeyboardButton();
                addNumberButton.setText("add a number");
                addNumberButton.setCallbackData("/plusNumber 1");
                addNumberRow.add(addNumberButton);

                List<InlineKeyboardButton> numberRow = new ArrayList<>();
                InlineKeyboardButton numberButton = new InlineKeyboardButton();
                numberButton.setText("1");
                numberButton.setCallbackData("1");
                numberRow.add(numberButton);
                // Set the keyboard to the markup
                inlineRows.add(addNumberRow);
                inlineRows.add(numberRow);
                // Add it to the message
                markupInline.setKeyboard(inlineRows);
                sendMessage.setReplyMarkup(markupInline);

                receiveAndSendService.sendMessage(sendMessage);
                break;
        }
    }

    private void processLocationMessage(Message message) {
        Long userID = message.getChatId();
        Subscriber user = subscriberService.getSubscriberList().get(userID);

        String feedbackMessage = "🎉Thanks!! Your location updated";
        if (user == null) {
            feedbackMessage = "🎉You have successfully subscribed to the daily Message.\n\uD83D\uDDD2Check the command menu to edit your daily message";
            user = new Subscriber();
            user.setId(userID);
            subscriberService.getSubscriberList().put(userID, user);
        }

        user.setLongitude(message.getLocation().getLongitude());
        user.setLatitude(message.getLocation().getLatitude());
        user.setCity(openWeatherApi.getCity(user.getLatitude(), user.getLongitude()));

        receiveAndSendService.sendMessage(ToUserUtils.getTextMessage(userID, feedbackMessage));
    }
}
