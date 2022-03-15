package com.iharmolchan.meetingroomreservation.repository;

import com.iharmolchan.meetingroomreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByMeetingRoomId(Long meetingRoomId);

    @Query(
            "SELECT res FROM Reservation AS res WHERE res.meetingRoom.id = ?1 " +
            "AND ((?2 BETWEEN res.reservationStart AND res.reservationFinish) " +
                "OR (?3 BETWEEN res.reservationStart AND res.reservationFinish))"
    )
    List<Reservation> getOverlappedReservation(Long meetingRoomId, LocalDateTime start, LocalDateTime finish);
}
