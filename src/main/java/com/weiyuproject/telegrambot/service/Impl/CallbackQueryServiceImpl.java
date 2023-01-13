package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.object.entity.*;
import com.weiyuproject.telegrambot.service.CallbackQueryService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.ScheduleService;
import com.weiyuproject.telegrambot.service.SubscriberService;
import com.weiyuproject.telegrambot.utils.DateUtils;
import com.weiyuproject.telegrambot.utils.ScheduleUtils;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CallbackQueryServiceImpl implements CallbackQueryService {
    @Autowired
    private ReceiveAndSendService receiveAndSendService;
    @Autowired
    private SubscriberService subscriberService;

    @Autowired private ScheduleService scheduleService;

    @Override
    public void processCallbackQuery(CallbackQuery callbackQuery) {
        SubscriberEntity subscriber = subscriberService.getSubscriber(callbackQuery.getMessage().getChatId());
        String callbackData = callbackQuery.getData();
        String[] callbackTokens = callbackData.split("`");
        switch (callbackTokens[0]) {
            case TelegramCommands.CALLBACK_SCHEDULE_TYPE -> {
                processScheduleTypeCallback(callbackQuery, callbackTokens, subscriber);
            }
            case TelegramCommands.CALLBACK_CHANGE_MONTH -> {
                // 0 command, 1 changed value,  2 schedule type, 3 year, 4 month, 5 schedule name
                LocalDate firstDayOfMonth = LocalDate.of(Integer.parseInt(callbackTokens[3]), Integer.parseInt(callbackTokens[4]), 1).plusMonths(Integer.parseInt(callbackTokens[1]));
                EditMessageReplyMarkup toNextMonthMarkup = new EditMessageReplyMarkup();
                toNextMonthMarkup.setReplyMarkup(new InlineKeyboardMarkup(ToUserUtils.getInlineCalendar(firstDayOfMonth, firstDayOfMonth, Integer.parseInt(callbackTokens[2]), callbackTokens[5])));
                toNextMonthMarkup.setChatId(callbackQuery.getMessage().getChatId());
                toNextMonthMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
                receiveAndSendService.sendMessageToTelegram(toNextMonthMarkup);
            }
            case TelegramCommands.CALLBACK_CHANGE_DAY -> {
                // 0 command, 1 schedule type, 2 year, 3 month, 4 day, 5 schedule name
                LocalDate selectedDay = LocalDate.of(Integer.parseInt(callbackTokens[2]), Integer.parseInt(callbackTokens[3]), Integer.parseInt(callbackTokens[4]));
                LocalDate fistDayOfMonth = LocalDate.of(selectedDay.getYear(), selectedDay.getMonthValue(), 1);
                List<List<InlineKeyboardButton>> calendar = ToUserUtils.getInlineCalendar(fistDayOfMonth, selectedDay, Integer.parseInt(callbackTokens[1]), callbackTokens[5]);

                EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
                editMessageReplyMarkup.setReplyMarkup(new InlineKeyboardMarkup(calendar));
                editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
                editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
                receiveAndSendService.sendMessageToTelegram(editMessageReplyMarkup);
            }
            case TelegramCommands.CALLBACK_CONFIRM_WEEK -> {
                // 0 command, 1 type, 2 year, 3 month, 4 day, 5 name
                String scheduleInfo = callbackTokens[1] + "`" + callbackTokens[2] + "`" + callbackTokens[3] + "`" + callbackTokens[4] + "`" + callbackTokens[5];
                EditMessageText editMessageText = ToUserUtils.getEditMessageText(
                        "What is the schedule time?🤔",
                        callbackQuery.getMessage().getChatId(),
                        callbackQuery.getMessage().getMessageId(),
                        new InlineKeyboardMarkup(ToUserUtils.inlineTimeKeyboardRows(12, 30, scheduleInfo))
                );
                receiveAndSendService.sendMessageToTelegram(editMessageText);
            }
            case TelegramCommands.CALLBACK_CONFIRM_DATE -> {
                System.out.println(Arrays.toString(callbackTokens));
                // 0 command, 1 schedule type, 2 year, 3 month, 4 day, 5 schedule name
                if (Integer.parseInt(callbackTokens[1]) == ScheduleUtils.ANNIVERSARY) {
                    LocalDate anniversaryDate = LocalDate.of(Integer.parseInt(callbackTokens[2]), Integer.parseInt(callbackTokens[3]), Integer.parseInt(callbackTokens[4]));
                    AnniversaryEntity anniversary = new AnniversaryEntity(callbackQuery.getMessage().getChatId(), callbackTokens[5], anniversaryDate);
                    scheduleService.addSchedule(anniversary);
                    EditMessageText editMessageText = ToUserUtils.getEditMessageText("🎉your anniversary saved!", callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), null);
                    receiveAndSendService.sendMessageToTelegram(editMessageText);
                } else if (Integer.parseInt(callbackTokens[1]) == ScheduleUtils.ONE_TIME_SCHEDULE) {
                    // TODO: if the date is earlier than current, let user select a new time

                    String scheduleInfo = callbackTokens[1] + "`" + callbackTokens[2] + "`" + callbackTokens[3] + "`" + callbackTokens[4] + "`" + callbackTokens[5];
                    EditMessageText editMessageText = ToUserUtils.getEditMessageText(
                            "What is the schedule time?🤔",
                            callbackQuery.getMessage().getChatId(),
                            callbackQuery.getMessage().getMessageId(),
                            new InlineKeyboardMarkup(ToUserUtils.inlineTimeKeyboardRows(12, 30, scheduleInfo))
                    );
                    receiveAndSendService.sendMessageToTelegram(editMessageText);
                }
            }
            case TelegramCommands.CALLBACK_CHANGE_HR -> {
                // 0 command, 1 hour, 2 minute, 3 hr shift, 4 schedule type, 5 year, 6 month, 7 day, 8 schedule name
                String scheduleInfo = String.format("%s`%s`%s`%s`%s", callbackTokens[4], callbackTokens[5], callbackTokens[6], callbackTokens[7], callbackTokens[8]);
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
            }
            case TelegramCommands.CALLBACK_CHANGE_MIN -> {
                String scheduleInfo = String.format("%s`%s`%s`%s`%s", callbackTokens[4], callbackTokens[5], callbackTokens[6], callbackTokens[7], callbackTokens[8]);
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
            }
            case TelegramCommands.CALLBACK_SCHEDULE_CONFIRM -> {
                int year = Integer.parseInt(callbackTokens[4]);
                int month = Integer.parseInt(callbackTokens[5]);
                int day = Integer.parseInt(callbackTokens[6]);
                int hour = Integer.parseInt(callbackTokens[1]);
                int minute = Integer.parseInt(callbackTokens[2]);
                int scheduleType = Integer.parseInt(callbackTokens[3]);
                String scheduleName = callbackTokens[7];

                // 0 command, 1 minute, 2 hour, 3 type, 4 year, 5 month, 6 day, 7 name
                LocalDateTime scheduleTime = LocalDateTime.of(year, month, day, hour, minute); // save as UTC time

                ScheduleEntity schedule = null;
                if (scheduleType == ScheduleUtils.ONE_TIME_SCHEDULE) {
                    schedule = new OneTimeScheduleEntity(callbackQuery.getMessage().getChatId(), scheduleName, scheduleTime);
                } else if (scheduleType == ScheduleUtils.WEEKLY_SCHEDULE) {
                    schedule = new WeeklyScheduleEntity(callbackQuery.getMessage().getChatId(), scheduleName, scheduleTime);
                }

                scheduleService.addSchedule(schedule);
                EditMessageText editMessageText = ToUserUtils.getEditMessageText("🎉your schedule saved!", callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), null);
                receiveAndSendService.sendMessageToTelegram(editMessageText);
            }


            // TODO: implement functions so that user can edit schedulers
//            case TelegramCommands.CALLBACK_EDIT_ONETIME_SCHEDULE:{
//
//                break;
//            }
//
//            case TelegramCommands.CALLBACK_EDIT_ANNIVERSARY:{
//
//                break;
//            }
//
//            case TelegramCommands.CALLBACK_EDIT_WEEKLY_SCHEDULE:{
//                ToUserUtils.get
//                break;
//            }

            case TelegramCommands.CALLBACK_DROP_ANNIVERSARY -> {
                // 0 command, 1 schedule id
                System.out.println("from abc: " + callbackData);
                EditMessageText editMessageText = ToUserUtils.getEditMessageText("Schedule has been drop", callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), null);
                receiveAndSendService.sendMessageToTelegram(editMessageText);
                scheduleService.deleteAnniversary(Long.valueOf(callbackTokens[1]));
            }
            case TelegramCommands.CALLBACK_DROP_ONETIME_SCHEDULE -> {
                System.out.println("from abc: " + callbackData);
                EditMessageText editMessageText = ToUserUtils.getEditMessageText("Schedule has been drop", callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), null);
                receiveAndSendService.sendMessageToTelegram(editMessageText);
                scheduleService.deleteOneTimeSchedule(Long.valueOf(callbackTokens[1]));
            }
            case TelegramCommands.CALLBACK_DROP_WEEKLY_SCHEDULE -> {
                System.out.println("from abc: " + callbackData);
                EditMessageText editMessageText = ToUserUtils.getEditMessageText("Schedule has been drop", callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), null);
                receiveAndSendService.sendMessageToTelegram(editMessageText);
                scheduleService.deleteWeeklySchedule(Long.valueOf(callbackTokens[1]));
            }
            case TelegramCommands.CALLBACK_SCHEDULE_CANCEL -> {
                EditMessageText editMessageText = ToUserUtils.getEditMessageText("Your schedule cancelled", callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), null);
                receiveAndSendService.sendMessageToTelegram(editMessageText);
            }
        }


    }

    private void processScheduleTypeCallback(CallbackQuery callbackQuery, String[] callbackTokens, SubscriberEntity subscriber) {
        // 0 callback command, 1 schedule type, 2 schedule name
        int scheduleType = Integer.parseInt(callbackTokens[1]);
        String scheduleName = callbackTokens[2];

        if (ScheduleUtils.ONE_TIME_SCHEDULE == scheduleType || ScheduleUtils.ANNIVERSARY == scheduleType) {
            LocalDate today = LocalDateTime.now().plusHours(subscriber.getTimeOffset()).toLocalDate();
            LocalDate firstDayOfMonth = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
            EditMessageText editMessageText = ToUserUtils.getEditMessageText(
                    "What is the date of the schedule?🤔",
                    callbackQuery.getMessage().getChatId(),
                    callbackQuery.getMessage().getMessageId(),
                    new InlineKeyboardMarkup(ToUserUtils.getInlineCalendar(firstDayOfMonth, today, scheduleType, scheduleName))
            );
            receiveAndSendService.sendMessageToTelegram(editMessageText);
        } else if (ScheduleUtils.WEEKLY_SCHEDULE == scheduleType) {
            LocalDateTime localDateTime = LocalDateTime.now().plusHours(subscriber.getTimeOffset());
            Map<Integer, InlineKeyboardButton> oneWeekDates = new HashMap<>();

            for (int i = 1; i <= 7; i++) {
                // 0 command, 1 type, 2 year, 3 month, 4 day, 5 name
                InlineKeyboardButton inlineKeyboardButton = ToUserUtils.getInlineButton(DateUtils.getWeekOfDay(localDateTime.getDayOfWeek().getValue()), String.format("%s`%d`%d`%d`%d`%s", TelegramCommands.CALLBACK_CONFIRM_WEEK, ScheduleUtils.WEEKLY_SCHEDULE, localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(), scheduleName));

                oneWeekDates.put(localDateTime.getDayOfWeek().getValue(), inlineKeyboardButton);
                localDateTime = localDateTime.plusDays(1);
            }

            List<InlineKeyboardButton> weeksRow = ToUserUtils.getInlineButtonsRow(
                    oneWeekDates.get(DayOfWeek.SUNDAY.getValue()),
                    oneWeekDates.get(DayOfWeek.MONDAY.getValue()),
                    oneWeekDates.get(DayOfWeek.TUESDAY.getValue()),
                    oneWeekDates.get(DayOfWeek.WEDNESDAY.getValue()),
                    oneWeekDates.get(DayOfWeek.THURSDAY.getValue()),
                    oneWeekDates.get(DayOfWeek.FRIDAY.getValue()),
                    oneWeekDates.get(DayOfWeek.SATURDAY.getValue())
            );

            EditMessageText editMessageText = ToUserUtils.getEditMessageText(
                    "What day of the week does it start?",
                    callbackQuery.getMessage().getChatId(),
                    callbackQuery.getMessage().getMessageId(),
                    new InlineKeyboardMarkup(ToUserUtils.getInlineButtonsRows(weeksRow))
            );

            receiveAndSendService.sendMessageToTelegram(editMessageText);
        }
    }

}
