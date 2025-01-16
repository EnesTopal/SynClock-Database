package com.example.SynClock.model.DTOs;

import com.example.SynClock.model.Group;
import com.example.SynClock.model.User;
import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    private long uuid;
    private String groupName;
    private List<User> users;

    public GroupDTO(Group group) {
        this.uuid = group.getUuid();
        this.groupName = group.getGroupName();
        this.users = group.getUsers();
    }
}
