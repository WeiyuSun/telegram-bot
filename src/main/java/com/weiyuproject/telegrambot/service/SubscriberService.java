package com.weiyuproject.telegrambot.service;
import com.weiyuproject.telegrambot.object.dto.Subscriber;
import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface SubscriberService {
    boolean removeSubscriber(Long userID);
    boolean removeSubscriber(SubscriberEntity user);
    SubscriberEntity getSubscriber(Long userID);
    List<SubscriberEntity> getSubscribersByTimeOffset(Integer timeOffset);
    boolean add(SubscriberEntity subscriber);
    boolean contains(Long id);
    //boolean addSchedule(Long userID, Schedule schedule);
    boolean setUserState(Long userId, Integer state);
    boolean updateSubscriber(SubscriberEntity subscriber);
    void setWeatherService(Long userID, boolean enable);
    void setQuoteService(Long userID, boolean enable);
}
