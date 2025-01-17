package com.example.SynClock.model.DTOs;

import com.example.SynClock.model.AlarmClock;
import lombok.Data;

@Data
public class AlarmClockDTO {
    private Long uuid;
    private String time;

    public AlarmClockDTO(AlarmClock alarmClock) {
        this.uuid = alarmClock.getUuid();
        this.time = alarmClock.getTime();
    }
}