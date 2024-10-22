package com.example.calendar.controller;

import com.example.calendar.model.Meeting;
import com.example.calendar.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/book")
    public ResponseEntity<Meeting> bookMeeting(@RequestBody Meeting meeting) {
        Meeting bookedMeeting = meetingService.bookMeeting(meeting);
        return ResponseEntity.ok(bookedMeeting);
    }

    @GetMapping("/free-slots")
    public ResponseEntity<List<LocalDateTime>> getFreeSlots(@RequestParam String user1,
                                                             @RequestParam String user2,
                                                             @RequestParam int durationInMinutes) {
        Duration duration = Duration.ofMinutes(durationInMinutes);
        List<LocalDateTime> freeSlots = meetingService.findFreeSlots(user1, user2, duration);
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/conflicts")
    public ResponseEntity<List<Meeting>> findConflicts(@RequestBody Meeting meeting) {
        List<Meeting> conflicts = meetingService.findConflicts(meeting);
        return ResponseEntity.ok(conflicts);
    }
}
