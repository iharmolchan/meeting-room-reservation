package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.dto.ReservationTO;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.model.Reservation;
import com.iharmolchan.meetingroomreservation.service.MeetingRoomService;
import com.iharmolchan.meetingroomreservation.service.ReservationService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

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
        List<Reservation> reservations = meetingRoomId == null ? reservationService.getAll() :
                reservationService.getAllByRoomId(meetingRoomId);
        return reservations.stream().map(this::convertToDto).toList();
    }

    @JsonView(DefaultView.GET.class)
    @GetMapping("/{id}")
    public ReservationTO getById(@PathVariable Long id) {
        return convertToDto(reservationService.getById(id));
    }


    @Operation(summary = "Creates reservation as well as cleaning reservation for created one.")
    @PostMapping
    public ReservationTO createReservation(
            @JsonView(DefaultView.CREATE.class) @RequestBody ReservationTO reservationTO
    ) {
        Reservation reservation = convertToEntity(reservationTO);
        return convertToDto(reservationService.save(reservation));
    }

    @Operation(summary = "Updates reservation as well as cleaning reservation for created one.")
    @PutMapping
    public ReservationTO updateReservation(
            @JsonView(DefaultView.UPDATE.class) @RequestBody ReservationTO reservationTO
    ) {
        Reservation reservation = convertToEntity(reservationTO);
        return convertToDto(reservationService.save(reservation));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }

    @Operation(
            summary = "Get free meeting rooms sorted by efficiency of allocation. Returns rooms only with multimedia " +
                    "if multimedia is required and rooms with  multimedia and without if multimedia is not required " +
                    "(in that case rooms wihout multimedia are shown higher)."

    )
    @GetMapping("free-rooms")
    public List<MeetingRoom> getFreeRooms(
            @Parameter(description = "Datetime in yyyy-MM-dd HH:mm:ss format", example = "2000-10-31 01:30:00")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime meetingStartDateTime,
            @RequestParam Integer attendeesNumber,
            @RequestParam Boolean multimediaRequired,
            @RequestParam(required = false) Long buildingId
    ) {
        return reservationService.getFreeRooms(meetingStartDateTime, attendeesNumber, multimediaRequired, buildingId);
    }

    private ReservationTO convertToDto(Reservation reservation) {
        return modelMapper.map(reservation, ReservationTO.class);
    }

    private Reservation convertToEntity(ReservationTO reservationTO) {
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
