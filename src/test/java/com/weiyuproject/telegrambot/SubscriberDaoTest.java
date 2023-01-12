package com.weiyuproject.telegrambot;

import com.weiyuproject.telegrambot.object.dao.SubscriberDao;
import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;
import com.weiyuproject.telegrambot.utils.UserStateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubscriberDaoTest {
    @Autowired
    SubscriberDao subscriberDao;

    @Test
    public void testUpdateByID(){
        SubscriberEntity  winnipeg = new SubscriberEntity(121112L, "Winnipeg", 51.33, 65.12, -2177);
        SubscriberEntity toronto = new SubscriberEntity(786932L, "Toronto", 90.12, 61.12, -2177);
        subscriberDao.save(winnipeg);
        subscriberDao.save(toronto);

        System.out.println(subscriberDao.getSubscribersByTimeOffset(-2177));

        SubscriberEntity torontoChanged = new SubscriberEntity(786932L, "Toronto", 99.99, 70.12, 1213);
        //subscriberDao.updateSubscriberById(torontoChanged);
        System.out.println("after changed: " + subscriberDao.getSubscriberByUserID(torontoChanged.getUserID()));

    }

    @Test
    public void testUpdateEnableByID(){
        SubscriberEntity  winnipeg = new SubscriberEntity(121112L, "Winnipeg", 51.33, 65.12, -2177, false, false);
        SubscriberEntity toronto = new SubscriberEntity(786932L, "Toronto", 90.12, 61.12, -2177, false, false);
        subscriberDao.save(winnipeg);
        subscriberDao.save(toronto);

        subscriberDao.updateEnableWeatherServiceById(121112L, true);
        subscriberDao.updateEnableQuoteServiceById(786932L, true);

        System.out.println(subscriberDao.getSubscriberByUserID(121112L).getEnableWeatherService());
        System.out.println(subscriberDao.getSubscriberByUserID(786932L).getEnableQuoteService());
    }

    @Test
    public void testChangeState(){
        SubscriberEntity  winnipeg = new SubscriberEntity(121112L, "Winnipeg", 51.33, 65.12, -2177, false, false);
        SubscriberEntity toronto = new SubscriberEntity(786932L, "Toronto", 90.12, 61.12, -2177, false, false);
        subscriberDao.save(winnipeg);
        subscriberDao.save(toronto);

        subscriberDao.updateStateById(winnipeg.getUserID(), UserStateUtil.WAITING_EVENT_NAME);
        System.out.println(subscriberDao.getSubscriberByUserID(winnipeg.getUserID()));
    }
}
