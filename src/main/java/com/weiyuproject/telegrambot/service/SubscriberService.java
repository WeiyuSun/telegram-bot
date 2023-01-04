package com.weiyuproject.telegrambot.service;

import com.weiyuproject.telegrambot.entity.Subscriber;

import java.util.HashMap;
import java.util.Map;

public interface SubscriberService {
    Map<Long, Subscriber> getSubscriberList();

    boolean removeSubscriber(Long userID);
    boolean removeSubscriber(Subscriber user);

    Subscriber getSubscriber(Long userID);
    Subscriber getSubscriber(Subscriber subscriber);

    void add(Subscriber subscriber);

    boolean contains(Long id);
}
