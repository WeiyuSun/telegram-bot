package com.weiyuproject.telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryService {
    void processCallbackQuery(CallbackQuery callbackQuery);
}
