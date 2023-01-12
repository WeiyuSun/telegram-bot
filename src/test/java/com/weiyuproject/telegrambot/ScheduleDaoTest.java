package com.weiyuproject.telegrambot;

import com.weiyuproject.telegrambot.object.dao.AnniversaryDao;
import com.weiyuproject.telegrambot.object.dao.OneTimeScheduleDao;
import com.weiyuproject.telegrambot.object.dao.WeeklyScheduleDao;
import com.weiyuproject.telegrambot.object.entity.AnniversaryEntity;
import com.weiyuproject.telegrambot.object.entity.OneTimeScheduleEntity;
import com.weiyuproject.telegrambot.object.entity.WeeklyScheduleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class ScheduleDaoTest {
    @Autowired private AnniversaryDao anniversaryDao;
    @Autowired private OneTimeScheduleDao oneTimeScheduleDao;
    @Autowired private WeeklyScheduleDao weeklyScheduleDao;

    @Test
    public void testSaveAndDelete(){
        AnniversaryEntity anniversary = new AnniversaryEntity(123123L, "this is anniversary", LocalDate.now());
        OneTimeScheduleEntity oneTimeSchedule = new OneTimeScheduleEntity(123123L, "this is one time", LocalDateTime.now());
        WeeklyScheduleEntity weeklySchedule = new WeeklyScheduleEntity(123123L, "this is weekly time", LocalDateTime.now());

        AnniversaryEntity anniversary1 = new AnniversaryEntity(123123L, "this is anniversary", LocalDate.now());
        OneTimeScheduleEntity oneTimeSchedule1 = new OneTimeScheduleEntity(123123L, "this is one time", LocalDateTime.now());
        WeeklyScheduleEntity weeklySchedule1 = new WeeklyScheduleEntity(123123L, "this is weekly time", LocalDateTime.now());
        anniversaryDao.save(anniversary);
        oneTimeScheduleDao.save(oneTimeSchedule);
        weeklyScheduleDao.save(weeklySchedule);
        anniversaryDao.save(anniversary1);
        oneTimeScheduleDao.save(oneTimeSchedule1);
        weeklyScheduleDao.save(weeklySchedule1);

        System.out.println(anniversaryDao.getAnniversariesByUserId(123123L));
        System.out.println(oneTimeScheduleDao.getSchedulesByUserId(123123L));
        System.out.println(weeklyScheduleDao.getSchedulesByUserId(123123L));

        anniversaryDao.deleteById(1L);
        anniversaryDao.deleteById(2L);
        weeklyScheduleDao.deleteById(1L);
        weeklyScheduleDao.deleteById(2L);
        oneTimeScheduleDao.deleteById(1L);
        oneTimeScheduleDao.deleteById(2L);

        System.out.println(anniversaryDao.getAnniversariesByUserId(123123L));
        System.out.println(oneTimeScheduleDao.getSchedulesByUserId(123123L));
        System.out.println(weeklyScheduleDao.getSchedulesByUserId(123123L));
    }
}
