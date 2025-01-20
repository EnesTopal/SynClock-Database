package com.example.SynClock.model.DTOs;

import com.example.SynClock.model.Group;
import com.example.SynClock.model.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private long uuid;
    private String username;
    private String password;
    private List<Long> myGroups;

    public UserDTO(User user) {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.password = user.getUserpassword();
        this.myGroups = user.getGroups().stream()
                .map(group -> Long.valueOf(group.getUuid())).collect(Collectors.toList());

    }

}

