package com.weiyuproject.telegrambot.service;
import com.weiyuproject.telegrambot.object.entity.UserEntity;

import java.util.List;

public interface UserService {
    boolean removeUser(Long userID);
    boolean removeUser(UserEntity user);
    UserEntity getUser(Long userID);
    List<UserEntity> getUsersByTimeOffset(Integer timeOffset);
    boolean add(UserEntity User);
    boolean contains(Long id);
    //boolean addSchedule(Long userID, Schedule schedule);
    boolean setUserState(Long userId, Integer state);
    boolean updateUser(UserEntity User);
    void setWeatherService(Long userID, boolean enable);
    void setQuoteService(Long userID, boolean enable);
}
