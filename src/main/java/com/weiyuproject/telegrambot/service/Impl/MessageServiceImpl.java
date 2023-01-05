package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.MessageService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.SubscriberService;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
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
    ReceiveAndSendService receiveAndSendService;

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
            receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(message.getChatId(), "\uD83D\uDE41 You haven't subscribed to me yet, click here -> /subscribe to get fully functions\uD83D\uDE09"));
        }
    }


    private void processTextMessage(Message message) {
        String text = message.getText();
        Long userID = message.getChatId();
        switch (text) {
            case TelegramCommands.START: //1349759680
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getRequestLocationButtonMessage(userID, "Share Location", "🎉Welcome to here."));
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "I am a robot that is used to send daily messages to provide convince for your life."));
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "If you want more features, I sincerely invite you to become my subscriber."));
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "My service is based on your location. So, to subcribe me, please share your location"));
                break;

            case TelegramCommands.SUBSCRIBE:
                if (subscriberService.contains(userID)) {
                    receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "You're already a subscriber. It's my pleasure to serve you\uD83E\uDD70"));
                } else {
                    receiveAndSendService.sendMessageToTelegram(ToUserUtils.getRequestLocationButtonMessage(userID, "Share Location", "To subscribe me and get all functions , please share your location with me."));
                    receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "To subscribe me and get all functions , please share your location with me."));
                }
                break;

            case TelegramCommands.UNSUBSCRIBE:
                subscriberService.removeSubscriber(userID);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Subscription cancelled"));
                break;

            case TelegramCommands.HELP:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(message.getChatId(), "This is for help command"));
                break;

            case TelegramCommands.WEATHER:
                Subscriber subscriber = subscriberService.getSubscriber(userID);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "🎉Weather service activated"));
                subscriber.setWeatherService(true);
                break;

            case TelegramCommands.MUTE_WEATHER:
                subscriberService.getSubscriber(userID).setWeatherService(false);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Weather service closed"));
                break;

            case TelegramCommands.QUOTE:
                subscriberService.getSubscriber(userID).setQuoteService(true);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "🎉Quote service activated"));
                break;

            case TelegramCommands.MUTE_QUOTE:
                subscriberService.getSubscriber(userID).setQuoteService(false);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Quote service closed"));
                break;
            case TelegramCommands.SCHEDULE:
                System.out.println("hello world");

                SendMessage sendMessage = new SendMessage(); // Create a message object object
                sendMessage.setChatId(userID);
                sendMessage.setText("select a time");
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();

                List<InlineKeyboardButton> addTimeRow = new ArrayList<>();
                InlineKeyboardButton addOneHourButton = new InlineKeyboardButton();
                addOneHourButton.setText("+1");
                addOneHourButton.setCallbackData("/plusOneHour 12");
                InlineKeyboardButton addThreeHoursButton = new InlineKeyboardButton();
                addThreeHoursButton.setText("+3");
                addThreeHoursButton.setCallbackData("/plusThreeHours 12");
                addTimeRow.add(addOneHourButton);
                addTimeRow.add(addThreeHoursButton);

                InlineKeyboardButton addFiveMinButton = new InlineKeyboardButton();
                addFiveMinButton.setText("+5");
                addFiveMinButton.setCallbackData("/plusFiveMin 30");
                InlineKeyboardButton addTenMinButton = new InlineKeyboardButton();
                addTenMinButton.setText("+10");
                addTenMinButton.setCallbackData("/plusTenMin 30");
                addTimeRow.add(addFiveMinButton);
                addTimeRow.add(addTenMinButton);

                List<InlineKeyboardButton> timeRow = new ArrayList<>();
                InlineKeyboardButton hourButton = new InlineKeyboardButton();
                hourButton.setText("\n1️⃣2️⃣\n");
                hourButton.setCallbackData("/invalid");
                InlineKeyboardButton minButton = new InlineKeyboardButton();
                minButton.setText("\n3️⃣0️⃣\n");
                minButton.setCallbackData("/invalid");

                timeRow.add(hourButton);
                timeRow.add(minButton);

                List<InlineKeyboardButton> subTimeRow = new ArrayList<>();
                InlineKeyboardButton subOneHourButton = new InlineKeyboardButton();
                subOneHourButton.setText("-1");
                subOneHourButton.setCallbackData("/subOneHour 12");
                InlineKeyboardButton subThreeHoursButton = new InlineKeyboardButton();
                subThreeHoursButton.setText("-3");
                subThreeHoursButton.setCallbackData("/subThreeHours 12");
                subTimeRow.add(subOneHourButton);
                subTimeRow.add(subThreeHoursButton);

                InlineKeyboardButton subFiveMinButton = new InlineKeyboardButton();
                subFiveMinButton.setText("-5");
                subFiveMinButton.setCallbackData("/subFiveMin 30");
                InlineKeyboardButton subTenMinButton = new InlineKeyboardButton();
                subTenMinButton.setText("-10");
                subTenMinButton.setCallbackData("/subTenMin 30");
                subTimeRow.add(subFiveMinButton);
                subTimeRow.add(subTenMinButton);

                List<InlineKeyboardButton> submitRow = new ArrayList<>();
                InlineKeyboardButton cancelButton = new InlineKeyboardButton();
                cancelButton.setText("Cancel❌");
                cancelButton.setCallbackData("/cancelEvent");
                InlineKeyboardButton confirmButton = new InlineKeyboardButton();
                confirmButton.setText("Confirm✔️");
                confirmButton.setCallbackData("/confirmTime 12 30");
                submitRow.add(cancelButton);
                submitRow.add(confirmButton);

                inlineRows.add(addTimeRow);
                inlineRows.add(timeRow);
                inlineRows.add(subTimeRow);
                inlineRows.add(submitRow);

                markupInline.setKeyboard(inlineRows);
                sendMessage.setReplyMarkup(markupInline);

                receiveAndSendService.sendMessageToTelegram(sendMessage);
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

        receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, feedbackMessage));
    }
}
