package com.weiyuproject.telegrambot;

import com.weiyuproject.telegrambot.object.dao.UserDao;
import com.weiyuproject.telegrambot.object.entity.UserEntity;
import com.weiyuproject.telegrambot.utils.UserStateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDtoDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void testUpdateByID(){
        UserEntity winnipeg = new UserEntity(121112L, "Winnipeg", 51.33, 65.12, -2177);
        UserEntity toronto = new UserEntity(786932L, "Toronto", 90.12, 61.12, -2177);
        userDao.save(winnipeg);
        userDao.save(toronto);

        System.out.println(userDao.getUsersByTimeOffset(-2177));

        UserEntity torontoChanged = new UserEntity(786932L, "Toronto", 99.99, 70.12, 1213);
        //subscriberDao.updateSubscriberById(torontoChanged);
        System.out.println("after changed: " + userDao.getUserByUserID(torontoChanged.getUserID()));

    }

    @Test
    public void testUpdateEnableByID(){
        UserEntity winnipeg = new UserEntity(121112L, "Winnipeg", 51.33, 65.12, -2177, false, false);
        UserEntity toronto = new UserEntity(786932L, "Toronto", 90.12, 61.12, -2177, false, false);
        userDao.save(winnipeg);
        userDao.save(toronto);

        userDao.updateEnableWeatherServiceById(121112L, true);
        userDao.updateEnableQuoteServiceById(786932L, true);

        System.out.println(userDao.getUserByUserID(121112L).getEnableWeatherService());
        System.out.println(userDao.getUserByUserID(786932L).getEnableQuoteService());
    }

    @Test
    public void testChangeState(){
        UserEntity winnipeg = new UserEntity(121112L, "Winnipeg", 51.33, 65.12, -2177, false, false);
        UserEntity toronto = new UserEntity(786932L, "Toronto", 90.12, 61.12, -2177, false, false);
        userDao.save(winnipeg);
        userDao.save(toronto);

        userDao.updateStateById(winnipeg.getUserID(), UserStateUtil.WAITING_SCHEDULE_NAME);
        System.out.println(userDao.getUserByUserID(winnipeg.getUserID()));
    }
}
