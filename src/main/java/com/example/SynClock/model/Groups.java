package com.example.SynClock.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user_groups")
@Data
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private long uuid;

    @Column(name = "group_name", nullable = false)
    private String groupName; // Grup için bir isim ekleyebilirsiniz.

    @ManyToMany(mappedBy = "groups")
    private List<User> users;
}

