package com.weiyuproject.telegrambot.service;
import com.weiyuproject.telegrambot.object.entity.SubscriberEntity;

import java.util.List;

public interface DailyMessageService {
     List<String> getDailyMessages(SubscriberEntity subscriber);
}
