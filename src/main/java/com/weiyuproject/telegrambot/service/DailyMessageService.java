package com.weiyuproject.telegrambot.service;
import com.weiyuproject.telegrambot.object.entity.UserEntity;

import java.util.List;

public interface DailyMessageService {
     List<String> getDailyMessages(UserEntity user);
}
