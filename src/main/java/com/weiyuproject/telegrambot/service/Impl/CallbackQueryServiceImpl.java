package com.weiyuproject.telegrambot.service.Impl;
import com.weiyuproject.telegrambot.entity.OneTimeSchedule;
import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.CallbackQueryService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.SubscriberService;
import com.weiyuproject.telegrambot.utils.ScheduleType;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CallbackQueryServiceImpl implements CallbackQueryService {
    @Autowired
    private ReceiveAndSendService receiveAndSendService;
    @Autowired
    private SubscriberService subscriberService;

    @Override
    public void processCallbackQuery(CallbackQuery callbackQuery) {
        Subscriber subscriber = subscriberService.getSubscriber(callbackQuery.getMessage().getChatId());
        String callbackData = callbackQuery.getData();
        String[] callbackTokens = callbackData.split("&&");
        System.out.println(Arrays.toString(callbackTokens));
        switch (callbackTokens[0]) {
            case TelegramCommands.CALLBACK_SCHEDULE_TYPE: {
                processScheduleTypeCallback(callbackQuery, callbackTokens, subscriber);
                break;
            }

            case TelegramCommands.CALLBACK_CHANGE_MONTH: {
                // 0 command, 1 changed value,  2 schedule type, 3 year, 4 month, 5 schedule name
                LocalDate firstDayOfMonth = LocalDate.of(Integer.parseInt(callbackTokens[3]), Integer.parseInt(callbackTokens[4]), 1).plusMonths(Integer.parseInt(callbackTokens[1]));
                EditMessageReplyMarkup toNextMonthMarkup = new EditMessageReplyMarkup();
                toNextMonthMarkup.setReplyMarkup(new InlineKeyboardMarkup(ToUserUtils.getInlineCalendar(firstDayOfMonth, firstDayOfMonth, Integer.parseInt(callbackTokens[2]), callbackTokens[5])));
                toNextMonthMarkup.setChatId(callbackQuery.getMessage().getChatId());
                toNextMonthMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
                receiveAndSendService.sendMessageToTelegram(toNextMonthMarkup);
                break;
            }

            case TelegramCommands.CALLBACK_CHANGE_DAY: {
                // 0 command, 1 schedule type, 2 year, 3 month, 4 day, 5 schedule name
                LocalDate selectedDay = LocalDate.of(Integer.parseInt(callbackTokens[2]), Integer.parseInt(callbackTokens[3]), Integer.parseInt(callbackTokens[4]));
                LocalDate fistDayOfMonth = LocalDate.of(selectedDay.getYear(), selectedDay.getMonthValue(), 1);
                List<List<InlineKeyboardButton>> calendar = ToUserUtils.getInlineCalendar(fistDayOfMonth, selectedDay, Integer.parseInt(callbackTokens[1]), callbackTokens[5]);

                EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
                editMessageReplyMarkup.setReplyMarkup(new InlineKeyboardMarkup(calendar));
                editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
                editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
                receiveAndSendService.sendMessageToTelegram(editMessageReplyMarkup);
                break;
            }

            case TelegramCommands.CALLBACK_CONFIRM_DATE: {
                // TODO: if the date is earlier than current, let user select a new time
                // 0 command, 1 schedule type, 2 year, 3 month, 4 day, 5 schedule name
                String scheduleInfo = callbackTokens[1] + "&&" + callbackTokens[2] + "&&" + callbackTokens[3] + "&&" + callbackTokens[4] + "&&" + callbackTokens[5];
                EditMessageText editMessageText = ToUserUtils.getEditMessageText(
                        "What is the schedule time?🤔",
                        callbackQuery.getMessage().getChatId(),
                        callbackQuery.getMessage().getMessageId(),
                        new InlineKeyboardMarkup(ToUserUtils.inlineTimeKeyboardRows(12, 30, scheduleInfo))
                );
                receiveAndSendService.sendMessageToTelegram(editMessageText);
                break;
            }

            case TelegramCommands.CALLBACK_CHANGE_HR: {
                // 0 command, 1 hour, 2 minute, 3 hr shift, 4 schedule type, 5 year, 6 month, 7 day, 8 schedule name
                String scheduleInfo = String.format("%s&&%s&&%s&&%s&&%s", callbackTokens[4], callbackTokens[5], callbackTokens[6], callbackTokens[7], callbackTokens[8]);
                int changedHour = Integer.parseInt(callbackTokens[1]) + Integer.parseInt(callbackTokens[3]);
                if (changedHour < 0)
                    changedHour += 24;
                else if (changedHour >= 24)
                    changedHour -= 24;
                EditMessageReplyMarkup replyMarkup = new EditMessageReplyMarkup();
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(ToUserUtils.inlineTimeKeyboardRows(changedHour, Integer.parseInt(callbackTokens[2]), scheduleInfo));
                replyMarkup.setReplyMarkup(inlineKeyboardMarkup);
                replyMarkup.setChatId(callbackQuery.getMessage().getChatId());
                replyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
                receiveAndSendService.sendMessageToTelegram(replyMarkup);
                break;
            }

            case TelegramCommands.CALLBACK_CHANGE_MIN: {
                String scheduleInfo = String.format("%s&&%s&&%s&&%s&&%s", callbackTokens[4], callbackTokens[5], callbackTokens[6], callbackTokens[7], callbackTokens[8]);
                // 0 command, 1 hour, 2 minute, 3 min shift, 4 schedule type, 5 year, 6 month, 7 day, 8 schedule name
                int changedMin = Integer.parseInt(callbackTokens[2]) + Integer.parseInt(callbackTokens[3]);
                if (changedMin < 0)
                    changedMin += 60;
                else if (changedMin >= 60)
                    changedMin -= 60;

                List<List<InlineKeyboardButton>> timeKeyboardRows = ToUserUtils.inlineTimeKeyboardRows(Integer.parseInt(callbackTokens[1]), changedMin, scheduleInfo);
                EditMessageReplyMarkup editMessageReplyMarkup = ToUserUtils.getEditMessageMarkup(
                        callbackQuery.getMessage().getChatId(),
                        callbackQuery.getMessage().getMessageId(),
                        new InlineKeyboardMarkup(timeKeyboardRows)
                );

                receiveAndSendService.sendMessageToTelegram(editMessageReplyMarkup);
                break;
            }

            case TelegramCommands.CALLBACK_SCHEDULE_CONFIRM: {
                // 0 command, 1 minute, 2 hour, 3 type, 4 year, 5 month, 6 day, 7 name

                if (callbackTokens[3].equals(ScheduleType.ONE_TIME_SCHEDULE + "")) {
                    int year = Integer.parseInt(callbackTokens[4]);
                    int month = Integer.parseInt(callbackTokens[5]);
                    int day = Integer.parseInt(callbackTokens[6]);
                    int hour = Integer.parseInt(callbackTokens[1]);
                    int minute = Integer.parseInt(callbackTokens[2]);

                    System.out.printf("%d:%d%n", hour, minute);

                    LocalDateTime scheduleTime = LocalDateTime.of(year, month, day, hour, minute).minusSeconds(subscriber.getTimeOffset());
                    OneTimeSchedule schedule = new OneTimeSchedule();
                    schedule.setScheduleTime(scheduleTime);
                    schedule.setName(callbackTokens[7]);
                    subscriberService.addOnetimeSchedule(callbackQuery.getMessage().getChatId(), schedule);
                    for (OneTimeSchedule schedule1: subscriber.getOneTimeEvents()){
                        System.out.println(schedule1.getScheduleTime());
                    }
                    EditMessageText editMessageText = ToUserUtils.getEditMessageText("🎉your schedule saved!", callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), null);
                    receiveAndSendService.sendMessageToTelegram(editMessageText);
                }
                break;
            }
        }


    }

    private void processScheduleTypeCallback(CallbackQuery callbackQuery, String[] callbackTokens, Subscriber subscriber) {
        // 0 callback command, 1 schedule type, 2 schedule name
        int scheduleType = Integer.parseInt(callbackTokens[1]);
        String scheduleName = callbackTokens[2];
        switch (scheduleType) {
            case ScheduleType.ONE_TIME_SCHEDULE:
                LocalDate today = LocalDateTime.now().plusSeconds(subscriber.getTimeOffset()).toLocalDate();
                System.out.println("today is :" + LocalDateTime.now().plusSeconds(subscriber.getTimeOffset()));
                LocalDate firstDayOfMonth = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
                EditMessageText editMessageText = ToUserUtils.getEditMessageText(
                        "What is the date of the schedule?🤔",
                        callbackQuery.getMessage().getChatId(),
                        callbackQuery.getMessage().getMessageId(),
                        new InlineKeyboardMarkup(ToUserUtils.getInlineCalendar(firstDayOfMonth, today, scheduleType, scheduleName))
                );
                receiveAndSendService.sendMessageToTelegram(editMessageText);
                break; // case: ONE_TIME_SCHEDULE
        }
    }

}


