package com.example.SynClock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_data")
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

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_to_groups",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "uuid")
    )
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();



}
//    @ManyToMany
//    @JoinTable(
//            name = "user_groups",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "group_id")
//    )
//    private List<Group> groups;
