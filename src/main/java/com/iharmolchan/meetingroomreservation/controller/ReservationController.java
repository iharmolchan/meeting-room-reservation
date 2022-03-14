package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.model.Reservation;
import com.iharmolchan.meetingroomreservation.service.ReservationService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAll(@RequestParam(required = false) Long meetingRoomId) {
        return meetingRoomId == null ? reservationService.getAll() : reservationService.getAllByRoomId(meetingRoomId);
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }


    @PostMapping
    public Reservation createReservation(
            @RequestParam Long meetingRoomId,
            @JsonView(DefaultView.CREATE.class) @RequestBody Reservation reservation
    ) {
        return reservationService.save(reservation, meetingRoomId);
    }

    @PutMapping
    public Reservation updateReservation(
            @RequestParam Long meetingRoomId,
            @JsonView(DefaultView.UPDATE.class) @RequestBody Reservation reservation
    ) {
        return reservationService.save(reservation, meetingRoomId);
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
            @Parameter(description = "Datetime in yyyy-MM-dd'T'HH:mm:ss format", example = "2000-10-31T01:30:00")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime meetingStartDateTime,
            @RequestParam Integer attendeesNumber,
            @RequestParam Boolean multimediaRequired,
            @RequestParam(required = false) Long buildingId
    ) {
        return reservationService.getFreeRooms(meetingStartDateTime, attendeesNumber, multimediaRequired, buildingId);
    }


}
