package com.iharmolchan.meetingroomreservation.service;

import com.iharmolchan.meetingroomreservation.model.MeetingRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    List<MeetingRoom> getFreeRooms(LocalDateTime meetingDateTime, Integer attendeesNumber, Boolean multimediaRequired,
                                   Long buildingId);
}
