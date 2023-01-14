package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.GoogleApi;
import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.object.entity.*;
import com.weiyuproject.telegrambot.service.MessageService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.ScheduleService;
import com.weiyuproject.telegrambot.service.UserService;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired private UserService userService;
    @Autowired private ScheduleService scheduleService;
    @Autowired private OpenWeatherApi openWeatherApi;
    @Autowired private GoogleApi googleApi;
    @Autowired ReceiveAndSendService receiveAndSendService;

    @Override
    public void processMessageFromTelegram(Message message) {
        if (userService.contains(message.getChatId())) {
            processMessageFroSubscriber(message);
        } else {
            processMessageForUnsubscribeUser(message);
        }
    }

    private void processMessageFroSubscriber(Message message) {
        UserEntity user = userService.getUser(message.getChatId());
        if (message.hasText() && TelegramCommands.isTextCommand(message.getText()) &&
                user.getUserState().equals(UserStateUtil.WAITING_SCHEDULE_NAME)) {
            userService.setUserState(user.getUserID(), UserStateUtil.OK);
        }

        if (message.hasText()) {
            processTextMessage(message);
        } else if (message.hasLocation()) {
            processLocationMessage(message);
        }
    }

    private void processMessageForUnsubscribeUser(Message message) {
        if (message.hasLocation()) {
            processLocationMessage(message);
        } else if (TelegramCommands.START.equals(message.getText())) {
            processTextMessage(message);
        } else {
            receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(message.getChatId(), "\uD83D\uDE41 You haven't subscribed to me yet, please update location to get fully functions\uD83D\uDE09"));
        }
    }

    private void processTextMessage(Message message) {
        String text = message.getText();
        Long userID = message.getChatId();

        switch (text) {
            case TelegramCommands.START:{
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID,  "🎉Welcome to here."));
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "I am a robot that is used to send daily messages in the morning to provide convince for your life."));
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "If you want more features, I sincerely invite you to become my subscriber."));
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getMenuButtonsMessage(userID, "My service is based on your location. So, to subcribe me, please share your location"));
                break;

            }

            case TelegramCommands.UNSUBSCRIBE:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Subscription cancelled"));
                userService.removeUser(userID);
                scheduleService.deleteAllUserSchedule(userID);
                break;

            case TelegramCommands.HELP:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(message.getChatId(), """
                        1⃣️The update_location button is used to upload your current location to me. Since almost all my service need to location. If your move to another area/city, pleaser upload your new location to me. If you want to unsubscribe my service, click /unsubscribe, then all service closed.\s

                        2⃣️/weather can active the weather forecasting services. And to cancel it, use /mute_weather

                        3⃣️/quote can active the daily quote service, then I will send you a well-prepared quote every day. To cancel it, use /mute_quote

                        4⃣️/schedule can help you to create schedules to you. I will remind you your daily schedule every day. To drop schedules, use /drop_schedule.

                        If you like me and my service, please star me on GitHub: https://github.com/WeiyuSun/telegram-bot. This inspired me to create more interesting features\uD83D\uDE0A"""));
                break;

            case TelegramCommands.WEATHER:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "🎉Weather service activated"));
                userService.setWeatherService(userID, true);
                break;

            case TelegramCommands.MUTE_WEATHER:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Weather service closed"));
                userService.setWeatherService(userID, false);
                break;

            case TelegramCommands.QUOTE:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "🎉Quote service activated"));
                userService.setQuoteService(userID, true);
                break;

            case TelegramCommands.MUTE_QUOTE:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "\uD83D\uDE41Quote service closed"));
                userService.setQuoteService(userID, false);
                break;
            case TelegramCommands.SCHEDULE:
                receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, "What the name of your new schedule?"));
                userService.setUserState(userID, UserStateUtil.WAITING_SCHEDULE_NAME);
                break;
            case TelegramCommands.DROP_SCHEDULE: {
                List<ScheduleEntity> allSchedules = scheduleService.getAllSchedules(userID);
                for(ScheduleEntity schedule: allSchedules){
                    System.out.println(schedule);
                }
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(userID);
                sendMessage.setText("Which schedule you want to drop?🤔");
                List<List<InlineKeyboardButton>> buttonsRows = new ArrayList<>();
                for (ScheduleEntity schedule : allSchedules) {
                    String buttonContent = null;
                    String callBack = null;
                    if (schedule instanceof OneTimeScheduleEntity) {
                        buttonContent = ((OneTimeScheduleEntity) schedule).getName() + " at " + ((OneTimeScheduleEntity) schedule).getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        callBack = String.format("%s`%d", TelegramCommands.CALLBACK_DROP_ONETIME_SCHEDULE, ((OneTimeScheduleEntity) schedule).getId());
                    } else if(schedule instanceof WeeklyScheduleEntity) {
                        buttonContent = ((WeeklyScheduleEntity) schedule).getName() + " on " + ((WeeklyScheduleEntity) schedule).getTimeMark().getDayOfWeek().toString().toLowerCase();
                        callBack = String.format("%s`%d", TelegramCommands.CALLBACK_DROP_WEEKLY_SCHEDULE, ((WeeklyScheduleEntity) schedule).getId());
                    } else if(schedule instanceof AnniversaryEntity) {
                        buttonContent = ((AnniversaryEntity) schedule).getName() + " at " + ((AnniversaryEntity) schedule).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        callBack = String.format("%s`%d", TelegramCommands.CALLBACK_DROP_ANNIVERSARY, ((AnniversaryEntity) schedule).getId());
                    }

                    buttonsRows.add(ToUserUtils.getInlineButtonsRow(ToUserUtils.getInlineButton(buttonContent, callBack)));
                }
                sendMessage.setReplyMarkup(new InlineKeyboardMarkup(buttonsRows));
                receiveAndSendService.sendMessageToTelegram(sendMessage);
                break;
            }


            case default:
                if (userService.getUser(userID).getUserState().equals(UserStateUtil.WAITING_SCHEDULE_NAME)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(userID);

                    if (text.length() > 35) {
                        sendMessage.setText("The schedule name cannot over 35 characters.");
                        receiveAndSendService.sendMessageToTelegram(sendMessage);
                        return;
                    }

                    userService.setUserState(userID, UserStateUtil.OK);
                    sendMessage.setText("What the type of your schedule?");
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    InlineKeyboardButton weeklyButton = ToUserUtils.getInlineButton("Weekly", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleUtils.WEEKLY_SCHEDULE, text));
//                    InlineKeyboardButton monthlyButton = ToUserUtils.getInlineButton("Monthly", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleType.MONTHLY_SCHEDULE, text));
                    InlineKeyboardButton oneTimeButton = ToUserUtils.getInlineButton("One Time", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleUtils.ONE_TIME_SCHEDULE, text));
