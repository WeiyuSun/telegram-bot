package com.weiyuproject.telegrambot.service;

import com.weiyuproject.telegrambot.object.entity.AnniversaryEntity;
import com.weiyuproject.telegrambot.object.entity.OneTimeScheduleEntity;
import com.weiyuproject.telegrambot.object.entity.ScheduleEntity;
import com.weiyuproject.telegrambot.object.entity.WeeklyScheduleEntity;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleService {
    boolean addAnniversary(AnniversaryEntity anniversary);
    boolean addOneTimeSchedule(OneTimeScheduleEntity oneTimeSchedule);
    boolean addWeeklySchedule(WeeklyScheduleEntity weeklySchedule);

    boolean deleteWeeklySchedule(Long scheduleID);
    boolean deleteOneTimeSchedule(Long scheduleID);
    boolean deleteAnniversary(Long scheduleID);

    List<OneTimeScheduleEntity> getOnetimeSchedules(Long userID);
    List<WeeklyScheduleEntity> getWeeklySchedules(Long userID);
    List<AnniversaryEntity> getAnniversaries(Long userID);
    boolean addSchedule(ScheduleEntity schedule);
    List<ScheduleEntity> getAllSchedules(Long userID);
}
