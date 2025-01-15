package com.example.SynClock.model;

import java.util.List;

public class GroupDTO {
    private long uuid;
    private String groupName; // Grup ismi
    private List<UserDTO> users; // Grubun kullanıcıları

    // Getter ve Setter metodları
    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
