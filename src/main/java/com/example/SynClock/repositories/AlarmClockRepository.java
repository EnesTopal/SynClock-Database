package com.example.SynClock.repositories;

import com.example.SynClock.model.AlarmClock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmClockRepository extends JpaRepository<AlarmClock, Long> {
}
