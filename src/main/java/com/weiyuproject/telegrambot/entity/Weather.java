package com.weiyuproject.telegrambot.entity;

import lombok.Data;

@Data
public class Weather {
    private String detail;
    private Integer minTemperature;
    private Integer maxTemperature;
    private Integer feelTemperature;
}
