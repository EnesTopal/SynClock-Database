package com.example.SynClock.repositories;

import com.example.SynClock.model.AlarmClock;
import com.example.SynClock.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmClockRepository extends JpaRepository<AlarmClock, Long> {
    List<AlarmClock> findByGroupUuid(Long uuid);
}