// call back
//    @Override
//    public void processCallbackQuery(CallbackQuery callbackQuery) {
//        String callbackData = callbackQuery.getData();
//        System.out.println(callbackData);
//        String[] dataTokens = callbackData.split("&&");
//        System.out.println(Arrays.toString(dataTokens));
//        // 0 command, 1 hour, 2 minute, 3 change value, 4 schedule info 5 schedule type
//
//        if (dataTokens[0].equals(TelegramCommands.CALLBACK_CHANGE_HR)) {
//            int changedHour = Integer.parseInt(dataTokens[1]) + Integer.parseInt(dataTokens[3]);
//            if (changedHour < 0)
//                changedHour += 24;
//            else if (changedHour >= 24)
//                changedHour -= 24;
//            EditMessageReplyMarkup replyMarkup = new EditMessageReplyMarkup();
//            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//            inlineKeyboardMarkup.setKeyboard(ToUserUtils.inlineTimeKeyboardRows(changedHour, Integer.parseInt(dataTokens[2]), dataTokens[4], Integer.parseInt(dataTokens[5])));
//            replyMarkup.setReplyMarkup(inlineKeyboardMarkup);
//            replyMarkup.setChatId(callbackQuery.getMessage().getChatId());
//            replyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
//            receiveAndSendService.sendMessageToTelegram(replyMarkup);
//        } else if (dataTokens[0].equals(TelegramCommands.CALLBACK_CHANGE_MIN)) {
//            int changedMin = Integer.parseInt(dataTokens[2]) + Integer.parseInt(dataTokens[3]);
//            if (changedMin < 0)
//                changedMin += 60;
//            else if (changedMin >= 60)
//                changedMin -= 60;
//            EditMessageReplyMarkup replyMarkup = new EditMessageReplyMarkup();
//            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//            inlineKeyboardMarkup.setKeyboard(ToUserUtils.inlineTimeKeyboardRows(Integer.parseInt(dataTokens[1]), changedMin, dataTokens[4], Integer.parseInt(dataTokens[5])));
//            replyMarkup.setReplyMarkup(inlineKeyboardMarkup);
//            replyMarkup.setChatId(callbackQuery.getMessage().getChatId());
//            replyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
//            receiveAndSendService.sendMessageToTelegram(replyMarkup);
//        } else if(dataTokens[0].equals(TelegramCommands.CALLBACK_SCHEDULE_CONFIRM)) {
//        }
//    }
