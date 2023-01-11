package com.weiyuproject.telegrambot.dao;

import com.weiyuproject.telegrambot.object.entity.AnniversaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnniversaryDao extends JpaRepository<AnniversaryEntity, Long> {
    List<AnniversaryEntity> getAnniversariesByUserId(Long userID);
}
