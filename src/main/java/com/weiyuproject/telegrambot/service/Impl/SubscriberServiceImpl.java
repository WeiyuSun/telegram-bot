package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.entity.OneTimeSchedule;
import com.weiyuproject.telegrambot.entity.Subscriber;
import com.weiyuproject.telegrambot.service.SubscriberService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    private Map<Long, Subscriber> subscribers = new HashMap<>();


    @Override public Map<Long, Subscriber> getSubscriberList() {
        return subscribers;
    }

    @Override
    public boolean removeSubscriber(Long userID) {
        if(subscribers.containsKey(userID)){
            subscribers.remove(userID);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeSubscriber(Subscriber user) {
        if(user.getId() != null){
            return removeSubscriber(user.getId());
        }
        return false;
    }

    @Override public Subscriber getSubscriber(Long userID) {
        return subscribers.get(userID);
    }
    @Override
    public Subscriber getSubscriber(Subscriber subscriber) {
        return subscribers.get(subscriber.getId());
    }
    @Override public void add(Subscriber subscriber) {subscribers.put(subscriber.getId(), subscriber);}
    @Override public boolean contains(Long id) {
        return subscribers.containsKey(id);
    }

    @Override
    public boolean addOnetimeSchedule(Long userID, OneTimeSchedule oneTimeSchedule) {
        Subscriber subscriber = subscribers.get(userID);

        if(subscriber == null)
            return false;

        if(subscriber.getOneTimeEvents() == null){
            subscriber.setOneTimeEvents(new ArrayList<>());
        }

        subscriber.getOneTimeEvents().add(oneTimeSchedule);
        return true;
    }

    @Override
    public boolean setUserState(Long userId, Integer state) {
        Subscriber subscriber = subscribers.get(userId);

        if(subscriber == null)
            return false;

        subscriber.setUserState(state);
        return true;
    }
}
