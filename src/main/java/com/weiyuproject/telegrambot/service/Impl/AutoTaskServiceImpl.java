package com.weiyuproject.telegrambot.service.Impl;
import com.weiyuproject.telegrambot.service.AutoTaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.ZoneId;

@Service
public class AutoTaskServiceImpl implements AutoTaskService {
    @Override
//    @Scheduled()
//    @PostConstruct
    public void utcTimeTask() {
        for(String zoneId: ZoneId.getAvailableZoneIds()){
            System.out.println(zoneId);
        }
    }


}
