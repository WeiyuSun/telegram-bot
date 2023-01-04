package com.weiyuproject.telegrambot.entity;


import lombok.Data;

import java.util.Date;

@Data
public abstract class Event {
    private Date startDay;
    private Date endDay;
    private String detail;
    private Date eventStartTime;
    private Date eventEndTime;
}
