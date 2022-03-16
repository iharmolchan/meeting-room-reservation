package com.iharmolchan.meetingroomreservation.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.iharmolchan.meetingroomreservation.dto.FloorTO;
import com.iharmolchan.meetingroomreservation.dto.MeetingRoomTO;
import com.iharmolchan.meetingroomreservation.model.Floor;
import com.iharmolchan.meetingroomreservation.model.MeetingRoom;
import com.iharmolchan.meetingroomreservation.service.FloorService;
import com.iharmolchan.meetingroomreservation.service.MeetingRoomService;
import com.iharmolchan.meetingroomreservation.views.DefaultView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

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
    public List<MeetingRoom> getAll(@RequestParam(required = false) Long floorId) {
        return floorId == null ? meetingRoomService.getAll() : meetingRoomService.getAllByFloorId(floorId);
    }

    @JsonView(DefaultView.GET.class)
    @GetMapping("/{id}")
    public MeetingRoom getById(@PathVariable Long id) {
        return meetingRoomService.getById(id);
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
        return modelMapper.map(meetingRoom, MeetingRoomTO.class);
    }

    private MeetingRoom convertToEntity(MeetingRoomTO meetingRoomTO) {
        Floor floor = floorService.getById(meetingRoomTO.getFloorId());
        MeetingRoom meetingRoom = modelMapper.map(meetingRoomTO, MeetingRoom.class);
        meetingRoom.setFloor(floor);
        return meetingRoom;
    }
}
