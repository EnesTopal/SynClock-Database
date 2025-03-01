package com.example.SynClock.model.DTOs;

import lombok.Data;

@Data
public class CreateAlarmClockDTO {
    private String time;
    private Integer groupId;
}
