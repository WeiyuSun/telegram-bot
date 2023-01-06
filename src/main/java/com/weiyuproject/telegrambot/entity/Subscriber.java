package com.weiyuproject.telegrambot.entity;

import lombok.Data;

import java.util.List;

@Data
public class Subscriber {
    private Long id;
    private String city;
    private Double longitude;
    private Double latitude;
    private String newsPreference;
    private static String dailyQuote;
    private Integer timeOffset;

    private boolean weatherService;
    private boolean quoteService;
    private boolean newsService;
    private boolean scheduleService;

    private Integer userState;

    private List<OneTimeEvent> oneTimeEvents;
    private List<WeeklyEvent> weeklyEvents;
    private List<MonthlyEvent> monthlyEvents;
    private List<Anniversary> anniversaries;
}
