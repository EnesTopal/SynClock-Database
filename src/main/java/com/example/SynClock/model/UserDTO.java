package com.example.SynClock.model;

import java.util.List;

public class UserDTO {
    private long uuid;
    private String username;
    private List<GroupDTO> groups; // Kullanıcının grupları

    // Getter ve Setter metodları
    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }
}

