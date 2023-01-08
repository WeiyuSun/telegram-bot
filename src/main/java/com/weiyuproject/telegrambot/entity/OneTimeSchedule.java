package com.weiyuproject.telegrambot.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OneTimeSchedule extends Schedule {
    private LocalDateTime scheduleTime;
}
