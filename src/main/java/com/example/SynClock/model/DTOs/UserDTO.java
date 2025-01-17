package com.example.SynClock.model.DTOs;

import com.example.SynClock.model.Group;
import com.example.SynClock.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private long uuid;
    private String username;

    public UserDTO(User user) {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
    }
}

