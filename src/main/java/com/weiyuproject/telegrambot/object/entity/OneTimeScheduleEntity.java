package com.weiyuproject.telegrambot.object.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "onetime_schedule")
public class OneTimeScheduleEntity extends ScheduleEntity {
    public OneTimeScheduleEntity() {
    }

    public OneTimeScheduleEntity(Long userId, String name, LocalDateTime time) {
        this.name = name;
        this.userId = userId;
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;
}
