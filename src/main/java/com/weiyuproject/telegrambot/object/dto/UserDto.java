package com.weiyuproject.telegrambot.object.dto;
import com.weiyuproject.telegrambot.object.entity.AnniversaryEntity;
import com.weiyuproject.telegrambot.object.entity.OneTimeScheduleEntity;
import com.weiyuproject.telegrambot.object.entity.WeeklyScheduleEntity;
import lombok.Data;
import java.util.List;

@Data
public class UserDto {
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
    private List<OneTimeScheduleEntity> oneTimeEvents;
    private List<WeeklyScheduleEntity> weeklyEvents;
    private List<AnniversaryEntity> anniversaries;
}
