package com.weiyuproject.telegrambot.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ToUserUtils {
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

    public static SendMessage getInitialTimeKeyboard(Long userID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userID);
        sendMessage.setText("Please select a time for your new schedule");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        markupInline.setKeyboard(inlineTimeKeyboardRows(12, 30, "test event", ScheduleType.ONE_TIME_SCHEDULE));
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

    public static EditMessageReplyMarkup setReplyTimeKeyboard(Long userID, String[] commandTokens) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();

        if (TelegramCommands.CALLBACK_CHANGE_HR.equals(commandTokens[0])) {

        } else if (TelegramCommands.CALLBACK_CHANGE_MIN.equals(commandTokens[0])) {

        }
        return null;
    }

    public static List<List<InlineKeyboardButton>> inlineTimeKeyboardRows(int hour, int minute, String scheduleInfo, Integer evenType) {
        List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();

        List<InlineKeyboardButton> addTimeRow = new ArrayList<>();
        InlineKeyboardButton addOneHourButton = new InlineKeyboardButton();
        addOneHourButton.setText("+1");
        addOneHourButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, 1, scheduleInfo, evenType));
        InlineKeyboardButton addThreeHoursButton = new InlineKeyboardButton();
        addThreeHoursButton.setText("+3");
        addThreeHoursButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, 3, scheduleInfo, evenType));
        addTimeRow.add(addOneHourButton);
        addTimeRow.add(addThreeHoursButton);
        InlineKeyboardButton addFiveMinButton = new InlineKeyboardButton();
        addFiveMinButton.setText("+5");
        addFiveMinButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, 5, scheduleInfo, evenType));
        InlineKeyboardButton addTenMinButton = new InlineKeyboardButton();
        addTenMinButton.setText("+10");
        addTenMinButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, 10, scheduleInfo, evenType));
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
        subOneHourButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, -1, scheduleInfo, evenType));
        InlineKeyboardButton subThreeHoursButton = new InlineKeyboardButton();
        subThreeHoursButton.setText("-3");
        subThreeHoursButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_HR, hour, minute, -3, scheduleInfo, evenType));
        subTimeRow.add(subOneHourButton);
        subTimeRow.add(subThreeHoursButton);
        InlineKeyboardButton subFiveMinButton = new InlineKeyboardButton();
        subFiveMinButton.setText("-5");
        subFiveMinButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, -5, scheduleInfo, evenType));
        InlineKeyboardButton subTenMinButton = new InlineKeyboardButton();
        subTenMinButton.setText("-10");
        subTenMinButton.setCallbackData(String.format("%s&&%d&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_CHANGE_MIN, hour, minute, -10, scheduleInfo, evenType));
        subTimeRow.add(subFiveMinButton);
        subTimeRow.add(subTenMinButton);

        List<InlineKeyboardButton> submitRow = new ArrayList<>();
        InlineKeyboardButton cancelButton = new InlineKeyboardButton();
        cancelButton.setText("Cancel✖️");
        cancelButton.setCallbackData(TelegramCommands.CALLBACK_SCHEDULE_CANCEL);
        InlineKeyboardButton confirmButton = new InlineKeyboardButton();
        confirmButton.setText("Confirm✔️");
        confirmButton.setCallbackData(String.format("%s&&%d&&%d&&%s&&%d", TelegramCommands.CALLBACK_SCHEDULE_CONFIRM, hour, minute, scheduleInfo, evenType));
        submitRow.add(cancelButton);
        submitRow.add(confirmButton);

        inlineRows.add(addTimeRow);
        inlineRows.add(timeRow);
        inlineRows.add(subTimeRow);
        inlineRows.add(submitRow);
        return inlineRows;
    }

    public static InlineKeyboardButton getInlineButton(String text, String callBackDate) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(text);
        inlineKeyboardButton.setCallbackData(callBackDate);
        return inlineKeyboardButton;
    }

    public static List<InlineKeyboardButton> getInlineButtonsRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(List.of(buttons));
    }

    public static List<List<InlineKeyboardButton>> getInlineButtonRows(List<InlineKeyboardButton>... rows) {
        return new ArrayList<>(List.of(rows));
    }
}


//    public InlineKeyboardMarkup getInitialInlineKeyboard(Calendar ){
//
//    }
//
//    public EditMessageReplyMarkup getReplyInlineKeyboard(List<List<InlineButton>> buttonRows){
//
//    }

