package com.weiyuproject.telegrambot.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToUserUtils {
    private ToUserUtils(){}
    public static SendMessage getTextMessage(Long chatID, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText(message);

        return sendMessage;
    }

    public static SendMessage getRequestLocationButtonMessage(Long chatID, String buttonContent, String textMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText(textMessage);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(buttonContent);
        keyboardButton.setRequestLocation(true);
        keyboardRow.add(keyboardButton);
        keyboardRows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public static List<List<InlineKeyboardButton>> inlineTimeKeyboardRows(int hour, int minute, String scheduleInfo) {
        List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();

        List<InlineKeyboardButton> addTimeRow = new ArrayList<>();
        InlineKeyboardButton addOneHourButton = new InlineKeyboardButton();
        addOneHourButton.setText("+1");
        addOneHourButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, 1, scheduleInfo));
        InlineKeyboardButton addThreeHoursButton = new InlineKeyboardButton();
        addThreeHoursButton.setText("+3");
        addThreeHoursButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, 3, scheduleInfo));
        addTimeRow.add(addOneHourButton);
        addTimeRow.add(addThreeHoursButton);
        InlineKeyboardButton addFiveMinButton = new InlineKeyboardButton();
        addFiveMinButton.setText("+5");
        addFiveMinButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, 5, scheduleInfo));
        InlineKeyboardButton addTenMinButton = new InlineKeyboardButton();
        addTenMinButton.setText("+10");
        addTenMinButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, 10, scheduleInfo));
        addTimeRow.add(addFiveMinButton);
        addTimeRow.add(addTenMinButton);

        List<InlineKeyboardButton> timeRow = new ArrayList<>();
        InlineKeyboardButton hourButton = new InlineKeyboardButton();
        hourButton.setText(EmojiUtil.getPositiveNumberEmoji(hour));
        hourButton.setCallbackData("/invalid");
        InlineKeyboardButton minButton = new InlineKeyboardButton();
        minButton.setText(EmojiUtil.getPositiveNumberEmoji(minute));
        minButton.setCallbackData("/invalid");
        timeRow.add(hourButton);
        timeRow.add(minButton);

        List<InlineKeyboardButton> subTimeRow = new ArrayList<>();
        InlineKeyboardButton subOneHourButton = new InlineKeyboardButton();
        subOneHourButton.setText("-1");
        subOneHourButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, -1, scheduleInfo));
        InlineKeyboardButton subThreeHoursButton = new InlineKeyboardButton();
        subThreeHoursButton.setText("-3");
        subThreeHoursButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, -3, scheduleInfo));
        subTimeRow.add(subOneHourButton);
        subTimeRow.add(subThreeHoursButton);
        InlineKeyboardButton subFiveMinButton = new InlineKeyboardButton();
        subFiveMinButton.setText("-5");
        subFiveMinButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, -5, scheduleInfo));
        InlineKeyboardButton subTenMinButton = new InlineKeyboardButton();
        subTenMinButton.setText("-10");
        subTenMinButton.setCallbackData(String.format("%s`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, -10, scheduleInfo));
        subTimeRow.add(subFiveMinButton);
        subTimeRow.add(subTenMinButton);

        List<InlineKeyboardButton> submitRow = new ArrayList<>();
        InlineKeyboardButton cancelButton = new InlineKeyboardButton();
        cancelButton.setText("Cancel✖️");
        cancelButton.setCallbackData(TelegramCommands.CALLBACK_SCHEDULE_CANCEL);
        InlineKeyboardButton confirmButton = new InlineKeyboardButton();
        confirmButton.setText("Confirm✔️");
        confirmButton.setCallbackData(String.format("%s`%d`%d`%s", TelegramCommands.CALLBACK_SCHEDULE_CONFIRM, hour, minute, scheduleInfo));
        submitRow.add(cancelButton);
        submitRow.add(confirmButton);

        inlineRows.add(addTimeRow);
        inlineRows.add(timeRow);
        inlineRows.add(subTimeRow);
        inlineRows.add(submitRow);
        return inlineRows;
    }

    public static InlineKeyboardButton getInlineButton(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }

    public static List<InlineKeyboardButton> getInlineButtonsRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(List.of(buttons));
    }

    public static List<List<InlineKeyboardButton>> getInlineButtonsRows(List<InlineKeyboardButton>... rows) {
        return new ArrayList<>(List.of(rows));
    }

    public static List<InlineKeyboardButton> getCalendarHeadRow() {
        return getInlineButtonsRow(
                getInlineButton("Sa", "/invalid"),
                getInlineButton("Mo", "/invalid"),
                getInlineButton("Tu", "/invalid"),
                getInlineButton("We", "/invalid"),
                getInlineButton("Th", "/invalid"),
                getInlineButton("Fr", "/invalid"),
                getInlineButton("Su", "/invalid")
        );
    }

    public static List<List<InlineKeyboardButton>> getInlineCalendar(LocalDate firstDayOfMonth, LocalDate targetDay, int scheduleType, String scheduleName) {
        List<List<InlineKeyboardButton>> calendarRows = new ArrayList<>();
        calendarRows.add(ToUserUtils.getCalendarHeadRow());

        int ignoreDays = (firstDayOfMonth.getDayOfWeek().equals(DayOfWeek.SUNDAY)) ? 0 : firstDayOfMonth.getDayOfWeek().getValue();
        int totalDaysButton;
        if ((ignoreDays + firstDayOfMonth.lengthOfMonth()) % 7 == 0) {
            totalDaysButton = ignoreDays + firstDayOfMonth.lengthOfMonth();
        } else {
            totalDaysButton = ((ignoreDays + firstDayOfMonth.lengthOfMonth()) / 7 + 1) * 7;
        }

        List<InlineKeyboardButton> currRow = new ArrayList<>();
        for (int i = 1; i <= totalDaysButton; i++) {
            currRow.add(ToUserUtils.getDayButtonForCalendar(firstDayOfMonth, targetDay, i, scheduleType, scheduleName));

            if (i % 7 == 0) {
                calendarRows.add(currRow);
                currRow = new ArrayList<>();
            }
        }

        InlineKeyboardButton cancelButton = ToUserUtils.getInlineButton("Cancel", TelegramCommands.CALLBACK_CANCEL);
        InlineKeyboardButton confirmButton = ToUserUtils.getInlineButton("Confirm", String.format("%s`%d`%d`%d`%d`%s", TelegramCommands.CALLBACK_CONFIRM_DATE, scheduleType, targetDay.getYear(), targetDay.getMonthValue(), targetDay.getDayOfMonth(), scheduleName));

        // TODO: change year button
        InlineKeyboardButton lastMonth = ToUserUtils.getInlineButton("<<", String.format("%s`%d`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_MONTH, -1, scheduleType, firstDayOfMonth.getYear(), firstDayOfMonth.getMonthValue(), scheduleName));
        InlineKeyboardButton currMonth = getInlineButton(String.format("%d/%d", firstDayOfMonth.getMonthValue(), firstDayOfMonth.getYear()), "/invalid");
        InlineKeyboardButton nextMonth = ToUserUtils.getInlineButton(">>", String.format("%s`%d`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_MONTH, 1, scheduleType, firstDayOfMonth.getYear(), firstDayOfMonth.getMonthValue(), scheduleName));
        calendarRows.add(ToUserUtils.getInlineButtonsRow(lastMonth, currMonth, nextMonth));
        calendarRows.add(ToUserUtils.getInlineButtonsRow(cancelButton, confirmButton));

        return calendarRows;
    }

    public static InlineKeyboardButton getDayButtonForCalendar(LocalDate firstDayOfMonth, LocalDate today, int buttonIndex, int scheduleType, String scheduleName) {
        int firstDayWeekValue = firstDayOfMonth.getDayOfWeek().getValue() + 1 == 8 ? 1 : firstDayOfMonth.getDayOfWeek().getValue() + 1;

        int year = firstDayOfMonth.getYear();
        int month = firstDayOfMonth.getMonthValue();

        if (buttonIndex < firstDayWeekValue) {
            return getInlineButton("☁️", "/invalid");
        }

        if (buttonIndex < firstDayWeekValue + firstDayOfMonth.getMonth().length(firstDayOfMonth.isLeapYear())) {
            String format = String.format("%s`%d`%d`%d`%d`%s", TelegramCommands.CALLBACK_CHANGE_DAY, scheduleType, year, month, (buttonIndex - firstDayWeekValue + 1), scheduleName);
            if (today.getMonthValue() == month && buttonIndex - firstDayWeekValue + 1 == today.getDayOfMonth()) {
                return getInlineButton("\uD83D\uDFE2", format);
            }
            return getInlineButton(String.valueOf(buttonIndex - firstDayWeekValue + 1), format);
        }

        return getInlineButton("☁️", "/invalid");
    }

    public static EditMessageText getEditMessageText(String text, Long chatID, Integer messageID, InlineKeyboardMarkup inlineKeyboardMarkup){
        EditMessageText editMessageText = new EditMessageText(text);
        editMessageText.setMessageId(messageID);
        editMessageText.setChatId(chatID);
        editMessageText.setReplyMarkup( inlineKeyboardMarkup);
        return editMessageText;
    }

    public static EditMessageReplyMarkup getEditMessageMarkup(Long chatID, Integer messageID, InlineKeyboardMarkup inlineKeyboardMarkup){
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatID);
        editMessageReplyMarkup.setMessageId(messageID);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageReplyMarkup;
    }
}


