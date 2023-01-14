package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.object.dao.UserDao;
import com.weiyuproject.telegrambot.object.entity.UserEntity;
import com.weiyuproject.telegrambot.service.UserService;
import com.weiyuproject.telegrambot.utils.UserStateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserDao userDao;
    @Override
    public boolean removeUser(Long userID) {
        userDao.deleteById(userID);
        return false;
    }

    @Override
    public boolean removeUser(UserEntity user) {
        if(user == null || user.getUserID() == null)
            return false;

        return removeUser(user.getUserID());
    }

    @Override
    public UserEntity getUser(Long userID) {
        return userDao.getUserByUserID(userID);
    }

    @Override
    public List<UserEntity> getUsersByTimeOffset(Integer timeOffset) {
        return userDao.getUsersByTimeOffset(timeOffset);
    }

    @Override
    public boolean add(UserEntity User) {
        userDao.save(User);
        return false;
    }


    @Override
    public boolean contains(Long id) {
        return userDao.getUserByUserID(id) != null;
    }

    @Override
    public boolean setUserState(Long userId, Integer state) {
        if (!UserStateUtil.isStateValid(state))
            return false;
        return userDao.updateStateById(userId, state) > 0;
    }

    @Override
    public boolean updateUser(UserEntity User) {
        Integer result = 0;
        try{
            result = userDao.updateUserById(User);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return  result > 0;
    }

    @Override
    public void setWeatherService(Long useID, boolean enable) {
        userDao.updateEnableWeatherServiceById(useID, enable);
    }

    @Override
    public void setQuoteService(Long userID, boolean enable) {
        userDao.updateEnableQuoteServiceById(userID, enable);
    }
}
