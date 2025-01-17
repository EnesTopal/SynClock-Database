package com.example.SynClock.services;

import com.example.SynClock.model.DTOs.AlarmClockDTO;
import com.example.SynClock.repositories.AlarmClockRepository;
import com.example.SynClock.repositories.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.SynClock.model.AlarmClock;
import com.example.SynClock.model.Group;
import com.example.SynClock.model.DTOs.CreateAlarmClockDTO;

import java.util.Optional;


@Service
public class AlarmClockService {

    private final AlarmClockRepository alarmClockRepository;
    private final GroupRepository groupRepository;

    public AlarmClockService(AlarmClockRepository alarmClockRepository, GroupRepository groupRepository) {
        this.alarmClockRepository = alarmClockRepository;
        this.groupRepository = groupRepository;
    }

    public ResponseEntity<AlarmClockDTO> createAlarmClock(CreateAlarmClockDTO createAlarmClockDTO) {
        Group group = groupRepository.findById(createAlarmClockDTO.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + createAlarmClockDTO.getGroupId()));

        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setTime(createAlarmClockDTO.getTime());
        alarmClock.setGroup(group);
        AlarmClock savedAlarmClock = alarmClockRepository.save(alarmClock);
//        group.getAlarmClocks().add(savedAlarmClock);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AlarmClockDTO(savedAlarmClock));
    }

    public ResponseEntity<String> deleteAlarmClock(Long uuid){
        Optional<AlarmClock> alarmClockOptional = alarmClockRepository.findById(uuid);
        if (alarmClockOptional.isPresent()){
            AlarmClock alarmClock = alarmClockOptional.get();
            alarmClockRepository.delete(alarmClock);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Success: Alarm and associated data deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Alarm not found: No alarm matching the given ID was found.");
    }

}
