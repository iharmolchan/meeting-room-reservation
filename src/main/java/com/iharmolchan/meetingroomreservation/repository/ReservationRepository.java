package com.iharmolchan.meetingroomreservation.repository;

import com.iharmolchan.meetingroomreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByParentReservationId(Long parentId);

    List<Reservation> findAllByMeetingRoomId(Long meetingRoomId);

    @Query(
            "SELECT res FROM Reservation AS res WHERE res.id not in ?1 AND res.meetingRoom.id = ?2 " +
                    "AND ((?3 >= res.reservationStart AND ?3 < res.reservationFinish) " +
                    "OR (?4 > res.reservationStart AND ?4 <= res.reservationFinish))"
    )
    List<Reservation> getOverlappedReservations(List<Long> excludedIds, Long meetingRoomId, LocalDateTime start, LocalDateTime finish);
}
