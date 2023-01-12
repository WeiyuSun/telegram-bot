package com.weiyuproject.telegrambot.config;

import com.weiyuproject.telegrambot.robot.DailyMessageBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class TelegramBotConfig {
    @Value("${telegram.config.botUsername}")
    private String botUsername;
    @Value("${telegram.config.accessToken}")
    private String accessToken;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url("https://3975-2604-3d09-aa7c-87c0-c3f-bd9b-adef-6fe8.ngrok.io").build();
    } // public address, now it is ngrok, in the future it will (i think) be the server address
    // Create it as
    @Bean
    public DailyMessageBot dailyMessageBot(SetWebhook setWebhookInstance){

        DailyMessageBot dailyMessageBot = new DailyMessageBot(setWebhookInstance);
        dailyMessageBot.setBotUsername(botUsername);
        dailyMessageBot.setBotToken(accessToken);
        dailyMessageBot.setBotPath("daily_robot");

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(dailyMessageBot, setWebhookInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("SetWebHook from Daily rob bot {}", setWebhookInstance);
        return dailyMessageBot;
    }
}