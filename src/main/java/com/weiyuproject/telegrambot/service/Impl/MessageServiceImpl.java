package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.GoogleApi;
import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;
import com.weiyuproject.telegrambot.service.MessageService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.SubscriberService;
import com.weiyuproject.telegrambot.utils.ScheduleUtils;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import com.weiyuproject.telegrambot.utils.UserStateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
    private GoogleApi googleApi;
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
        SubscriberEntity subscriber = subscriberService.getSubscriber(message.getChatId());
        if ((!message.hasText() || TelegramCommands.isTextCommand(message.getText())) &&
                subscriber.getUserState().equals(UserStateUtil.WAITING_EVENT_NAME)) {
            subscriber.setUserState(UserStateUtil.OK);
        }
        if (message.hasText()) {
            processTextMessage(message, subscriber);
        } else if (message.hasLocation()) {
            processLocationMessage(message);
        }
    }

    private void processMessageForUnsubscribeUser(Message message) {

        if (message.hasLocation()) {
            processLocationMessage(message);
        } else if (TelegramCommands.START.equals(message.getText()) || TelegramCommands.SUBSCRIBE.equals(message.getText())) {
            processTextMessage(message, null);
        } else {
            receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(message.getChatId(), "\uD83D\uDE41 You haven't subscribed to me yet, click here -> /subscribe to get fully functions\uD83D\uDE09"));
        }
    }


    private void processTextMessage(Message message, SubscriberEntity subscriber) {
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
//                    receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "To subscribe me and get all functions , please share your location with me."));
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
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "🎉Weather service activated"));
                subscriberService.setWeatherService(userID, true);
                break;

            case TelegramCommands.MUTE_WEATHER:
                subscriberService.setWeatherService(userID, false);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Weather service closed"));
                break;

            case TelegramCommands.QUOTE:
                subscriberService.setQuoteService(userID, true);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "🎉Quote service activated"));
                break;

            case TelegramCommands.MUTE_QUOTE:
                subscriberService.setQuoteService(userID, false);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Quote service closed"));
                break;
            case TelegramCommands.SCHEDULE:
                subscriberService.setUserState(userID, UserStateUtil.WAITING_EVENT_NAME);
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "What the name of your new schedule/event?"));
                break;
            case default:
                if (UserStateUtil.WAITING_EVENT_NAME == subscriber.getUserState()) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(userID);

                    if (text.length() > 35) {
                        sendMessage.setText("The schedule name cannot over 35 characters.");
                        receiveAndSendService.sendMessageToTelegram(sendMessage);
                        return;
                    }

                    subscriberService.setUserState(userID, UserStateUtil.OK);
                    sendMessage.setText("What the type of your schedule?");
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    InlineKeyboardButton weeklyButton = ToUserUtils.getInlineButton("Weekly", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleUtils.WEEKLY_SCHEDULE, text));
//                    InlineKeyboardButton monthlyButton = ToUserUtils.getInlineButton("Monthly", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleType.MONTHLY_SCHEDULE, text));
                    InlineKeyboardButton oneTimeButton = ToUserUtils.getInlineButton("One Time", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleUtils.ONE_TIME_SCHEDULE, text));
                    InlineKeyboardButton anniversary = ToUserUtils.getInlineButton("Anniversary", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleUtils.ANNIVERSARY, text));
                    List<List<InlineKeyboardButton>> buttonsRows = ToUserUtils.getInlineButtonsRows(ToUserUtils.getInlineButtonsRow(anniversary, oneTimeButton, weeklyButton));
                    markup.setKeyboard(buttonsRows);
                    sendMessage.setReplyMarkup(markup);
                    receiveAndSendService.sendMessageToTelegram(sendMessage);
                }
                break;
        }
    }

    private void processLocationMessage(Message message) {
        System.out.println("from processLocationMessage: " + message);
        Long userID = message.getChatId();
        SubscriberEntity user = subscriberService.getSubscriber(userID);

        String feedbackMessage = "🎉Thanks!! Your location updated";
        if (user == null) {
            feedbackMessage = "🎉You have successfully subscribed to the daily Message.\n\uD83D\uDDD2Check the command menu to edit your daily message";
            String city = openWeatherApi.getCity(message.getLocation().getLongitude(), message.getLocation().getLatitude());
            user = new SubscriberEntity(userID, city, message.getLocation().getLongitude(), message.getLocation().getLatitude(), -21600);
            subscriberService.add(user);
        }

        user.setLongitude(message.getLocation().getLongitude());
        user.setLatitude(message.getLocation().getLatitude());
        user.setCity(openWeatherApi.getCity(user.getLongitude(), user.getLatitude()));
        user.setTimeOffset(googleApi.getTimeOffset(user.getLongitude(), user.getLatitude()));

        if(!subscriberService.updateSubscriber(user)){
            feedbackMessage = "Sorry, current location is not available...";
        }

        receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, feedbackMessage));
        user.setUserState(UserStateUtil.OK);
    }
}
