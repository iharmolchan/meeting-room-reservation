package com.iharmolchan.meetingroomreservation.service;

import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    Reservation save(Reservation reservation, Long meetingRoomId);

    List<Reservation> getAll();

    List<Reservation> getAllByRoomId(Long floorId);

    Reservation getById(Long id);

    void deleteById(Long id);

    List<MeetingRoom> getFreeRooms(
            LocalDateTime meetingDateTime, Integer attendeesNumber,
            Boolean multimediaRequired, Long buildingId
    );
}
