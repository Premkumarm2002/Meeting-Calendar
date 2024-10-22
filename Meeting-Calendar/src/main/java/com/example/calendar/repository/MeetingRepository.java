package com.example.calendar.repository;

import com.example.calendar.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByOwner(String owner);
    List<Meeting> findByOwnerAndStartTimeBetween(String owner, LocalDateTime start, LocalDateTime end);
}
