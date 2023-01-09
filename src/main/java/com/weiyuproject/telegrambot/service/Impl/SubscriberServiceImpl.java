package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.entity.*;
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

    private boolean addOnetimeSchedule(Long userID, OneTimeSchedule oneTimeSchedule) {
        Subscriber subscriber = subscribers.get(userID);

        if(subscriber == null)
            return false;

        if(subscriber.getOneTimeEvents() == null){
            subscriber.setOneTimeEvents(new ArrayList<>());
        }

        subscriber.getOneTimeEvents().add(oneTimeSchedule);
        for(OneTimeSchedule oneTimeSchedule1: subscriber.getOneTimeEvents() ){
            System.out.println(oneTimeSchedule1.getName() + " " + oneTimeSchedule1.getScheduleTime());
        }
        return true;
    }

    private boolean addAnniversary(Long userID, Anniversary anniversary) {
        Subscriber subscriber = subscribers.get(userID);

        if(subscriber == null)
            return false;

        if(subscriber.getAnniversaries() == null)
            subscriber.setAnniversaries(new ArrayList<>());

        subscriber.getAnniversaries().add(anniversary);

        for(Anniversary anniversary1: subscriber.getAnniversaries() ){
            System.out.println(anniversary1.getName() + " " + anniversary1.getAnniversaryDate());
        }
        return true;
    }

    private boolean addWeeklySchedule(Long userID, WeeklySchedule weeklySchedule){
        Subscriber subscriber = subscribers.get(userID);

        if(subscriber == null)
            return false;

        if(subscriber.getWeeklyEvents() == null)
            subscriber.setWeeklyEvents(new ArrayList<>());

        subscriber.getWeeklyEvents().add(weeklySchedule);
        for(WeeklySchedule weeklySchedule1: subscriber.getWeeklyEvents() ){
            System.out.println(weeklySchedule1.getName() + " " + weeklySchedule1.getScheduleTime());
        }
        return true;
    }

    @Override
    public boolean addSchedule(Long userID, Schedule schedule) {
        if(schedule instanceof OneTimeSchedule)
            return addOnetimeSchedule(userID, (OneTimeSchedule) schedule);
        else if(schedule instanceof WeeklySchedule)
            return addWeeklySchedule(userID, (WeeklySchedule) schedule);
        else
            return addAnniversary(userID, (Anniversary) schedule);
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
