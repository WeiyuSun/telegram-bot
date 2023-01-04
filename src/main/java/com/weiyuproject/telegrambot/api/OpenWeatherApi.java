package com.weiyuproject.telegrambot.api;

import com.alibaba.fastjson.JSONObject;
import com.weiyuproject.telegrambot.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherApi {
    @Value("${openWeather.config.weatherUrl}")
    private String weatherUrl;
    @Value("${openWeather.config.cityUrl}")
    private String cityUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Weather getTodayWeather(Double latitude, Double longitude) {
        JSONObject result = restTemplate.getForObject(weatherUrl, JSONObject.class, latitude.toString(), longitude.toString());
        String detail = result.getJSONArray("weather").getJSONObject(0).getString("description");
        Integer minTemp = result.getJSONObject("main").getInteger("temp_min") - 273;
        Integer maxTemp = result.getJSONObject("main").getInteger("temp_max") - 273;
        Integer feelTemp = result.getJSONObject("main").getInteger("feels_like") - 273;

        Weather weather = new Weather();
        weather.setDetail(detail);
        weather.setFeelTemperature(feelTemp);
        weather.setMaxTemperature(maxTemp);
        weather.setMinTemperature(minTemp);
        return weather;
    }

    public String getCity(Double latitude, Double longitude) {
        JSONObject[] result = restTemplate.getForObject(cityUrl, JSONObject[].class, latitude, longitude);
        System.out.println(
                result[0]);
        return result[0].getString("name");
    }
}
