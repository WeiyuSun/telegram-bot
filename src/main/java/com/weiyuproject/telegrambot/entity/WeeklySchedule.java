package com.weiyuproject.telegrambot.entity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class WeeklySchedule extends Schedule {
    private LocalDateTime scheduleTime;
    public WeeklySchedule(String name, LocalDateTime scheduleTime){
        super(name);
        this.scheduleTime = scheduleTime;
    }
}