//                    InlineKeyboardButton anniversary = ToUserUtils.getInlineButton("Anniversary", String.format("%s`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_TYPE, ScheduleUtils.ANNIVERSARY, text));
                    List<List<InlineKeyboardButton>> buttonsRows = ToUserUtils.getInlineButtonsRows(ToUserUtils.getInlineButtonsRow(/*anniversary,*/ oneTimeButton, weeklyButton));
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
        UserEntity user = userService.getUser(userID);

        String feedbackMessage = "🎉Thanks!! Your location updated";
        if (user == null) {
            feedbackMessage = "🎉You have successfully subscribed to the daily Message.\n\uD83D\uDDD2Check the command menu to edit your daily message";
            String city = openWeatherApi.getCity(message.getLocation().getLongitude(), message.getLocation().getLatitude());
            user = new UserEntity(userID, city, message.getLocation().getLongitude(), message.getLocation().getLatitude(), -21600);
            userService.add(user);
        }

        user.setLongitude(message.getLocation().getLongitude());
        user.setLatitude(message.getLocation().getLatitude());
        user.setCity(openWeatherApi.getCity(user.getLongitude(), user.getLatitude()));
        user.setTimeOffset(googleApi.getTimeOffset(user.getLongitude(), user.getLatitude()));

        if (!userService.updateUser(user)) {
            feedbackMessage = "Sorry, current location is not available...";
        }

        receiveAndSendService.sendMessageToTelegram(ToUserUtils.getTextMessage(userID, feedbackMessage));
        user.setUserState(UserStateUtil.OK);
    }
}
