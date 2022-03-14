package com.iharmolchan.meetingroomreservation.service.impl;

import com.iharmolchan.meetingroomreservation.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.model.Reservation;
import com.iharmolchan.meetingroomreservation.repository.MeetingRoomRepository;
import com.iharmolchan.meetingroomreservation.repository.ReservationRepository;
import com.iharmolchan.meetingroomreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReservationService implements ReservationService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation save(Reservation reservation, Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomRepository.getById(meetingRoomId);
        reservation.setMeetingRoom(meetingRoom);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getAllByRoomId(Long floorId) {
        return reservationRepository.findAllByMeetingRoomId(floorId);
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new DbEntityNotFoundException("Can't find reservation with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<MeetingRoom> getFreeRooms(
            LocalDateTime meetingDateTime, Integer attendeesNumber,
            Boolean multimediaRequired, Long buildingId
    ) {
        return meetingRoomRepository.getFreeRooms(attendeesNumber, multimediaRequired, meetingDateTime, buildingId);
    }
}
