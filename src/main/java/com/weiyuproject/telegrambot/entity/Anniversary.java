package com.weiyuproject.telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Anniversary extends Schedule {
    private LocalDate anniversaryDate;
    public Anniversary(String name, LocalDate anniversaryDate) {
        super(name);
        this.anniversaryDate = anniversaryDate;
    }
}
