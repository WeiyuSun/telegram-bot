package com.weiyuproject.telegrambot.service.Impl;
import com.weiyuproject.telegrambot.api.TianxingApi;
import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;
import com.weiyuproject.telegrambot.service.AutoTaskService;
import com.weiyuproject.telegrambot.service.ReceiveAndSendService;
import com.weiyuproject.telegrambot.service.SubscriberService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.util.List;

@Service
public class AutoTaskServiceImpl implements AutoTaskService {
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private ReceiveAndSendService receiveAndSendService;

    @Autowired
    private TianxingApi tianxingApi;
    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT")
    public void utcTimeTask() {
        tianxingApi.getQuoteFromUrl();
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(0);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+1")
    public void utcTimePlus1Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(1);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+2")
    public void utcTimePlus2Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(2);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+3")
    public void utcTimePlus3Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(3);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+4")
    public void utcTimePlus4Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(4);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+5")
    public void utcTimePlus5Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(5);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+6")
    public void utcTimePlus6Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(6);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+7")
    public void utcTimePlus7Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(7);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+8")
    public void utcTimePlus8Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(8);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }


    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+9")
    public void utcTimePlus9Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(9);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+10")
    public void utcTimePlus10Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(10);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+11")
    public void utcTimePlus11Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(11);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT+12")
    public void utcTimePlus12Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(12);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-1")
    public void utcTimeSub1Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-1);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-2")
    public void utcTimeSub2Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-2);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-3")
    public void utcTimeSub3Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-3);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-4")
    public void utcTimeSub4Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-4);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-5")
    public void utcTimeSub5Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-5);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-6")
    public void utcTimeSub6Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-6);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-7")
    public void utcTimeSub7Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-7);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-8")
    public void utcTimeSub8Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-8);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-9")
    public void utcTimeSub9Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-9);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-10")
    public void utcTimeSub10Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-10);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-12")
    public void utcTimeSub12Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-12);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-11")
    public void utcTimeSub11Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-11);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-13")
    public void utcTimeSub13Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-13);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?", zone = "Etc/GMT-14")
    public void utcTimeSub14Task() {
        List<SubscriberEntity> subscriberList = subscriberService.getSubscribersByTimeOffset(-14);
        receiveAndSendService.sendDailyMessageToTelegram(subscriberList);
    }
}
