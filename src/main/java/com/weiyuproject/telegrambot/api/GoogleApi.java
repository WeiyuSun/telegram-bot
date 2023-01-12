package com.weiyuproject.telegrambot.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleApi {
    @Value("${google.config.timezoneUrl}")
    private String getTimeOffsetUrl;
    @Autowired
    private RestTemplate restTemplate;

    public Integer getTimeOffset(Double longitude, Double latitude){
        System.out.println("from getTimeOffset: " + latitude + " " + longitude);
        JSONObject result = restTemplate.getForObject(getTimeOffsetUrl, JSONObject.class, latitude, longitude, "0");
        System.out.println("google api result " + result);

        if(result == null)
            return null;

        Integer timeOffsetInSeconds = result.getInteger("rawOffset");

        if(timeOffsetInSeconds == null)
            return null;

        return timeOffsetInSeconds / 3600;
    }
}
