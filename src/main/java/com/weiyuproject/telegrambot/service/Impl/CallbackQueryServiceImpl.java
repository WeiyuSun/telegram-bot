package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.service.CallbackQueryService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallbackQueryServiceImpl implements CallbackQueryService {
    @Autowired private ReceiveAndSendService receiveAndSendService;
    @Override
    public void processCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        String[] dataTokens = callbackData.split(" ");

        if(dataTokens[0].equals("/plusNumber")){
            Integer newNumber = Integer.parseInt(dataTokens[1]) + 1;
            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
            editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
            editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
            editMessageReplyMarkup.setInlineMessageId(callbackQuery.getInlineMessageId());

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> inlineRows = new ArrayList<>();

            List<InlineKeyboardButton> addNumberRow = new ArrayList<>();
            InlineKeyboardButton addNumberButton = new InlineKeyboardButton();
            addNumberButton.setText("add a number");
            addNumberButton.setCallbackData("/plusNumber " + newNumber);
            addNumberRow.add(addNumberButton);

            List<InlineKeyboardButton> numberRow = new ArrayList<>();
            InlineKeyboardButton numberButton = new InlineKeyboardButton();
            numberButton.setText(newNumber.toString());
            numberButton.setCallbackData(newNumber.toString());
            numberRow.add(numberButton);

            inlineRows.add(addNumberRow);
            inlineRows.add(numberRow);

            markupInline.setKeyboard(inlineRows);
            editMessageReplyMarkup.setReplyMarkup(markupInline);
            receiveAndSendService.sendMessageToTelegram(editMessageReplyMarkup);
        }
    }
}
