package com.weiyuproject.telegrambot.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TianxingApi {
    @Value("${tianxing.config.quoteUrl}")
    private String quoteUrl;
    private String todayQuote;
    @Autowired
    private RestTemplate restTemplate;

    public String getQuoteFromUrl(){
        JSONObject result = restTemplate.getForObject(quoteUrl, JSONObject.class);
        todayQuote = result.getJSONObject("result").getString("content");
        return todayQuote;
    }

    public String getTodayQuote(){
        if(todayQuote == null)
            return getQuoteFromUrl();
        return todayQuote;
    }
}
