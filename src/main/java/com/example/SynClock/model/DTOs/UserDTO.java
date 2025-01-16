package com.example.SynClock.model.DTOs;

import com.example.SynClock.model.Group;
import com.example.SynClock.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private long uuid;
    private String username;
    private String userpassword;
    private List<Group> groups;

    public UserDTO(User user) {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.userpassword = user.getUserpassword();
        this.groups = user.getGroups();
    }

}

