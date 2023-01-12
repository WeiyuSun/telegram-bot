package com.weiyuproject.telegrambot.controller;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
public class BotController {
    @Autowired
    private ReceiveAndSendService receiveAndSendService;

    @PostMapping("/callback/daily_robot")
    public void receiveUpdateFromTelegram(@RequestBody Update update){
        System.out.println("get update " + update);
        receiveAndSendService.processUpdateFromTelegram(update);
    }

}
