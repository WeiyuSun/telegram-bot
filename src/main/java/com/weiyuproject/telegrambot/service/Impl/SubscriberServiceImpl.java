package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.object.dao.SubscriberDao;
import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;
import com.weiyuproject.telegrambot.service.SubscriberService;
import com.weiyuproject.telegrambot.utils.UserStateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    @Autowired
    private SubscriberDao subscriberDao;
    @Override
    public boolean removeSubscriber(Long userID) {
        subscriberDao.deleteById(userID);
        return false;
    }

    @Override
    public boolean removeSubscriber(SubscriberEntity user) {
        if(user == null || user.getUserID() == null)
            return false;

        return removeSubscriber(user.getUserID());
    }

    @Override
    public SubscriberEntity getSubscriber(Long userID) {
        return subscriberDao.getSubscriberByUserID(userID);
    }

    @Override
    public List<SubscriberEntity> getSubscribersByTimeOffset(Integer timeOffset) {
        return subscriberDao.getSubscribersByTimeOffset(timeOffset);
    }

    @Override
    public boolean add(SubscriberEntity subscriber) {
        subscriberDao.save(subscriber);
        return false;
    }


    @Override
    public boolean contains(Long id) {
        return subscriberDao.getSubscriberByUserID(id) != null;
    }

    @Override
    public boolean setUserState(Long userId, Integer state) {
        if (!isStateValid(state))
            return false;
        return subscriberDao.updateStateById(userId, state) > 0;
    }

    @Override
    public boolean updateSubscriber(SubscriberEntity subscriber) {
        Integer result = 0;
        try{
            result = subscriberDao.updateSubscriberById(subscriber);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return  result > 0;
    }

    @Override
    public void setWeatherService(Long useID, boolean enable) {
        subscriberDao.updateEnableWeatherServiceById(useID, enable);
    }

    @Override
    public void setQuoteService(Long userID, boolean enable) {
        subscriberDao.updateEnableQuoteServiceById(userID, enable);
    }

    private boolean isStateValid(Integer state) {
        return UserStateUtil.WAITING_EVENT_NAME == state || UserStateUtil.OK == state
                || UserStateUtil.WAITING_LOCATION_MESSAGE == state;
    }
}
