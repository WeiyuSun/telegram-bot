package com.weiyuproject.telegrambot.robot;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
@Setter
@Slf4j
public class DailyMessageBot extends SpringWebhookBot {
    private String botUsername;
    private String botToken;
    private String botPath;
    public DailyMessageBot(SetWebhook setWebhook) {
        super(setWebhook);
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    @Override
    public String getBotToken() {
        return botToken;
    }
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }
    @Override
    public String getBotPath() {
        return botPath;
    }
}
