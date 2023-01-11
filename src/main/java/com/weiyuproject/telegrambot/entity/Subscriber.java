package com.weiyuproject.telegrambot.entity;
import lombok.Data;
import java.util.List;

@Data
public class Subscriber {
    private Long id;
    private String city;
    private Double longitude;
    private Double latitude;
    private Integer timeOffset;
    private boolean weatherService;
    private boolean quoteService;
    private boolean newsService;
    private boolean scheduleService;
    private Integer userState;
    private List<OneTimeSchedule> oneTimeEvents;
    private List<WeeklySchedule> weeklyEvents;
    private List<MonthlyEvent> monthlyEvents;
    private List<Anniversary> anniversaries;
}
