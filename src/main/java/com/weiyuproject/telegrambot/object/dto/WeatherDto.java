package com.weiyuproject.telegrambot.object.dto;

import lombok.Data;

@Data
public class WeatherDto {
    private String detail;
    private Integer minTemperature;
    private Integer maxTemperature;
    private Integer feelTemperature;
}
