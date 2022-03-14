package com.iharmolchan.meetingroomreservation.repository;

import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {

    @Query(
            "SELECT mr FROM MeetingRoom AS mr " +
            "INNER JOIN mr.floor as f "+
            "INNER JOIN f.building AS b " +
            "LEFT JOIN mr.reservations as r " +
            "WHERE mr.capacity >= ?1 AND mr.multimediaCapability = ?2 AND (b.id = ?4 OR ?4 IS NULL)" +
                   "AND ((?3 NOT BETWEEN r.reservationStart AND r.reservationFinish) OR r.id IS NULL)" +
            "ORDER BY mr.capacity"
    )
    List<MeetingRoom> getFreeRooms(Integer capacity, Boolean multimediaCapability, LocalDateTime meetingStartDateTime, Long buildingId);
}
