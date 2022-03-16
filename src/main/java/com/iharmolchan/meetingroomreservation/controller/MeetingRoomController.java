package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.dto.MeetingRoomTO;
import com.iharmolchan.meetingroomreservation.dto.ReservationTO;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.service.FloorService;
import com.iharmolchan.meetingroomreservation.service.MeetingRoomService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/meeting-rooms")
public class MeetingRoomController {
    private final ModelMapper modelMapper;
    private final MeetingRoomService meetingRoomService;
    private final FloorService floorService;

    @Operation(summary = "Returns all rooms with corresponding reservations.")
    @JsonView(DefaultView.GET.class)
    @GetMapping
    public List<MeetingRoomTO> getAll(@RequestParam(required = false) Long floorId) {
        log.info("Returning all meeting rooms. Chosen floor is: {}.", floorId);
        List<MeetingRoom> meetingRooms = floorId == null ? meetingRoomService.getAll() : meetingRoomService.getAllByFloorId(floorId);
        return meetingRooms.stream().map(this::convertToDto).toList();
    }

    @Operation(summary = "Returns a room with corresponding reservations.")
    @JsonView(DefaultView.GET.class)
    @GetMapping("/{id}")
    public MeetingRoomTO getById(@PathVariable Long id) {
        log.info("Returning meeting room with id: {}.", id);
        return convertToDto(meetingRoomService.getById(id));
    }

    @Operation(
            summary = "Get free meeting rooms sorted by efficiency of allocation. Returns rooms only with multimedia " +
                    "if multimedia is required and rooms with  multimedia and without if multimedia is not required " +
                    "(in that case rooms wihout multimedia are shown higher). Returns rooms with reservations."

    )
    @GetMapping("/free")
    public List<MeetingRoom> getFreeRooms(
            @Parameter(description = "Datetime in yyyy-MM-dd HH:mm:ss format", example = "2000-10-31 01:30:00")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime meetingStartDateTime,
            @RequestParam Integer attendeesNumber,
            @RequestParam Boolean multimediaRequired,
            @RequestParam(required = false) Long buildingId
    ) {
        log.info(
                "Getting all available meeting rooms at {} in building with id {}. Attendees number: {}. Multimedia required: {}",
                meetingStartDateTime, buildingId, attendeesNumber, multimediaRequired
        );
        return meetingRoomService.getFreeRooms(meetingStartDateTime, attendeesNumber, multimediaRequired, buildingId);
    }

    @PostMapping
    public MeetingRoomTO createMeetingRoom(@JsonView(DefaultView.CREATE.class) @RequestBody MeetingRoomTO meetingRoomTo) {
        log.info("Creating meeting room: {}", meetingRoomTo);
        MeetingRoom meetingRoom = convertToEntity(meetingRoomTo);
        return convertToDto(meetingRoomService.save(meetingRoom));
    }

    @PutMapping
    public MeetingRoomTO updateMeetingRoom(@JsonView(DefaultView.UPDATE.class) @RequestBody MeetingRoomTO meetingRoomTo) {
        log.info("Updating meeting room : {}", meetingRoomTo);
        MeetingRoom meetingRoom = convertToEntity(meetingRoomTo);
        return convertToDto(meetingRoomService.save(meetingRoom));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting meeting room with id: {}.", id);
        meetingRoomService.deleteById(id);
    }

    private MeetingRoomTO convertToDto(MeetingRoom meetingRoom) {
        log.debug("Converting meeting room {} to DTO.", meetingRoom);
        MeetingRoomTO meetingRoomTO = modelMapper.map(meetingRoom, MeetingRoomTO.class);
        List<ReservationTO> reservations = meetingRoom.getReservations().stream()
                .map(reservation -> modelMapper.map(reservation, ReservationTO.class))
                .toList();
        meetingRoomTO.setReservations(reservations);
        return meetingRoomTO;
    }

    private MeetingRoom convertToEntity(MeetingRoomTO meetingRoomTO) {
        log.debug("Converting meeting room DTO {} to entity.", meetingRoomTO);
        Floor floor = floorService.getById(meetingRoomTO.getFloorId());
        MeetingRoom meetingRoom = modelMapper.map(meetingRoomTO, MeetingRoom.class);
        meetingRoom.setFloor(floor);
        return meetingRoom;
    }
}
