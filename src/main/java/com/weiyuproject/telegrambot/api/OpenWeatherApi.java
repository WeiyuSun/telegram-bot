package com.weiyuproject.telegrambot.api;

import com.alibaba.fastjson.JSONObject;
import com.weiyuproject.telegrambot.object.dto.WeatherDto;
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

    public WeatherDto getTodayWeather(Double longitude, Double latitude) {
        JSONObject result = restTemplate.getForObject(weatherUrl, JSONObject.class, latitude.toString(), longitude.toString());
        String detail = result.getJSONArray("weather").getJSONObject(0).getString("description");
        Integer minTemp = result.getJSONObject("main").getInteger("temp_min") - 273;
        Integer maxTemp = result.getJSONObject("main").getInteger("temp_max") - 273;
        Integer feelTemp = result.getJSONObject("main").getInteger("feels_like") - 273;

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setDetail(detail);
        weatherDto.setFeelTemperature(feelTemp);
        weatherDto.setMaxTemperature(maxTemp);
        weatherDto.setMinTemperature(minTemp);
        return weatherDto;
    }

    public String getCity(Double longitude, Double latitude) {
        JSONObject[] result = restTemplate.getForObject(cityUrl, JSONObject[].class, latitude, longitude);
        System.out.println(
                result[0]);
        return result[0].getString("name");
    }
}
