package com.example.SynClock.services;

import com.example.SynClock.model.ApiResponse;
import com.example.SynClock.model.DTOs.AlarmClockDTO;
import com.example.SynClock.repositories.AlarmClockRepository;
import com.example.SynClock.repositories.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.SynClock.model.AlarmClock;
import com.example.SynClock.model.Group;
import com.example.SynClock.model.DTOs.CreateAlarmClockDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AlarmClockService {

    private final AlarmClockRepository alarmClockRepository;
    private final GroupRepository groupRepository;

    public AlarmClockService(AlarmClockRepository alarmClockRepository, GroupRepository groupRepository) {
        this.alarmClockRepository = alarmClockRepository;
        this.groupRepository = groupRepository;
    }

    public ResponseEntity<ApiResponse<AlarmClockDTO>> createAlarmClock(CreateAlarmClockDTO createAlarmClockDTO) {
        Group group = groupRepository.findById(createAlarmClockDTO.getGroupId().longValue())
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + createAlarmClockDTO.getGroupId()));

        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setTime(createAlarmClockDTO.getTime());
        alarmClock.setGroup(group);
        AlarmClock savedAlarmClock = alarmClockRepository.save(alarmClock);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<AlarmClockDTO>("Alarm created", new AlarmClockDTO(savedAlarmClock)));
    }

    public ResponseEntity<ApiResponse<String>> deleteAlarmClock(Long uuid) {
        Optional<AlarmClock> alarmClockOptional = alarmClockRepository.findById(uuid);
        if (alarmClockOptional.isPresent()) {
            AlarmClock alarmClock = alarmClockOptional.get();
            alarmClockRepository.delete(alarmClock);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("Success: Alarm and associated data deleted successfully."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Alarm not found: No alarm matching the given ID was found."));
    }

    public ResponseEntity<ApiResponse<List<AlarmClockDTO>>> getAlarms(Long uuid){
        Optional<Group> groupOptional = groupRepository.findById(uuid);
        if (groupOptional.isPresent()){
            List<AlarmClock> alarms = alarmClockRepository.findByGroupUuid(uuid);
            List<AlarmClockDTO> alarmClockDTOS = alarms.stream()
                    .map(AlarmClockDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<List<AlarmClockDTO>>("Alarms got received",alarmClockDTOS));
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("There is an error occurred"));
        }
    }

}











