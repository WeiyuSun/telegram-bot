package com.weiyuproject.telegrambot.service.Impl;
import com.weiyuproject.telegrambot.api.TianxingApi;
import com.weiyuproject.telegrambot.object.entity.UserEntity;
import com.weiyuproject.telegrambot.service.AutoTaskService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoTaskServiceImpl implements AutoTaskService {
    @Autowired private UserService userService;
    @Autowired private ReceiveAndSendService receiveAndSendService;
    @Autowired private TianxingApi tianxingApi;
    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT")
    public void utcTimeTask() {
        tianxingApi.getQuoteFromUrl();
        List<UserEntity> userList = userService.getUsersByTimeOffset(0);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+1")
    public void utcTimePlus1Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(1);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+2")
    public void utcTimePlus2Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(2);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+3")
    public void utcTimePlus3Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(3);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+4")
    public void utcTimePlus4Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(4);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+5")
    public void utcTimePlus5Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(5);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+6")
    public void utcTimePlus6Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(6);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+7")
    public void utcTimePlus7Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(7);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+8")
    public void utcTimePlus8Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(8);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }


    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+9")
    public void utcTimePlus9Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(9);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+10")
    public void utcTimePlus10Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(10);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+11")
    public void utcTimePlus11Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(11);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+12")
    public void utcTimePlus12Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(12);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-1")
    public void utcTimeSub1Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-1);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-2")
    public void utcTimeSub2Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-2);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-3")
    public void utcTimeSub3Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-3);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-4")
    public void utcTimeSub4Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-4);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-5")
    public void utcTimeSub5Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-5);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-6")
    public void utcTimeSub6Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-6);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-7")
    public void utcTimeSub7Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-7);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-8")
    public void utcTimeSub8Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-8);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-9")
    public void utcTimeSub9Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-9);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-10")
    public void utcTimeSub10Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-10);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-12")
    public void utcTimeSub12Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-12);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-11")
    public void utcTimeSub11Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-11);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-13")
    public void utcTimeSub13Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-13);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-14")
    public void utcTimeSub14Task() {
        List<UserEntity> userList = userService.getUsersByTimeOffset(-14);
        receiveAndSendService.sendDailyMessageToTelegram(userList);
    }
}
