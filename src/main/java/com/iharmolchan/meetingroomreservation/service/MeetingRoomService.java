package com.iharmolchan.meetingroomreservation.service;

import com.iharmolchan.meetingroomreservation.model.MeetingRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomService {
    MeetingRoom save(MeetingRoom meetingRoom);

    List<MeetingRoom> getAll();

    List<MeetingRoom> getAllByFloorId(Long floorId);

    List<MeetingRoom> getFreeRooms(
            LocalDateTime meetingDateTime, Integer attendeesNumber,
            Boolean multimediaRequired, Long buildingId
    );

    MeetingRoom getById(Long id);

    void deleteById(Long id);
}
