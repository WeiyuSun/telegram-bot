package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.api.OpenWeatherApi;
import com.weiyuproject.telegrambot.api.TianxingApi;
import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.entity.Weather;
import com.weiyuproject.telegrambot.service.DailyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DailyMessageServiceImpl implements DailyMessageService {

    @Autowired
    private OpenWeatherApi openWeatherApi;
    @Autowired
    private TianxingApi tianxingApi;

    @Override
    public List<String> getDailyMessages(Subscriber subscriber) {

        List<String> todayMessages = new ArrayList<>();
        String quote = tianxingApi.getTodayQuote();
        todayMessages.add("\"Good morning🌞");
        if (subscriber.isWeatherService()) {
            if (subscriber.getLatitude() != null && subscriber.getLongitude() != null) {
                Weather weather = openWeatherApi.getTodayWeather(subscriber.getLatitude(), subscriber.getLongitude());
                System.out.println(weather);
                String weatherInf = "Temperature: " + weather.getMinTemperature() + "°C ~ " + weather.getMaxTemperature() + "°C" +
                        "\nApparent temperature: " + weather.getFeelTemperature() + "°C\nWeather: " + weather.getDetail() + "\n";
                todayMessages.add(weatherInf);
            } else {
                todayMessages.add("❗️You haven't share your location to me... I can't provide you the weather service\nShare your /location now to get weather service tomorrow");
            }
        }

        if (subscriber.isQuoteService())
            todayMessages.add(quote);


        return todayMessages;
    }
}
