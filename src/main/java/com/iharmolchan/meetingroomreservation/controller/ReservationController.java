package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.dto.ReservationTO;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.model.Reservation;
import com.iharmolchan.meetingroomreservation.service.MeetingRoomService;
import com.iharmolchan.meetingroomreservation.service.ReservationService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final MeetingRoomService meetingRoomService;
    private final ModelMapper modelMapper;

    @JsonView(DefaultView.GET.class)
    @GetMapping
    public List<ReservationTO> getAll(@RequestParam(required = false) Long meetingRoomId) {
        log.info("Returning all reservations.");
        List<Reservation> reservations = meetingRoomId == null ? reservationService.getAll() :
                reservationService.getAllByRoomId(meetingRoomId);
        return reservations.stream().map(this::convertToDto).toList();
    }

    @JsonView(DefaultView.GET.class)
    @GetMapping("/{id}")
    public ReservationTO getById(@PathVariable Long id) {
        log.info("Returning reservation with id: {}.", id);
        return convertToDto(reservationService.getById(id));
    }


    @Operation(summary = "Creates reservation as well as cleaning reservation for created one.")
    @PostMapping
    public ReservationTO createReservation(
            @JsonView(DefaultView.CREATE.class) @RequestBody ReservationTO reservationTO
    ) {
        log.info("Creating reservation: {}", reservationTO);
        Reservation reservation = convertToEntity(reservationTO);
        return convertToDto(reservationService.save(reservation));
    }

    @Operation(summary = "Updates reservation as well as cleaning reservation for created one.")
    @PutMapping
    public ReservationTO updateReservation(
            @JsonView(DefaultView.UPDATE.class) @RequestBody ReservationTO reservationTO
    ) {
        log.info("Updating reservation: {}.", reservationTO);
        Reservation reservation = convertToEntity(reservationTO);
        return convertToDto(reservationService.save(reservation));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting reservation with id: {}.", id);
        reservationService.deleteById(id);
    }

    private ReservationTO convertToDto(Reservation reservation) {
        log.debug("Converting reservation {} to DTO.", reservation);
        return modelMapper.map(reservation, ReservationTO.class);
    }

    private Reservation convertToEntity(ReservationTO reservationTO) {
        log.debug("Converting reservation DTO {} to entity.", reservationTO);
        Reservation reservation = modelMapper.map(reservationTO, Reservation.class);
        MeetingRoom meetingRoom = meetingRoomService.getById(reservationTO.getMeetingRoomId());
        reservation.setMeetingRoom(meetingRoom);
        if (reservationTO.getParentReservationId() != null) {
            Reservation parent = reservationService.getById(reservationTO.getParentReservationId());
            reservation.setParentReservation(parent);
        }
        return reservation;
    }

}
