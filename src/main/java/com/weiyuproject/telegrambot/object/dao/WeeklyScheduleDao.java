package com.weiyuproject.telegrambot.object.dao;
import com.weiyuproject.telegrambot.object.entity.WeeklyScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyScheduleDao extends JpaRepository<WeeklyScheduleEntity, Long> {
    List<WeeklyScheduleEntity> getSchedulesByUserId(Long userID);
    void removeAllByUserId(Long userID);
}
