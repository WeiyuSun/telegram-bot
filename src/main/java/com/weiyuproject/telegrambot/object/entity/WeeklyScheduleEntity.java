package com.weiyuproject.telegrambot.object.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "weekly_schedule")
public class WeeklyScheduleEntity {
    public WeeklyScheduleEntity() {
    }

    public WeeklyScheduleEntity(Long userId, String name, LocalDateTime timeMark) {
        this.name = name;
        this.userId = userId;
        this.timeMark = timeMark;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "timeMark", nullable = false)
    private LocalDateTime timeMark;
}
