package com.weiyuproject.telegrambot.entity;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public abstract class Schedule {
    private String name;

    public Schedule(String name){
        this.name = name;
    }
}
