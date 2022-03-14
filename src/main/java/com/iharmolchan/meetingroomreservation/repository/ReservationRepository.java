package com.iharmolchan.meetingroomreservation.repository;

import com.iharmolchan.meetingroomreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByMeetingRoomId(Long meetingRoomId);
}
