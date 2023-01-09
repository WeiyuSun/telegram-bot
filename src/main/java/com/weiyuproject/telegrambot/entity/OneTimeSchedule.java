package com.weiyuproject.telegrambot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OneTimeSchedule extends Schedule {
    private LocalDateTime scheduleTime;

    public OneTimeSchedule(String name, LocalDateTime scheduleTime){
        super(name);
        this.scheduleTime = scheduleTime;
    }
}
