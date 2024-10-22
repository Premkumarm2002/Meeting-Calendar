package com.example.calendar.service;

import com.example.calendar.model.Meeting;
import com.example.calendar.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public Meeting bookMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public List<LocalDateTime> findFreeSlots(String user1, String user2, Duration duration) {
        List<Meeting> meetingsUser1 = meetingRepository.findByOwner(user1);
        List<Meeting> meetingsUser2 = meetingRepository.findByOwner(user2);
        List<LocalDateTime> busySlots = new ArrayList<>();

        meetingsUser1.forEach(meeting -> {
            busySlots.add(meeting.getStartTime());
            busySlots.add(meeting.getEndTime());
        });
        meetingsUser2.forEach(meeting -> {
            busySlots.add(meeting.getStartTime());
            busySlots.add(meeting.getEndTime());
        });

        busySlots.sort(LocalDateTime::compareTo);
        List<LocalDateTime> freeSlots = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withNano(0);

        if (busySlots.isEmpty()) {
            freeSlots.add(currentTime);
            return freeSlots;
        }

        for (int i = 0; i < busySlots.size(); i += 2) {
            LocalDateTime start = busySlots.get(i);
            LocalDateTime end = i + 1 < busySlots.size() ? busySlots.get(i + 1) : null;

            if (currentTime.plus(duration).isBefore(start)) {
                freeSlots.add(currentTime);
                currentTime = currentTime.plus(duration);
            }

            if (end != null) {
                currentTime = end;
            }
        }

        freeSlots.add(currentTime);
        return freeSlots;
    }

    public List<Meeting> findConflicts(Meeting meeting) {
        return meetingRepository.findByOwner(meeting.getOwner())
                .stream()
                .filter(existingMeeting -> existingMeeting.getStartTime().isBefore(meeting.getEndTime()) &&
                                          existingMeeting.getEndTime().isAfter(meeting.getStartTime()))
                .toList();
    }
}
