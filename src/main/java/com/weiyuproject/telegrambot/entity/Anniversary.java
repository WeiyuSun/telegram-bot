package com.weiyuproject.telegrambot.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Anniversary extends Schedule {
    private LocalDate localDate;
}
