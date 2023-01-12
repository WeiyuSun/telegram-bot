package com.weiyuproject.telegrambot.controller;

import com.weiyuproject.telegrambot.api.TianxingApi;
import com.weiyuproject.telegrambot.service.AutoTaskService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// this controller is used to test
@RestController()
@RequestMapping("/send")
public class SendController {


    @Autowired
    private AutoTaskService autoTaskService;

    @Autowired
    private TianxingApi tianxingApi;

    @GetMapping("/dailyMessage")
    public void sendDailyMessage(@RequestParam("offset")Integer offset) {
        System.out.println("sending...");
        autoTaskService.utcTimeSub6Task();
        autoTaskService.utcTimePlus8Task();
    }
}
