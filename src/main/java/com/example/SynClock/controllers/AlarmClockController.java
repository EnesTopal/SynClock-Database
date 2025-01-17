package com.example.SynClock.controllers;

import com.example.SynClock.model.DTOs.AlarmClockDTO;
import com.example.SynClock.model.DTOs.CreateAlarmClockDTO;
import com.example.SynClock.services.AlarmClockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alarm-clocks")
public class AlarmClockController {

    private final AlarmClockService alarmClockService;

    public AlarmClockController(AlarmClockService alarmClockService) {
        this.alarmClockService = alarmClockService;
    }

    @PostMapping
    public ResponseEntity<AlarmClockDTO> createAlarmClock(@RequestBody CreateAlarmClockDTO createAlarmClockDTO) {
        return alarmClockService.createAlarmClock(createAlarmClockDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlarmClock(@PathVariable("id") Long id) {
        return alarmClockService.deleteAlarmClock(id);
    }
}