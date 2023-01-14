package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.api.TianxingApi;
import com.weiyuproject.telegrambot.object.dto.WeatherDto;
import com.weiyuproject.telegrambot.object.entity.*;
import com.weiyuproject.telegrambot.service.DailyMessageService;
import com.weiyuproject.telegrambot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyMessageServiceImpl implements DailyMessageService {

    @Autowired private OpenWeatherApi openWeatherApi;
    @Autowired private TianxingApi tianxingApi;
    @Autowired private ScheduleService scheduleService;

    @Override
    public List<String> getDailyMessages(UserEntity user) {
        LocalDateTime today = LocalDateTime.now().plusHours(user.getTimeOffset());

        List<String> todayMessages = new ArrayList<>();
        String quote = tianxingApi.getTodayQuote();
        todayMessages.add("\uD83D\uDD06Good morning^-^");
        todayMessages.add("Your are in " + user.getCity());
        if (user.getEnableWeatherService()) {
            WeatherDto weatherDto = openWeatherApi.getTodayWeather(user.getLongitude(), user.getLatitude());

            String weatherInf = "Temperature: " + weatherDto.getMinTemperature() + "°C ~ " + weatherDto.getMaxTemperature() + "°C" +
                    "\nApparent temperature: " + weatherDto.getFeelTemperature() + "°C\nWeather: " + weatherDto.getDetail() + "\n";
            todayMessages.add(weatherInf);
        }


        if (user.getEnableQuoteService())
            todayMessages.add(quote);

        List<AnniversaryEntity> anniversaryList = scheduleService.getAnniversaries(user.getUserID());
        List<WeeklyScheduleEntity> weeklyScheduleList = scheduleService.getWeeklySchedules(user.getUserID());
        List<OneTimeScheduleEntity> oneTimeScheduleList = scheduleService.getOnetimeSchedules(user.getUserID());

        List<String[]> scheduleMessage = new ArrayList<>();

        for (WeeklyScheduleEntity weeklySchedule : weeklyScheduleList) {
            if (weeklySchedule.getTimeMark().getDayOfWeek().getValue() == today.getDayOfWeek().getValue()) {
                scheduleMessage.add(new String[]{weeklySchedule.getName(), weeklySchedule.getTimeMark().getHour() + ":" + weeklySchedule.getTimeMark().getMinute()});
            }
        }

        for (OneTimeScheduleEntity oneTimeSchedule : oneTimeScheduleList) {
            if (oneTimeSchedule.getTime().getMonthValue() == today.getMonthValue() &&
                    oneTimeSchedule.getTime().getDayOfMonth() == today.getDayOfMonth() &&
                    oneTimeSchedule.getTime().getYear() == today.getYear()) {
                scheduleMessage.add(new String[]{oneTimeSchedule.getName(), oneTimeSchedule.getTime().getHour() + ":" + oneTimeSchedule.getTime().getMinute()});
            }
        }

        if(scheduleMessage.size() > 0){
            todayMessages.add("\uD83E\uDD3AToday's schedule:");
            scheduleMessage.sort((o1, o2) -> {
                String[] o1Time = o1[1].split(":");
                String[] o2Time = o2[1].split(":");

                if(Integer.parseInt(o1Time[0]) != Integer.parseInt(o2Time[0])){
                    return Integer.parseInt(o1Time[0]) - Integer.parseInt(o2Time[0]);
                }else {
                    return Integer.parseInt(o1Time[1]) - Integer.parseInt(o2Time[1]);
                }
            });
            StringBuilder scheduleDetail = new StringBuilder();

            for(String[] schedule: scheduleMessage){
                scheduleDetail.append(schedule[0]).append(" at ").append(schedule[1]).append("\n");
            }

            todayMessages.add(scheduleDetail.toString());
        }


        return todayMessages;
    }
}
