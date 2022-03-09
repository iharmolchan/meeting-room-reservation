package com.iharmolchan.meetingroomreservation.repository;

import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {

    @Query("FROM MeetingRoom AS mr WHERE mr.capacity >= ?1 AND mr.multimediaCapability = ?2 ORDER BY mr.capacity")
    List<MeetingRoom> getFreeRooms(Integer capacity, Boolean multimediaCapability);
}
