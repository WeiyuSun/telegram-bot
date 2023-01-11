package com.weiyuproject.telegrambot.dao;

import com.weiyuproject.telegrambot.object.entity.OneTimeScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OneTimeScheduleDao extends JpaRepository<OneTimeScheduleEntity, Long> {
    List<OneTimeScheduleEntity> getSchedulesByUserId(Long userID);
}
