package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.entity.Weather;
import com.weiyuproject.telegrambot.service.DailyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyMessageServiceImpl implements DailyMessageService {
    @Autowired
    private OpenWeatherApi openWeatherApi;

    @Override
    public String getDailyMessage(Subscriber subscriber) {
        StringBuilder todayMessage = new StringBuilder();
        todayMessage.append("Good morning🌞\n");
        if (subscriber.isWeatherService()) {
            if (subscriber.getLatitude() != null && subscriber.getLongitude() != null) {
                Weather weather = openWeatherApi.getTodayWeather(subscriber.getLatitude(), subscriber.getLongitude());
                System.out.println(weather);
                String weatherInf = "Temperature: " + weather.getMinTemperature() + "°C ~ "  + weather.getMaxTemperature() + "°C" +
                        "\nApparent temperature: " + weather.getFeelTemperature() + "°C\nWeather: " + weather.getDetail() + "\n";
                todayMessage.append(weatherInf);
            } else {
                todayMessage.append("❗️You haven't share your location to me... I can't provide you the weather service\nShare your /location now to get weather service tomorrow");
            }
        }
        return todayMessage.toString();
    }
}
