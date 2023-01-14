package com.weiyuproject.telegrambot.object.dao;
import com.weiyuproject.telegrambot.object.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<UserEntity, Long> {
    List<UserEntity> getUsersByTimeOffset(Integer timeOffset);

    UserEntity getUserByUserID(Long id);

    // TODO: build global exceptions handler
    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.user set city = :#{#user.getCity()}, time_offset = :#{#user.getTimeOffset()}, latitude = :#{#user.getLatitude()}, longitude = :#{#user.getLongitude()}, enable_quote_service = :#{#user.getEnableQuoteService()}, enable_weather_service = :#{#user.getEnableWeatherService()} WHERE userid = :#{#user.getUserID()}", nativeQuery = true)
    Integer updateUserById(@Param("user") UserEntity user) throws Exception;

    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.user set enable_weather_service = :#{#newValue} where userid = :#{#id}", nativeQuery = true)
    Integer updateEnableWeatherServiceById(@Param("id") Long id, @Param("newValue")boolean enableWeatherService);

    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.user set enable_quote_service = :#{#newValue} where userid = :#{#id}", nativeQuery = true)
    Integer updateEnableQuoteServiceById(@Param("id") Long id, @Param("newValue")boolean enableQuoteService);

    @Transactional
    @Modifying
    @Query(value = "UPDATE telegram_bot.user set user_state = :#{#state} where userid = :#{#userID}", nativeQuery = true)
    Integer updateStateById(@Param("userID") Long userID, @Param("state")Integer state);
}
