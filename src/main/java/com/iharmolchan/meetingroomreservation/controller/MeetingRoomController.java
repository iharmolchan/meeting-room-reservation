package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.dto.MeetingRoomTO;
import com.iharmolchan.meetingroomreservation.dto.ReservationTO;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.service.FloorService;
import com.iharmolchan.meetingroomreservation.service.MeetingRoomService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/meeting-rooms")
public class MeetingRoomController {
    private final ModelMapper modelMapper;
    private final MeetingRoomService meetingRoomService;
    private final FloorService floorService;

    @JsonView(DefaultView.GET.class)
    @GetMapping
    public List<MeetingRoomTO> getAll(@RequestParam(required = false) Long floorId) {
        List<MeetingRoom> meetingRooms = floorId == null ? meetingRoomService.getAll() : meetingRoomService.getAllByFloorId(floorId);
        return meetingRooms.stream().map(this::convertToDto).toList();
    }

    @JsonView(DefaultView.GET.class)
    @GetMapping("/{id}")
    public MeetingRoomTO getById(@PathVariable Long id) {
        return convertToDto(meetingRoomService.getById(id));
    }


    @PostMapping
    public MeetingRoomTO createMeetingRoom(@JsonView(DefaultView.CREATE.class) @RequestBody MeetingRoomTO meetingRoomTo) {
        MeetingRoom meetingRoom = convertToEntity(meetingRoomTo);
        return convertToDto(meetingRoomService.save(meetingRoom));
    }

    @PutMapping
    public MeetingRoomTO updateMeetingRoom(@JsonView(DefaultView.UPDATE.class) @RequestBody MeetingRoomTO meetingRoomTo) {
        MeetingRoom meetingRoom = convertToEntity(meetingRoomTo);
        return convertToDto(meetingRoomService.save(meetingRoom));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        meetingRoomService.deleteById(id);
    }

    private MeetingRoomTO convertToDto(MeetingRoom meetingRoom) {
        MeetingRoomTO meetingRoomTO = modelMapper.map(meetingRoom, MeetingRoomTO.class);
        List<ReservationTO> reservations = meetingRoom.getReservations().stream()
                .map(reservation -> modelMapper.map(reservation, ReservationTO.class))
                .toList();
        meetingRoomTO.setReservations(reservations);
        return meetingRoomTO;
    }

    private MeetingRoom convertToEntity(MeetingRoomTO meetingRoomTO) {
        Floor floor = floorService.getById(meetingRoomTO.getFloorId());
        MeetingRoom meetingRoom = modelMapper.map(meetingRoomTO, MeetingRoom.class);
        meetingRoom.setFloor(floor);
        return meetingRoom;
    }
}
