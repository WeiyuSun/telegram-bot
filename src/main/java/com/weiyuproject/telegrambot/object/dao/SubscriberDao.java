package com.weiyuproject.telegrambot.object.dao;
import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface SubscriberDao extends JpaRepository<SubscriberEntity, Long> {
    List<SubscriberEntity> getSubscribersByTimeOffset(Integer timeOffset);

    SubscriberEntity getSubscriberByUserID(Long id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.subscriber set city = :#{#subscriber.getCity()}, time_offset = :#{#subscriber.getTimeOffset()}, latitude = :#{#subscriber.getLatitude()}, longitude = :#{#subscriber.getLongitude()}, enable_quote_service = :#{#subscriber.getEnableQuoteService()}, enable_weather_service = :#{#subscriber.getEnableWeatherService()} WHERE userid = :#{#subscriber.getUserID()}", nativeQuery = true)
    Integer updateSubscriberById(@Param("subscriber") SubscriberEntity subscriber) throws Exception;

    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.subscriber set enable_weather_service = :#{#newValue} where userid = :#{#id}", nativeQuery = true)
    Integer updateEnableWeatherServiceById(@Param("id") Long id, @Param("newValue")boolean enableWeatherService);

    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.subscriber set enable_quote_service = :#{#newValue} where userid = :#{#id}", nativeQuery = true)
    Integer updateEnableQuoteServiceById(@Param("id") Long id, @Param("newValue")boolean enableQuoteService);

    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.subscriber set user_state = :#{#state} where userid = :#{#userID}", nativeQuery = true)
    Integer updateStateById(@Param("userID") Long userID, @Param("state")Integer state);
}
