package com.example.SynClock.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private long uuid;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String userpassword;

    @ManyToMany
    @JoinTable(
            name = "user_groups", // Ara tablo ismi
            joinColumns = @JoinColumn(name = "user_id"), // User tablosundan gelen kolon
            inverseJoinColumns = @JoinColumn(name = "group_id") // Groups tablosundan gelen kolon
    )
    private List<Groups> groups;


}
