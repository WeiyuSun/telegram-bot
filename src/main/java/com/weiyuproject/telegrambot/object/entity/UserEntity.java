package com.weiyuproject.telegrambot.object.entity;

import com.weiyuproject.telegrambot.utils.UserStateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    public UserEntity(Long userID, String city, Double longitude, Double latitude, Integer timeOffset, Boolean enableWeatherService, Boolean enableQuoteService) {
        this.userID = userID;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timeOffset = timeOffset;
        this.enableWeatherService = enableWeatherService;
        this.enableQuoteService = enableQuoteService;
        userState = UserStateUtil.OK;
    }

    public UserEntity(Long userID, String city, Double longitude, Double latitude, Integer timeOffset, Boolean enableWeatherService, Boolean enableQuoteService, Integer userState) {
        this.userID = userID;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timeOffset = timeOffset;
        this.enableWeatherService = enableWeatherService;
        this.enableQuoteService = enableQuoteService;
        this.userState = userState;
    }

    public UserEntity(Long userID, String city, Double longitude, Double latitude, Integer timeOffset) {
        this.userID = userID;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timeOffset = timeOffset;
        this.enableWeatherService = false;
        this.enableQuoteService = false;
        this.userState = UserStateUtil.OK;
    }

    @Id
    @Column(name = "userid")
    private Long userID;
    @Column(nullable = false, length = 50, name = "city")
    private String city;
    @Column(nullable = false, name = "longitude")
    private Double longitude;
    @Column(nullable = false, name = "latitude")
    private Double latitude;
    @Column(nullable = false, name = "time_offset")
    private Integer timeOffset;
    @Column(nullable = false, name = "enable_weather_service")
    private Boolean enableWeatherService;
    @Column(nullable = false, name = "enable_quote_service")
    private Boolean enableQuoteService;
    @Column(nullable = false, name = "user_state")
    private Integer userState;

    public UserEntity() {
    }
}
