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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    private final ModelMapper modelMapper;
    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public Reservation save(Reservation reservation) {
        checkReservationIsValid(reservation);

        Reservation savedReservation = reservationRepository.save(reservation);

        // if reservation is not cleaning reservation
        if (reservation.getParentReservation() == null) {
            Reservation cleaningReservation = prepareCleaningReservation(reservation);
            checkReservationIsValid(cleaningReservation);
            cleaningReservation.setParentReservation(reservation);
            reservationRepository.save(cleaningReservation);
        }
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

    private Reservation prepareCleaningReservation(Reservation reservation) {
        int roomCleanTime = basicRoomCleanTime + cleanTimePerRoomSeat * reservation.getMeetingRoom().getCapacity();
        LocalDateTime cleaningStart = reservation.getReservationFinish();
        LocalDateTime cleaningFinish = cleaningStart.plusMinutes(roomCleanTime);

        Reservation cleaningReservation = getCleaningReservation(reservation);
        cleaningReservation.setMeetingDescription("Cleaning room after meeting: " + reservation.getMeetingDescription());
        cleaningReservation.setReservationStart(cleaningStart);
        cleaningReservation.setReservationFinish(cleaningFinish);

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

        List<Long> excludedReservationIds = new ArrayList<>();
        excludedReservationIds.add(reservation.getId());

        if(reservation.getId() != null && reservation.getParentReservation() == null ) {
            Reservation cleaningReservation = getCleaningReservation(reservation);
            excludedReservationIds.add(cleaningReservation.getId());
        }

        List<Reservation> overlappedReservations = reservationRepository.getOverlappedReservations(
                excludedReservationIds,
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

    private Reservation getCleaningReservation(Reservation reservation) {
        return reservationRepository.findByParentReservationId(reservation.getId())
                .orElseGet(() -> Reservation.builder()
                        .meetingRoom(reservation.getMeetingRoom())
                        .build());
    }
}
