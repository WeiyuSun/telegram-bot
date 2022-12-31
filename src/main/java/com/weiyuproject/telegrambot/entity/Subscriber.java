package com.weiyuproject.telegrambot.entity;

import lombok.Data;

@Data
public class Subscriber {
    private Long id;
    private String city;
    private Double longitude;
    private Double latitude;
    private String newsPreference;
    private static String dailyQuote;
}
