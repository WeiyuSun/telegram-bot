package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.service.CallbackQueryService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.utils.TelegramCommands;
import com.weiyuproject.telegrambot.utils.ToUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class CallbackQueryServiceImpl implements CallbackQueryService {
    @Autowired
    private ReceiveAndSendService receiveAndSendService;

    @Override
    public void processCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        String[] callbackTokens = callbackData.split("&&");
        System.out.println(Arrays.toString(callbackTokens));
        // 0 command, 1 hour, 2 minute, 3 change value, 4 schedule info 5 schedule type

        if(TelegramCommands.CALLBACK_SCHEDULE_TYPE.equals(callbackTokens[0])){
            // 0 command, 1 schedule type, 2 schedule name
            // get name and type, set date then...


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
