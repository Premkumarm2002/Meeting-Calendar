package com.example.calendar.service;

import com.example.calendar.model.Meeting;
import com.example.calendar.repository.MeetingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;

    @InjectMocks
    private MeetingService meetingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookMeeting() {
        Meeting meeting = new Meeting();
        meeting.setOwner("sandy");
        meeting.setStartTime(LocalDateTime.now());
        meeting.setEndTime(LocalDateTime.now().plusHours(1));
        when(meetingRepository.save(meeting)).thenReturn(meeting);

        Meeting result = meetingService.bookMeeting(meeting);
        assertNotNull(result);
        assertEquals("sandy", result.getOwner());
    }

    @Test
    public void testFindFreeSlots() {
        Meeting meeting1 = new Meeting();
        meeting1.setOwner("sandy");
        meeting1.setStartTime(LocalDateTime.now().plusMinutes(30));
        meeting1.setEndTime(LocalDateTime.now().plusMinutes(90));

        Meeting meeting2 = new Meeting();
        meeting2.setOwner("john");
        meeting2.setStartTime(LocalDateTime.now().plusMinutes(60));
        meeting2.setEndTime(LocalDateTime.now().plusMinutes(120));

        when(meetingRepository.findByOwner("sandy")).thenReturn(List.of(meeting1));
        when(meetingRepository.findByOwner("john")).thenReturn(List.of(meeting2));

        List<LocalDateTime> freeSlots = meetingService.findFreeSlots("sandy", "john", Duration.ofMinutes(30));
        assertFalse(freeSlots.isEmpty());
        assertEquals(LocalDateTime.now().withSecond(0).withNano(0), freeSlots.get(0));
    }

    @Test
    public void testFindConflicts() {
        Meeting existingMeeting = new Meeting();
        existingMeeting.setOwner("sandy");
        existingMeeting.setStartTime(LocalDateTime.now().plusMinutes(10));
        existingMeeting.setEndTime(LocalDateTime.now().plusMinutes(50));

        Meeting newMeeting = new Meeting();
        newMeeting.setOwner("sandy");
        newMeeting.setStartTime(LocalDateTime.now().plusMinutes(20));
        newMeeting.setEndTime(LocalDateTime.now().plusMinutes(40));

        when(meetingRepository.findByOwner("sandy")).thenReturn(List.of(existingMeeting));

        List<Meeting> conflicts = meetingService.findConflicts(newMeeting);
        assertEquals(1, conflicts.size());
        assertEquals(existingMeeting.getId(), conflicts.get(0).getId());
    }
}
