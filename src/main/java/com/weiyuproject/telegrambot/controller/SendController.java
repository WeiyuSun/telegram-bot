package com.weiyuproject.telegrambot.controller;

import com.weiyuproject.telegrambot.api.TianxingApi;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// this controller is used to test
@RestController()
@RequestMapping("/send")
public class SendController {
    @Autowired
    private ReceiveAndSendService receiveAndSendService;

    @Autowired
    private TianxingApi tianxingApi;

    @GetMapping("/dailyMessage")
    public void sendDailyMessage() {
        System.out.println("sending...");
        tianxingApi.getQuoteFromUrl();
        receiveAndSendService.sendDailyMessageToTelegram();
    }
}
