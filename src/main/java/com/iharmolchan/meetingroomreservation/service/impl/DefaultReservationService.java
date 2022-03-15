package com.iharmolchan.meetingroomreservation.service.impl;

import com.iharmolchan.meetingroomreservation.exception.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.exception.ValidationException;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.model.Reservation;
import com.iharmolchan.meetingroomreservation.repository.MeetingRoomRepository;
import com.iharmolchan.meetingroomreservation.repository.ReservationRepository;
import com.iharmolchan.meetingroomreservation.service.ReservationService;
import com.iharmolchan.meetingroomreservation.utils.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultReservationService implements ReservationService {

    @Value("${meetingRooms.cleanTime.basic}")
    private Integer basicRoomCleanTime;

    @Value("${meetingRooms.cleanTime.perSeat}")
    private Integer cleanTimePerRoomSeat;

    @Value("${meetingRooms.workingHours.start}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime meetingRoomOpening;

    @Value("${meetingRooms.workingHours.finish}")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime meetingRoomClosing;

    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public Reservation save(Reservation reservation, Long meetingRoomId) {
        MeetingRoom meetingRoom = getMeetingRoomById(meetingRoomId);
        reservation.setMeetingRoom(meetingRoom);
        checkReservationIsValid(reservation);

        Reservation cleaningReservation = createCleaningReservation(reservation);
        reservation.setParentReservation(cleaningReservation);

        Reservation savedReservation = reservationRepository.save(reservation);
        reservationRepository.save(cleaningReservation);
        return savedReservation;
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

    private Reservation createCleaningReservation(Reservation reservation) {
        int roomCleanTime = basicRoomCleanTime + cleanTimePerRoomSeat * reservation.getMeetingRoom().getCapacity();
        LocalDateTime cleaningStart = reservation.getReservationFinish();
        LocalDateTime cleaningFinish = cleaningStart.plusMinutes(roomCleanTime);

        Reservation cleaningReservation = Reservation.builder()
                .reservationStart(cleaningStart)
                .reservationFinish(cleaningFinish)
                .meetingRoom(reservation.getMeetingRoom())
                .meetingDescription("Cleaning room after meeting: " + reservation.getMeetingDescription())
                .build();
        checkReservationIsValid(cleaningReservation);
        return cleaningReservation;
    }

    private void checkReservationIsValid(Reservation reservation) {
        LocalTime reservationStart = reservation.getReservationStart().toLocalTime();
        LocalTime reservationFinish = reservation.getReservationFinish().toLocalTime();

        boolean reservationDatesValid = TimeUtils.isBetween(meetingRoomOpening, meetingRoomClosing, reservationStart) &&
                TimeUtils.isBetween(meetingRoomOpening, meetingRoomClosing, reservationFinish);

        if (!reservationDatesValid) {
            throw new ValidationException(String.format(
                    "Reservation %s is invalid. Start and finish time should be in between opening (%s) and closing (%s) hours.",
                    reservation, meetingRoomOpening, meetingRoomClosing
            ));
        }

        List<Reservation> overlappedReservations = reservationRepository.getOverlappedReservation(
                reservation.getMeetingRoom().getId(),
                reservation.getReservationStart(),
                reservation.getReservationFinish()
        );

        if (!overlappedReservations.isEmpty()) {
            throw new ValidationException(String.format(
                    "Reservation %s is invalid. Reservation overlaps some other ones: %s",
                    reservation, overlappedReservations
            ));
        }
    }

    private MeetingRoom getMeetingRoomById(Long id) {
        return meetingRoomRepository.findById(id)
                .orElseThrow(() -> new DbEntityNotFoundException("Can't find meeting room with id: " + id));
    }
}
