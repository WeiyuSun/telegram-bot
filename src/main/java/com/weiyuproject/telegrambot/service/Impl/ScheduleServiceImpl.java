package com.weiyuproject.telegrambot.service.Impl;

import com.weiyuproject.telegrambot.object.dao.AnniversaryDao;
import com.weiyuproject.telegrambot.object.dao.OneTimeScheduleDao;
import com.weiyuproject.telegrambot.object.dao.WeeklyScheduleDao;
import com.weiyuproject.telegrambot.object.entity.AnniversaryEntity;
import com.weiyuproject.telegrambot.object.entity.OneTimeScheduleEntity;
import com.weiyuproject.telegrambot.object.entity.ScheduleEntity;
import com.weiyuproject.telegrambot.object.entity.WeeklyScheduleEntity;
import com.weiyuproject.telegrambot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired private AnniversaryDao anniversaryDao;
    @Autowired private OneTimeScheduleDao oneTimeScheduleDao;
    @Autowired private WeeklyScheduleDao weeklyScheduleDao;

    @Override
    public boolean addAnniversary(AnniversaryEntity anniversary) {
        anniversaryDao.save(anniversary);
        return true;
    }

    @Override
    public boolean addOneTimeSchedule(OneTimeScheduleEntity oneTimeSchedule) {
        oneTimeScheduleDao.save(oneTimeSchedule);
        return true;
    }

    @Override
    public boolean addWeeklySchedule(WeeklyScheduleEntity weeklySchedule) {
        weeklyScheduleDao.save(weeklySchedule);
        return true;
    }

    @Override
    public boolean deleteWeeklySchedule(Long scheduleID) {
        weeklyScheduleDao.deleteById(scheduleID);
        return true;
    }

    @Override
    public boolean deleteOneTimeSchedule(Long scheduleID) {
        oneTimeScheduleDao.deleteById(scheduleID);
        return true;
    }

    @Override
    public boolean deleteAnniversary(Long scheduleID) {
        anniversaryDao.deleteById(scheduleID);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteOneTimeSchedules(List<Long> ids){
        oneTimeScheduleDao.deleteOneTimeScheduleEntitiesByIdIn(ids);
        return true;
    }

    @Override
    public List<OneTimeScheduleEntity> getOnetimeSchedules(Long userID) {
        return oneTimeScheduleDao.getSchedulesByUserId(userID);
    }

    @Override
    public List<WeeklyScheduleEntity> getWeeklySchedules(Long userID) {
        return weeklyScheduleDao.getSchedulesByUserId(userID);
    }

    @Override
    public List<AnniversaryEntity> getAnniversaries(Long userID) {
        return anniversaryDao.getAnniversariesByUserId(userID);
    }

    public boolean addSchedule(ScheduleEntity schedule) {
        if (schedule instanceof OneTimeScheduleEntity) {
            return addOneTimeSchedule((OneTimeScheduleEntity) schedule);
        } else if (schedule instanceof AnniversaryEntity) {
            return addAnniversary((AnniversaryEntity) schedule);
        } else if (schedule instanceof WeeklyScheduleEntity) {
            return addWeeklySchedule((WeeklyScheduleEntity) schedule);
        }

        return false;
    }

    @Override
    @Transactional
    public List<ScheduleEntity> getAllSchedules(Long userID) {
        List<ScheduleEntity> allSchedules = new ArrayList<>();
        List<OneTimeScheduleEntity> oneTimeScheduleEntities = getOnetimeSchedules(userID);
        List<WeeklyScheduleEntity> weeklyScheduleEntities = getWeeklySchedules(userID);
        List<AnniversaryEntity> anniversaryEntities = getAnniversaries(userID);


        //String message = oneTimeSchedule.getName() + " at " + oneTimeSchedule.getTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        allSchedules.addAll(oneTimeScheduleEntities);
        allSchedules.addAll(weeklyScheduleEntities);
        allSchedules.addAll(anniversaryEntities);

        return allSchedules;
    }

    @Override
    @Transactional
    public boolean deleteAllUserSchedule(Long userID){
        oneTimeScheduleDao.removeAllByUserId(userID);
        weeklyScheduleDao.removeAllByUserId(userID);
        anniversaryDao.removeAllByUserId(userID);
        return true;
    }
}
