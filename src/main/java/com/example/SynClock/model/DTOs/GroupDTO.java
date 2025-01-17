package com.example.SynClock.model.DTOs;

import com.example.SynClock.model.AlarmClock;
import com.example.SynClock.model.Group;
import com.example.SynClock.model.User;
import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    private long uuid;
    private String groupName;
    private List<UserDTO> users;
    private List<AlarmClockDTO> alarmClocks;

    public GroupDTO(Group group) {
        this.uuid = group.getUuid();
        this.groupName = group.getGroupName();
        this.users = group.getUsers().stream()
                .map(UserDTO::new)
                .toList();
        this.alarmClocks = group.getAlarmClocks().stream()
                .map(AlarmClockDTO::new)
                .toList();
    }
}

