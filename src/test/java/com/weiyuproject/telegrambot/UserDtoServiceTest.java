package com.weiyuproject.telegrambot;

import com.weiyuproject.telegrambot.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDtoServiceTest {
    @Autowired
    private ScheduleService scheduleService;
    @Test
    public void testGetAll(){
    }
}