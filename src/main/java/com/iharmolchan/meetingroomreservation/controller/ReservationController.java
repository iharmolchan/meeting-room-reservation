package com.iharmolchan.meetingroomreservation.controller;

import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
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
