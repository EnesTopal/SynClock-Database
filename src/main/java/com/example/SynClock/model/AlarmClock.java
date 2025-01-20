package com.example.SynClock.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "alarm_clock")
@Data
public class AlarmClock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    private Long uuid;

    @Column(name = "time", nullable = false)
    private String time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

//    @ManyToOne
//    @JoinColumn(name = "group_id", nullable = false)
//    private Group group;
}


