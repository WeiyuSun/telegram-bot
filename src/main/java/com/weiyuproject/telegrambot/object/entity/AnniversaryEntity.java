package com.weiyuproject.telegrambot.object.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "anniversary")
public class AnniversaryEntity {
    public AnniversaryEntity(){}
    public AnniversaryEntity(Long userId, String name, LocalDate date) {
        this.name = name;
        this.userId = userId;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
